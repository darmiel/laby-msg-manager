package io.d2a.laby.cfg.annotations.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link SubscribeSettings} annotated methods can use the <code>@New</code> interface on
 * parameters.
 *
 * <code>@New</code> indicates that the annotated parameter should specify the new value from the
 * setting.
 * <p>
 * Example:
 * <pre>
 * \@SubscribeSettings("enable")
 * public void onEnableChange(@New final boolean newValue) {
 *   System.out.println("New Value: + newValue);
 * }
 * </pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface New {

}
