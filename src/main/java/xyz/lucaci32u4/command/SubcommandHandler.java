package xyz.lucaci32u4.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SubcommandHandler {
    /**
     * Specifies the subcommand name this method is linked to
     * @return subcommand name
     */
    String value() default "";
}