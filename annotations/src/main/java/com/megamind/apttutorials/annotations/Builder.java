package com.megamind.apttutorials.annotations;

/**
 * @author HanZhengYa
 * @description
 * @date 16:58 9/4/20
 * @since 1.0
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Builder {
}
