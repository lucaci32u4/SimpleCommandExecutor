package com.lucaci32u4.command.parser;

import com.lucaci32u4.command.ParseException;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EnumParser extends StringParser {

    /**
     * Constructs an enum/string parser with default values and possible options
     * @param defaultValue default value
     * @param options possible options
     */
    public EnumParser(String defaultValue, Supplier<Stream<String>> options) {
        super(defaultValue, options);
    }

    /**
     * Tries to match str with possible options
     * @param str the string to be parsed
     * @return str
     * @throws ParseException if a match cannot be made
     */
    @Override
    public String parse(String str) throws ParseException {
        return completions.get().filter(c -> c.equals(str))
                .findFirst().orElseThrow(() -> new ParseException("'" + str + "' is not a valid value"));
    }
}
