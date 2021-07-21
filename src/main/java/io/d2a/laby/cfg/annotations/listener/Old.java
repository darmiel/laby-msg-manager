package io.d2a.laby.cfg.annotations.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link SubscribeSettings} annotated methods can use the <code>@Old</code> interface on
 * parameters.
 *
 * <code>@Old</code> indicates that the annotated parameter should specify the old (previous) value
 * from the setting.
 * <p>
 * Example:
 * <pre>
 * \@SubscribeSettings("enable")
 * public void onEnableChange(@Old final boolean oldValue) {
 *   System.out.println("Old Value: + oldValue);
 * }
 * </pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Old {

}
