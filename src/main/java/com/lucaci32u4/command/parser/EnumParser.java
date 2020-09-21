package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EnumParser extends StringParser {

    public EnumParser(String defaultValue, Supplier<Stream<String>> options) {
        super(defaultValue, options);
    }

    public EnumParser(String defaultValue) {
        super(defaultValue);
    }

    @Override
    public String parse(String str) throws ParseException {
        return completions.get().filter(c -> c.equals(str))
                .findFirst().orElseThrow(() -> new ParseException("'" + str + "' is not a valid value"));
    }
}
