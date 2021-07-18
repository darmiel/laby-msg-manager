package io.d2a.laby.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface SubscribeSetting {

  String value();
  boolean rold() default true;
  boolean rnew() default true;

}
