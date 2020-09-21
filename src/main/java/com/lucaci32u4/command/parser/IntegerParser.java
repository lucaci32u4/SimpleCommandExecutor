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

    /**
     * Constructs an integer parser with a default value
     * @param defaultValue
     */
    public IntegerParser(Integer defaultValue) {
        this(defaultValue, IntStream::empty);
    }

    /**
     * Constructs an integer parser with a default value and possible completions
     * @param defaultValue
     * @param completions
     */
    public IntegerParser(Integer defaultValue, Supplier<IntStream> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    /**
     * Parse an argument into an integer
     * @param str the string to be parsed
     * @return the parsed integer
     * @throws ParseException if str does not represent and integer
     */
    @Override
    public Integer parse(String str) throws ParseException {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException nfex) {
            throw new ParseException("'" + str + "' is not an integer");
        }
    }

    /**
     * Returns the default value
     * @return the default value
     */
    @Override
    public Integer defaultValue() {
        return defaultValue;
    }

    /**
     * Provides possible completions for the given fragment
     * @param partialString current text fragment
     * @return stream of possible completions
     */
    @Override
    public Stream<String> completer(String partialString) {
        if (partialString.matches(numberMatcher.pattern())) {
            return Stream.concat(
                    completions.get().mapToObj(Integer::toString).filter(s -> s.startsWith(partialString)),
                    partialString.equals("") ? Stream.empty() : Stream.of(partialString));
        }
        return Stream.empty();
    }
}
