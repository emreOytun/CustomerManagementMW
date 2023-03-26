package com.emreoytun.customermanagementmw.dto;

public interface IModelMapperService {

    <Source, Target> Target map(Source sourceObj, Class<Target> targetClass, String... ignoreProperties);
    <Source, Target> void copyProperties(Source sourceObj, Target targetObj, String... ignoreProperties);

}
