package io.d2a.laby.cfg.annotations.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenSettingsChange {

  public enum Order {
    EARLY(0),
    NORMAL(10),
    LATE(100);

    public int weight;

    Order(int weight) {
      this.weight = weight;
    }
  }

  String value();

  Order priority() default Order.NORMAL;


}
