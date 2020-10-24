package com.truthbean.debbie.javafx;

import com.truthbean.debbie.bean.BeanComponent;
import com.truthbean.debbie.bean.BeanType;

import java.lang.annotation.*;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanComponent(type = BeanType.SINGLETON, name = "primaryStage")
public @interface DebbieJavaFx {

}