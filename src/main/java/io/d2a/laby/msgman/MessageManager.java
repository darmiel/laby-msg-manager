package io.d2a.laby.msgman;

import com.google.common.collect.Lists;
import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.ctl.ConfigController;
import io.d2a.laby.msgman.cfg.SettingsConfig;
import io.d2a.laby.msgman.format.ServerFormat;
import io.d2a.laby.msgman.format.ServerFormat.Room;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.network.server.LoginServerEvent;
import net.labymod.settings.elements.SettingsElement;

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
    {
      final File file = new File("servers");
      for (final File sub : Objects.requireNonNull(file.listFiles())) {
        if (!sub.isFile()) {
          continue;
        }
        if (!sub.getName().endsWith(".json")) {
          continue;
        }
        final Optional<ServerFormat> sf = ServerFormat.parseUnsafe(file);
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
  }

}
