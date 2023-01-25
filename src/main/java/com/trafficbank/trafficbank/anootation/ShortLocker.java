package com.trafficbank.trafficbank.anootation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ShortLockerContainer.class)
public @interface ShortLocker {
    String key();

    long expire() default 2L;

    boolean unlock() default false;
}
