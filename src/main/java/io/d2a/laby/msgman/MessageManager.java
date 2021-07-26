package io.d2a.laby.msgman;

import com.google.common.collect.Lists;
import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.ctl.ConfigController;
import io.d2a.laby.msgman.cfg.SettingsConfig;
import io.d2a.laby.msgman.format.ServerFormat;
import io.d2a.laby.msgman.format.ServerFormat.Room;
import io.d2a.laby.msgman.format.ServerFormat.Room.Format;
import io.d2a.laby.msgman.msg.Direction;
import io.d2a.laby.msgman.msg.InPlayerMessage;
import io.d2a.laby.msgman.msg.OutPlayerMessage;
import io.d2a.laby.msgman.msg.Placeholder;
import io.d2a.laby.msgman.msg.Placeholder.Keyword;
import io.d2a.laby.msgman.msg.PlayerMessage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.chat.MessageReceiveEvent;
import net.labymod.api.event.events.network.server.LoginServerEvent;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.ModColor;

public class MessageManager extends LabyModAddonAdapter {

  private final SettingsConfig config = new SettingsConfig();
  private final ConfigController<SettingsConfig> cfgCtl = ConfigController
      .fromUnsafe(this, SettingsConfig.class, config)
      .orElseThrow(IllegalStateException::new)
      .registerAll(this);

  private final List<ServerFormat> servers;
  private final List<Room> currentRooms;

  public MessageManager() {
    this.servers = Lists.newArrayList();
    this.currentRooms = Lists.newArrayList();
  }

  @Override
  public void onEnableUnsafe() throws Exception {
    System.out.println("It works! (or does it?)");

    // load all servers in server dir
    // TODO: (Prod) add to resources/
    {
      final File file = new File("servers");
      for (final File sub : Objects.requireNonNull(file.listFiles())) {
        if (!sub.isFile()) {
          continue;
        }
        if (!sub.getName().endsWith(".json")) {
          continue;
        }
        final Optional<ServerFormat> sf = ServerFormat.parseUnsafe(sub);
        sf.ifPresent(this.servers::add);
        System.out.println("Added server: " + sf);
      }
    }

    this.getApi().getEventService().registerListener(this);
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
    this.cfgCtl.readConfig();
  }

  @Override
  public void fillSettingsUnsafe(final List<SettingsElement> list) throws Exception {
    this.cfgCtl.getPageCtl().fillSettings(this.config, list);
    System.out.println("after fill: " + this.config.enabled + ", name: " + this.config.name);
  }

  @SubscribeSettings("enable")
  public void onEnableChange(@Old boolean o, @New boolean n) {
    System.out.println("msgman :: enable :: o: " + o + ", n: " + n);
  }

  /// Listeners

  @Subscribe
  public void onJoin(final LoginServerEvent event) {
    final Optional<ServerFormat> fmt = this.servers.stream()
        .filter(s -> s.accepts(event.getServerData()))
        .findFirst();
    if (!fmt.isPresent()) {
      return;
    }
    this.currentRooms.clear();
    this.currentRooms.addAll(fmt.get().rooms);

    System.out.println("msgman :: using server: " + fmt.get().name +
        " (" + fmt.get().address + ")");
    System.out.println("msgman :: rooms: " + this.currentRooms.size());
  }

  @Subscribe
  public void onMessage(final MessageReceiveEvent event) {
    final String clean = ModColor.removeColor(event.getComponent().getString());
    for (final Room room : this.currentRooms) {
      for (final Format format : room.format) {
        for (final List<String> match : format.match) {
          final Map<String, String> map = Placeholder.parse(match, clean);
          if (map == null) {
            continue;
          }

          // MATCHES! Use this!
          for (final Direction dir : format.direction) {
            this.processMessage(dir, clean, map, room, format, match);
          }

          break;
        }
      }
    }
  }

  /// ...

  private void processMessage(
      final Direction direction,
      final String clean,
      final Map<String, String> placeholders,
      final Room room,
      final Format format,
      final List<String> match
  ) {
    // TODO: Implement me
    PlayerMessage msg;
    if (direction == Direction.INBOUND) {
      msg = new InPlayerMessage(
          placeholders.getOrDefault(Keyword.SENDER.toString(), "Unknown Sender"),
          placeholders.getOrDefault(Keyword.MESSAGE.toString(), "n/a")
      );
    } else {
      msg = new OutPlayerMessage(
          placeholders.getOrDefault(Keyword.RECEIVER.toString(), "Unknown Receiver"),
          placeholders.getOrDefault(Keyword.MESSAGE.toString(), "n/a")
      );
    }
  }

}
