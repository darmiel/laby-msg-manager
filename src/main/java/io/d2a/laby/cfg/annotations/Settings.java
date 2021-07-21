package io.d2a.laby.cfg.annotations;

import io.d2a.laby.cfg.wrapper.DummyWrapper;
import io.d2a.laby.cfg.wrapper.SettingsElementWrapper;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Settings {

  /**
   * Name of setting element (displayName), can include color codes ('&' and '\u00A7')
   * <p>
   * CANNOT BE EMPTY!
   *
   * @return displayName, json key
   */
  String value() default "";

  Material icon() default Material.AIR;

  Class<? extends SettingsElementWrapper<? extends SettingsElement, ?>> wrapper() default DummyWrapper.class;

  String listener() default "";

}
