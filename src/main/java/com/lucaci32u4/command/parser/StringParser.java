package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class StringParser implements  ParameterParser<String> {
    protected final String defaultValue;
    protected final Supplier<Stream<String>> completions;

    public StringParser(String defaultValue, Supplier<Stream<String>> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    public StringParser(String defaultValue) {
        this(defaultValue, Stream::empty);
    }

    @Override
    public String parse(String str) throws ParseException {
        return str;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public Stream<String> completer(String partialString) {
        return completions.get().filter(s -> s.startsWith(partialString));
    }
}
