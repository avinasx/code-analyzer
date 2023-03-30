package com.example.app.util;

import com.epages.restdocs.apispec.ConstrainedFields;
import lombok.AllArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConstrainedFieldDocumentation {
    private final Class<?> clazz;

    public List<FieldDescriptor> restdocsFields(List<FieldDescriptor> requestFieldList) {
        var constrainedFieldAttribute = new ConstrainedFieldAttribute(clazz);
        return requestFieldList.stream()
                .map(fieldDescriptor -> {
                    var validationConstraints = constrainedFieldAttribute.constrainedAttribute(fieldDescriptor.getPath());
                    return fieldDescriptor.attributes(validationConstraints);
                })
                .collect(Collectors.toList());
    }

    public List<FieldDescriptor> openapiFields(List<FieldDescriptor> requestFieldList) {
        var constrainedFieldDescriptor = new ConstrainedFields(clazz);
        return requestFieldList.stream()
                .map(fieldDescriptor -> {
                    var type = fieldDescriptor.getType();
                    var optional = fieldDescriptor.isOptional();
                    var ignored = fieldDescriptor.isIgnored();
                    var description = fieldDescriptor.getDescription();

                    var field = constrainedFieldDescriptor.withPath(fieldDescriptor.getPath());
                    field.type(type).description(description);
                    if (optional) {
                        field.optional();
                    }
                    if (ignored) {
                        field.ignored();
                    }
                    return field;
                })
                .collect(Collectors.toList());
    }
}
