package com.lucaci32u4.command;

import org.jetbrains.annotations.NotNull;

public class ParseException extends RuntimeException {
    public ParseException(@NotNull String message) {
        super(message);
    }
}
