package io.d2a.laby.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a field is annotated with Header in the config, a {@link net.labymod.settings.elements.HeaderElement}
 * is created. If you only want to create a header, you can create a field of type {@link
 * io.d2a.laby.cfg.Dummy}.
 * <p>
 * Example:
 * <pre>
 *   class Config {
 *      \@Header(&a"Misc")
 *      private final Dummy dummy1 = Dummy.EMPTY;
 *   }
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

  /**
   * This value is displayed with the header. May contain color codes (either prefix the color codes
   * with '§' or '&').
   *
   * @return header value
   */
  String value() default "";

  /**
   * By default, it checks if the previous header matches the new header. If the value of the
   * previous header is the same as the new header, no second header with the same value is created.
   * If <code>force()</code> is true, another header with the same value is created anyway.
   *
   * @return force create new headers with same values
   */
  boolean force() default false;

}
