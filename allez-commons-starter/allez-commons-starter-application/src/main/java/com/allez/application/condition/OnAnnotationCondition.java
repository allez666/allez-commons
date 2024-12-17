package com.allez.application.condition;

import cn.hutool.core.collection.CollUtil;
import com.allez.application.annotation.ConditionalOnAnnotation;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: chenGuanXi
 * @create: 2024-12-17 21:59
 * @Description:
 */
@Order()
public class OnAnnotationCondition extends SpringBootCondition {


    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // todo 兼容一下 conditionalOnMissAnnotation
        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(ConditionalOnAnnotation.class.getName());
        if (CollUtil.isEmpty(allAnnotationAttributes)) {
            return ConditionOutcome.noMatch("no annotation found");
        }
        List<Object> objects = allAnnotationAttributes.get(ConditionalOnAnnotation.valueFieldName);
        Class<? extends Annotation>[] annotations = (Class<? extends Annotation>[]) CollUtil.getFirst(objects);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for (Class<? extends Annotation> aClass : annotations) {
            String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(aClass);
            if(beanNamesForAnnotation.length == 0) {
               return ConditionOutcome.noMatch(ConditionMessage.forCondition(aClass,"no exist").because(""));
            }
        }
        return ConditionOutcome.match();
    }
}
