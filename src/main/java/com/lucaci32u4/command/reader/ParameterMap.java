package com.lucaci32u4.command.reader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ParameterMap {
    private final Map<String, Object> map = new HashMap<>();

    /**
     * Add a new value for a parameter
     * @param name the parameter
     * @param value the value
     */
    protected void add(@NotNull String name, @Nullable Object value) {
        map.put(name, value);
    }

    /**
     * Check if a parameter has a value set
     * @param name the parameter
     * @return whether the parameter has the value set
     */
    public boolean has(@NotNull String name) {
        return map.containsKey(name);
    }

    /**
     * Get the value of a parameter and cast it to the desired class
     * @param name the parameter
     * @param classOrInterface the class to cast to
     * @return casted value of the parameter, if it exists. Else null.
     */
    @Nullable
    public <T> T get(@NotNull String name, @NotNull Class<T> classOrInterface) {
        Object rawValue = map.get(name);
        if (rawValue != null) {
            if (classOrInterface.isInstance(rawValue)) {
                return classOrInterface.cast(rawValue);
            }
        }
        return null;
    }
}
