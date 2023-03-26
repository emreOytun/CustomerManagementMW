package com.emreoytun.customermanagementmw.dto;

import com.emreoytun.customermanagementmw.dto.customer.responses.CustomerGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

// Makes conversions between Customer class and Dto classes.
// For most used ones.
@Service
public class ModelMapperService implements IModelMapperService {

    @Override
    public <Source, Target> Target map(Source sourceObj, Class<Target> targetClass, String... ignoreProperties) {
        try {
            Target targetObj = targetClass.newInstance();
            BeanUtils.copyProperties(sourceObj, targetObj);
            return targetObj;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <Source, Target> void copyProperties(Source sourceObj, Target targetObj, String... ignoreProperties) {
        BeanUtils.copyProperties(sourceObj, targetObj, ignoreProperties);
    }
}
