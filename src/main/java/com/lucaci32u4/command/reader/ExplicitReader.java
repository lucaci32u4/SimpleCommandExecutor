package com.lucaci32u4.command.reader;

import com.lucaci32u4.command.ParseException;
import com.lucaci32u4.command.parser.ParameterParser;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class ExplicitReader implements SubcommandReader {

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
        while (!args.isEmpty()) {
            String name = args.remove();
            if (args.isEmpty()) {
                throw new ParseException("Missing value for argument '" + name + "'");
            }
            String val = args.remove();
            if (!parsers.containsKey(name)) {
                throw new ParseException("Unknown argument '" + name + "'");
            }
            argmap.add(name, parsers.get(name).parse(val));
        }
        parsers.entrySet().stream().filter(e -> !argmap.has(e.getKey())).
                forEachOrdered(e -> argmap.add(e.getKey(), e.getValue().defaultValue()));
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
        Set<String> existingParameters = new HashSet<>();
        while (args.size() > 2) {
            existingParameters.add(args.remove());
            args.remove();
        }
        if (args.size() == 2) {
            String name = args.remove();
            String val = args.remove();
            ParameterParser<?> pps = parsers.get(name);
            if (pps != null) {
                return pps.completer(val);
            }
            return Stream.empty();
        }
        if (args.size() == 1) {
            String name = args.remove();
            return parsers.keySet().stream().filter(n -> !existingParameters.contains(n)).filter(n -> n.startsWith(name));
        }
        return Stream.empty();
    }
}
