package com.emreoytun.customermanagementmw.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

// Makes conversions between Customer class and Dto classes.
// For most used ones.
@Service
public class DtoTransformService {

    public <Source, Target> Target convert(Source sourceObj, Class<Target> targetClass, String... ignoreProperties) {
        try {
            Target targetObj = targetClass.newInstance();
            BeanUtils.copyProperties(sourceObj, targetObj);
            return targetObj;
        } catch (Exception e) {
            return null;
        }
    }

    public <Source, Target> void copyProperties(Source sourceObj, Target targetObj, String... ignoreProperties) {
        BeanUtils.copyProperties(sourceObj, targetObj, ignoreProperties);
    }
}
