package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntegerParser implements ParameterParser<Integer> {
    protected Pattern numberMatcher = Pattern.compile("([+-][0-9])?[0-9]*");

    protected final Integer defaultValue;
    protected final Supplier<IntStream> completions;

    public IntegerParser(Integer defaultValue) {
        this(defaultValue, IntStream::empty);
    }

    public IntegerParser(Integer defaultValue, Supplier<IntStream> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    @Override
    public Integer parse(String str) throws ParseException {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException nfex) {
            throw new ParseException("'" + str + "' is not an integer");
        }
    }

    @Override
    public Integer defaultValue() {
        return defaultValue;
    }

    @Override
    public Stream<String> completer(String partialString) {
        if (partialString.matches(numberMatcher.pattern())) {
            return Stream.concat(
                    completions.get().mapToObj(Integer::toString).filter(s -> s.startsWith(partialString)),
                    partialString.equals("") ? Stream.empty() : Stream.of(partialString));
        }
        return null;
    }
}
