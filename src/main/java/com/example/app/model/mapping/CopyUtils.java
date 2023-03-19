package com.example.app.model.mapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyUtils {
    /**
     * Copy all properties from source to target, but only if the source property is not null.
     * @param source source object
     * @param target target object
     */
    public static void copyNonNullProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        // Get source and target property descriptors
        var sourceProperties = BeanUtils.getPropertyDescriptors(source.getClass());
        var targetProperties = BeanUtils.getPropertyDescriptors(target.getClass());
        // Create a hashmap to store target properties
        var targetPropertiesMap = getPropertyDescriptorHashMap(targetProperties);

        for (var sourceProperty : sourceProperties) {
            var propertyName = sourceProperty.getName();
            if (targetPropertiesMap.containsKey(propertyName)) {
                var targetProperty = targetPropertiesMap.get(propertyName);

                // Check if source and target have both read and write methods
                var readMethod = sourceProperty.getReadMethod();
                var writeMethod = targetProperty.getWriteMethod();
                if (readMethod != null && writeMethod != null) {
                    try {
                        // Invoke read method to get the value of the source property
                        Object sourceValue = readMethod.invoke(source);
                        // Copy the value if it's not null
                        if (sourceValue != null) {
                            writeMethod.invoke(target, sourceValue);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Error copying properties: " + e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * Create a hashmap to store property descriptors
     * @param descriptors property descriptors
     * @return hashmap that key is property name and value is property descriptor
     */
    @NotNull
    private static HashMap<String, PropertyDescriptor> getPropertyDescriptorHashMap(PropertyDescriptor[] descriptors) {
        var targetPropsMap = new HashMap<String, PropertyDescriptor>();
        for (PropertyDescriptor targetProperty : descriptors) {
            targetPropsMap.put(targetProperty.getName(), targetProperty);
        }
        return targetPropsMap;
    }
}
