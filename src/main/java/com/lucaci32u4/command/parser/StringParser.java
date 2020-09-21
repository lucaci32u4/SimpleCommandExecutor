package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class StringParser implements  ParameterParser<String> {
    protected final String defaultValue;
    protected final Supplier<Stream<String>> completions;

    /**
     * Constructs a String parser with a default value and possible completions
     * @param defaultValue
     * @param completions
     */
    public StringParser(String defaultValue, Supplier<Stream<String>> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    /**
     * Constructs a String parser with a default value and po completions
     * @param defaultValue
     */
    public StringParser(String defaultValue) {
        this(defaultValue, Stream::empty);
    }

    /**
     * Parse a string
     * @param str the string to be parsed
     * @return the string
     * @throws ParseException never
     */
    @Override
    public String parse(String str) throws ParseException {
        return str;
    }

    /**
     * Returns the default string
     * @return the default string
     */
    @Override
    public String defaultValue() {
        return defaultValue;
    }

    /**
     * Creates completions for given current string
     * @param partialString current text fragment
     * @return possible completions
     */
    @Override
    public Stream<String> completer(String partialString) {
        return completions.get().filter(s -> s.startsWith(partialString));
    }
}
