package xyz.lucaci32u4.command.reader;

import xyz.lucaci32u4.command.ParseException;
import xyz.lucaci32u4.command.parser.ParameterParser;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public interface SubcommandReader {
    /**
     * Read argument list and parse into argument map
     * @param args argument queue
     * @param parsers map of parsers to use for each argument
     * @return map of arguments and their values
     * @throws ParseException if the arguments cannot be parsed
     */
    @NotNull
    ParameterMap readArguments(@NotNull Queue<String> args, @NotNull Map<String, ParameterParser<?>> parsers) throws ParseException;

    /**
     * Read argument list and provide completion for last argument
     * @param args argument queue
     * @param parsers map of parsers to use for each argument
     * @return possible completions
     */
    @NotNull
    Stream<String> completer(@NotNull Queue<String> args, @NotNull Map<String, ParameterParser<?>> parsers);
}
