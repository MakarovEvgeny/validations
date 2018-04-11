package project.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import project.model.eventlog.EventLogType;
import project.model.mapper.DefaultMapper;
import project.model.mapper.Mapper;

/**
 * Аннотация для аспекта. Включает логирование вызова метода в БД.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingThat {
    /** Тип события. */
    EventLogType type();
    /** Текстовое описание операции. */
    String operation();
    /** Уточняющий комментарий. */
    String commentary() default "";
    /** Маппер данных для записи лога пользовательских операций. */
    Class<? extends Mapper> mapper() default DefaultMapper.class;
}
