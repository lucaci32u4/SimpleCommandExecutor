package com.lucaci32u4.command.reader;

import com.lucaci32u4.command.ParseException;
import com.lucaci32u4.command.parser.ParameterParser;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class ImplicitReader implements SubcommandReader {

    /**
     * Parses arguments and constructs a map containing their values.
     * @param args argument queue
     * @param parsers map of parsers to use for each argument
     * @return map of arguments and their values
     * @throws ParseException if any of the arguments cannot be parsed
     */
    @Override
    public @NotNull ParameterMap readArguments(@NotNull Queue<String> args, @NotNull Map<String, ParameterParser<?>> parsers) throws ParseException {
        ParameterMap argmap = new ParameterMap();
        parsers.entrySet().stream()
                .map(e -> new ResultPair(e.getKey(), args.isEmpty() ? e.getValue().defaultValue() : e.getValue().parse(args.remove())))
                .forEachOrdered(rp -> argmap.add(rp.arg, rp.value));
        return argmap;
    }

    /**
     * Provides completions for the last argument in queue
     * @param args argument queue
     * @param parsers map of parsers to use for each argument
     * @return possible completions
     */
    @Override
    public @NotNull Stream<String> completer(@NotNull Queue<String> args, @NotNull Map<String, ParameterParser<?>> parsers) {
        if (args.size() > parsers.size() || args.size() < 1) {
            return Stream.empty();
        }
        int arglen = args.size();
        while (args.size() != 1) args.remove();
        return parsers.entrySet().stream().skip(arglen - 1).map(Map.Entry::getValue)
                .findFirst().map(pps -> pps.completer(args.peek()))
                .orElse(Stream.empty());
    }

    private static class ResultPair {
        public final String arg;
        public final Object value;

        public ResultPair(String arg, Object value) {
            this.arg = arg;
            this.value = value;
        }
    }
}
