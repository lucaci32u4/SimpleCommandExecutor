package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface ParameterParser<T> {
    /**
     * Parses the strings and returns the object representation
     * @param str the string to be parsed
     * @return object representatin of string
     * @throws ParseException if parsing cannot be done
     */
    @Nullable
    T parse(@NotNull String str) throws ParseException;

    /**
     * Default value to be used when no user input is given
     * @return the default value
     */
    @Nullable
    T defaultValue();

    /**
     * Computes possible suggestions for tab-completion. partialString is the current fragment the user entered.
     * @param partialString current text fragment
     * @return array of possible completions
     */
    @NotNull
    Stream<String> completer(@NotNull String partialString);
}
