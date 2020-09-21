package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.stream.Stream;

public class BooleanParser implements ParameterParser<Boolean> {
    private final boolean defaultValue;
    private final String trueWord;
    private final String falseWord;

    public BooleanParser(boolean defaultValue, String trueWord, String falseWord) {
        this.defaultValue = defaultValue;
        this.trueWord = trueWord;
        this.falseWord = falseWord;
    }

    public BooleanParser(boolean defaultValue) {
        this(defaultValue, "true", "false");
    }

    @Override
    public Boolean parse(String str) throws ParseException {
        if (str.equalsIgnoreCase(trueWord)) return true;
        if (str.equalsIgnoreCase(falseWord)) return false;
        throw new ParseException("'" + str + "' is not a valid value. Possible values: '" + trueWord + "', '" + falseWord + "'");
    }

    @Override
    public Boolean defaultValue() {
        return defaultValue;
    }

    @Override
    public Stream<String> completer(String partialString) {
        return Stream.of(trueWord, falseWord).filter(s -> s.startsWith(partialString));
    }
}
