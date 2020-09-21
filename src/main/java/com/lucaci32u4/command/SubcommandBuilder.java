package com.lucaci32u4.command;

import com.lucaci32u4.command.parser.ParameterParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubcommandBuilder {
    private final CommandBuilder commandBuilder;
    protected final Map<String, ParameterParser<?>> params = new LinkedHashMap<>();
    protected boolean explicitParameters = true;
    protected String name;

    protected SubcommandBuilder(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public SubcommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SubcommandBuilder explicitParameters(boolean enable) {
        explicitParameters = enable;
        return this;
    }

    public SubcommandBuilder parameter(String name, ParameterParser<?> parser) {
        params.put(name, parser);
        return this;
    }

    public CommandBuilder endSubcommand() {
        return commandBuilder;
    }
}
