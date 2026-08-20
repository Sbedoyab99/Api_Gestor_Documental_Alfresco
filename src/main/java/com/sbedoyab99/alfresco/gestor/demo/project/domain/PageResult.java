package com.sbedoyab99.alfresco.gestor.demo.project.domain;

public record PageResult<T>(
    Number page,
    Number size,
    Number totalItems,
    Number totalPages,
    Boolean hasNext, T data) {
}
