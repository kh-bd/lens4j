/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for generate read and write lens from class.
 *
 * @author Alexey_Bodyak
 */
@Documented
@Target(ElementType.TYPE)
@Repeatable(GenReadWriteLenses.class)
@Retention(RetentionPolicy.CLASS)
public @interface GenReadWriteLens {

    String path();

    String lensName();

    String factoryName() default "Lenses";
}
