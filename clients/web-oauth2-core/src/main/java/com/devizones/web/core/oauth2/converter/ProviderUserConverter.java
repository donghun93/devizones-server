package com.devizones.web.core.oauth2.converter;

@FunctionalInterface
public interface ProviderUserConverter<T, R> {
    R converter(T t);
}