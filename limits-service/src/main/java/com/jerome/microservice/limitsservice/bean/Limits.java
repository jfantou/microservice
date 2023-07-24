package com.jerome.microservice.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Limits {
    private int maximum;
    private int minimum;
}
