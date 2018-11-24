package com.crappyengineering.springboot.demo.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TaskStatus {
    @JsonEnumDefaultValue
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;
}
