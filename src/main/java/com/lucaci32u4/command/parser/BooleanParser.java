package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BooleanParser implements ParameterParser<Boolean> {
    private final boolean defaultValue;
    private final String trueWord;
    private final String falseWord;

    /**
     * Constructs a boolean parser with default value and replacement words for 'true' and 'false'
     * @param defaultValue the default value
     * @param trueWord replacement word for 'true'
     * @param falseWord replacement word for 'true'
     */
    public BooleanParser(boolean defaultValue, @NotNull String trueWord, @NotNull String falseWord) {
        this.defaultValue = defaultValue;
        this.trueWord = trueWord;
        this.falseWord = falseWord;
    }

    /**
     * Constructs a boolean parser with default value
     * @param defaultValue the default value
     */
    public BooleanParser(boolean defaultValue) {
        this(defaultValue, "true", "false");
    }

    @Override
    public @Nullable Boolean parse(@NotNull String str) throws ParseException {
        if (str.equalsIgnoreCase(trueWord)) return true;
        if (str.equalsIgnoreCase(falseWord)) return false;
        throw new ParseException("'" + str + "' is not a valid value. Possible values: '" + trueWord + "', '" + falseWord + "'");
    }

    @Override
    public @Nullable Boolean defaultValue() {
        return defaultValue;
    }

    @Override
    public @NotNull Stream<String> completer(@NotNull String partialString) {
        return Stream.of(trueWord, falseWord).filter(s -> s.startsWith(partialString));
    }
}
