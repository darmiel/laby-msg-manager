package io.d2a.laby.cfg.annotations.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link SubscribeSettings} annotated methods can use the <code>@Var</code> interface on
 * parameters.
 *
 * <code>@Var</code> indicates that the annotated parameter should specify the name of the changed
 * setting.
 * <p>
 * Example:
 * <pre>
 * \@SubscribeSettings("enable")
 * public void onEnableChange(@Var final String var) {
 *   System.out.println("Var: " + var); // always "Var: enable"
 * }
 * </pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Var {

}
