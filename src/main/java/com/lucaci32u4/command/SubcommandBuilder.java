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

    /**
     * Sets the name of this subcommand
     * @param name name
     * @return this
     */
    public SubcommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets whether explicit parameters are used.
     * @param enable true of explicit parameters are used, otherwise (for implicit parameters) false
     * @return this
     */
    public SubcommandBuilder explicitParameters(boolean enable) {
        explicitParameters = enable;
        return this;
    }

    /**
     * Creates a new parameter
     * @param name name of the parameter
     * @param parser the parser to be used to parse and provie tab completion
     * @return this
     */
    public SubcommandBuilder parameter(String name, ParameterParser<?> parser) {
        params.put(name, parser);
        return this;
    }

    /**
     * Finalize this subcommand and return to parent command builder
     * @return parent command builder
     */
    public CommandBuilder endSubcommand() {
        return commandBuilder;
    }
}
