package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoubleParser implements ParameterParser<Double> {
    protected final Double defaultValue;
    protected final Supplier<DoubleStream> completions;

    public DoubleParser(Double defaultValue) {
        this(defaultValue, DoubleStream::empty);
    }

    public DoubleParser(Double defaultValue, Supplier<DoubleStream> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    @Override
    public Double parse(String str) throws ParseException {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException nfex) {
            throw new ParseException("'" + str + "' is not an integer");
        }
    }

    @Override
    public Double defaultValue() {
        return defaultValue;
    }

    @Override
    public Stream<String> completer(String partialString) {
        return completions.get().mapToObj(Double::toString).filter(s -> s.startsWith(partialString));
    }
}

