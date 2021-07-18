package io.d2a.laby.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If the @{@link Default} annotation is used on a field, the default value from the field is used
 * as the starting value for the config. Either the default value can be stored directly by
 * instantiating the variable, or if it is a primitive data type, the default value of the primitive
 * type is used. <br /> Class:
 * <pre>
 *   class Config {
 *      @Setting
 *      @Default
 *      public boolean enabled = false;
 *   }
 * </pre>
 * <br /> Json:
 * <pre>
 *   {
 *     "Enabled": false
 *   }
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {

}
