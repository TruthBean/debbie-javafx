package com.truthbean.debbie.javafx;

import com.truthbean.debbie.bean.BeanComponent;

import java.lang.annotation.*;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanComponent
public @interface DebbieJavaFx {

}