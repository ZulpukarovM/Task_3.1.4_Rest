package ru.kata.spring.boot_security.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorResponse {

    private final List<Violation> violationList;

}
