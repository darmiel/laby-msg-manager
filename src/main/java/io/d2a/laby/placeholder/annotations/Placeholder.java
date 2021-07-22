package io.d2a.laby.placeholder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Placeholder {

  String value() default "";

  /**
   * @return Min. expected words
   */
  int minWords() default 1;

  /**
   * @return Max. expected words (-1 = Infinite)
   */
  int maxWords() default 1;

  /**
   * Sets {@link Placeholder#minWords()} = 1 and {@link Placeholder#maxWords()} = Infinite
   *
   * @return Infinite words to check
   */
  boolean inf() default false;

}
