package com.lucaci32u4.command.reader;

import com.lucaci32u4.command.ParseException;
import com.lucaci32u4.command.parser.ParameterParser;

import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class ImplicitReader implements SubcommandReader {
    @Override
    public ArgumentMap readArguments(Queue<String> args, Map<String, ParameterParser<?>> parsers) throws ParseException {
        ArgumentMap argmap = new ArgumentMap();
        parsers.entrySet().stream()
                .map(e -> new ResultPair(e.getKey(), args.isEmpty() ? e.getValue().defaultValue() : e.getValue().parse(args.remove())))
                .forEachOrdered(rp -> argmap.add(rp.arg, rp.value));
        return argmap;
    }

    @Override
    public Stream<String> completer(Queue<String> args, Map<String, ParameterParser<?>> parsers) {
        if (args.size() > parsers.size() || args.size() < 1) {
            return Stream.empty();
        }
        int arglen = args.size();
        while (args.size() != 1) args.remove();
        return parsers.entrySet().stream().skip(arglen - 1).map(Map.Entry::getValue)
                .findFirst().map(pps -> pps.completer(args.peek()))
                .orElse(Stream.empty());
    }

    private class ResultPair {
        public final String arg;
        public final Object value;

        public ResultPair(String arg, Object value) {
            this.arg = arg;
            this.value = value;
        }
    }
}
