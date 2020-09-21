package com.lucaci32u4.command.reader;

import com.lucaci32u4.command.ParseException;
import com.lucaci32u4.command.parser.ParameterParser;

import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public interface SubcommandReader {
    /**
     * Read argument list and parse into argument map
     * @param args argument list
     * @param parsers map of parsers to use for each argument
     * @return argument map
     * @throws ParseException if the arguments cannot be parsed
     */
    ArgumentMap readArguments(Queue<String> args, Map<String, ParameterParser<?>> parsers) throws ParseException;

    /**
     * Read argument list and provide completion for last argument
     * @param args argument list
     * @param parsers map of parsers to use for each argument
     * @return possible completions
     */
    Stream<String> completer(Queue<String> args, Map<String, ParameterParser<?>> parsers);
}
