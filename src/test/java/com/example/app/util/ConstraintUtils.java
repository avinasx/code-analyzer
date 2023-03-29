package com.example.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstraintUtils {
    public static FieldDescriptor constrainedFieldWithPath(Class<?> clazz, String path) {
        var constraints = new ConstraintDescriptions(clazz);
        return fieldWithPath(path).attributes(key("constraints").value(StringUtils.collectionToDelimitedString(
                constraints.descriptionsForProperty(path), ". ")));
    }
}
