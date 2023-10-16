package com.hermit.ppt.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;

@Converter
public class StringListConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        if(strings == null) return "";
        return String.join(",", strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        if(joined == null) return new HashSet<>();
        return new HashSet<>(Arrays.asList(joined.split(",")));
    }
}
