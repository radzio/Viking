package net.tango.viking.annotations;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value = TYPE)
public @interface AutoModule {
  Class<?>[] includes() default {};
  Class<?>[] scopes() default {};
}