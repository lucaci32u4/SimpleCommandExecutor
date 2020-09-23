package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoubleParser implements ParameterParser<Double> {
    protected final Double defaultValue;
    protected final Supplier<DoubleStream> completions;

    /**
     * Constructs a double parser with default value
     * @param defaultValue default value
     */
    public DoubleParser(@Nullable Double defaultValue) {
        this(defaultValue, DoubleStream::empty);
    }

    /**
     * Constructs a double parser with default value and possible completions
     * @param defaultValue default value
     * @param completions possible completions
     */
    public DoubleParser(@Nullable Double defaultValue, @NotNull Supplier<DoubleStream> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    /**
     * Parses a string into a double
     * @param str the string to be parsed
     * @return the parsed double
     * @throws ParseException if the string does not represent a valid double
     */
    @Override
    public @Nullable Double parse(@NotNull String str) throws ParseException {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException nfex) {
            throw new ParseException("'" + str + "' is not an integer");
        }
    }

    /**
     * Returns the default value
     * @return the default value
     */
    @Override
    public @Nullable Double defaultValue() {
        return defaultValue;
    }

    /**
     * Provides completions for the given text fragment
     * @param partialString current text fragment
     * @return stream of possible completions
     */
    @Override
    public @NotNull Stream<String> completer(@NotNull String partialString) {
        return completions.get().mapToObj(Double::toString).filter(s -> s.startsWith(partialString));
    }
}

