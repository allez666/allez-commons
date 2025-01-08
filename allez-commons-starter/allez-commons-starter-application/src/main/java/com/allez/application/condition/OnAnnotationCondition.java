package com.allez.application.condition;

import cn.hutool.core.collection.CollUtil;
import com.allez.application.annotation.ConditionalOnAnnotation;
import com.allez.application.annotation.ConditionalOnMissingAnnotation;
import com.allez.lang.util.AssertUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: chenyu
 * @create: 2024-12-17 21:59
 * @Description:
 */
@Order
public class OnAnnotationCondition extends SpringBootCondition {


    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (metadata.isAnnotated(ConditionalOnAnnotation.class.getName())) {
            ConditionOutcome conditionOutcome = onAnnotationMatch(context, metadata);
            if (!conditionOutcome.isMatch()) {
                return conditionOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnMissingAnnotation.class.getName())) {
            ConditionOutcome conditionOutcome = onMissingAnnotationMatch(context, metadata);
            if (!conditionOutcome.isMatch()) {
                return conditionOutcome;
            }
        }
        return ConditionOutcome.match();
    }

    @SuppressWarnings("unchecked")
    public ConditionOutcome onAnnotationMatch(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(ConditionalOnAnnotation.class.getName());
        if (CollUtil.isEmpty(allAnnotationAttributes)) {
            return ConditionOutcome.noMatch("no annotation found");
        }
        List<Object> objects = allAnnotationAttributes.get(ConditionalOnAnnotation.VALUE_FIELD_NAME);
        Class<? extends Annotation>[] annotations = (Class<? extends Annotation>[]) CollUtil.getFirst(objects);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        AssertUtils.notNull(beanFactory,"beanFactory is null");

        for (Class<? extends Annotation> aClass : annotations) {
            String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(aClass);
            if (beanNamesForAnnotation.length == 0) {
                return ConditionOutcome.noMatch(ConditionMessage.forCondition(aClass, "no exist").because(""));
            }
        }
        return ConditionOutcome.match();
    }

    @SuppressWarnings("unchecked")
    public ConditionOutcome onMissingAnnotationMatch(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(ConditionalOnMissingAnnotation.class.getName());
        if (CollUtil.isEmpty(allAnnotationAttributes)) {
            return ConditionOutcome.noMatch("no annotation found");
        }
        List<Object> objects = allAnnotationAttributes.get(ConditionalOnMissingAnnotation.VALUE_FIELD_NAME);
        Class<? extends Annotation>[] annotations = (Class<? extends Annotation>[]) CollUtil.getFirst(objects);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        AssertUtils.notNull(beanFactory,"beanFactory is null");
        for (Class<? extends Annotation> aClass : annotations) {
            String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(aClass);
            if (beanNamesForAnnotation.length != 0) {
                return ConditionOutcome.noMatch(ConditionMessage.forCondition(aClass, "exist").because(""));
            }
        }
        return ConditionOutcome.match();
    }
}
