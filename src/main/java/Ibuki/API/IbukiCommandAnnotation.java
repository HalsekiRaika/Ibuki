package Ibuki.API;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface IbukiCommandAnnotation {
    String CommandName();
    String CommandDescription();
    boolean isChannelMessage() default false;
    boolean hasArgs() default false;
    boolean isPrivate() default false;
}
