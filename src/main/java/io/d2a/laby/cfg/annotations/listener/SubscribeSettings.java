package io.d2a.laby.cfg.annotations.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with \@SubscribeSettings and registered with the {@link
 * io.d2a.laby.cfg.ctl.ListenerController} are called when the value for the setting changes.
 * Parameters can be annotated with @{@link New}, @{@link Old}, or @{@link Var} (see respective
 * descriptions).
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeSettings {

  /**
   * For which settings should the annotated method be called as soon as its value changes?
   *
   * @return name of settings
   */
  String[] value();

  /**
   * The priority specifies the order in which the methods are called. The higher
   * <code>weight</code> is, the later the method is executed in the ranking.
   *
   * @return order (priority)
   */
  Order priority() default Order.NORMAL;

  enum Order {
    EARLY(0),
    NORMAL(10),
    LATE(100);

    public int weight;

    Order(int weight) {
      this.weight = weight;
    }
  }

}
