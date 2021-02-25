package xyz.lucaci32u4.command;

import xyz.lucaci32u4.command.parser.ParameterParser;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubcommandBuilder {
    private final CommandBuilder commandBuilder;
    protected final Map<String, ParameterParser<?>> params = new LinkedHashMap<>();
    protected boolean explicitParameters = true;
    protected String name = "";

    protected SubcommandBuilder(@NotNull CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    /**
     * Sets the name of this subcommand
     * @param name name
     * @return this
     */
    @NotNull
    public SubcommandBuilder name(@NotNull String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets whether explicit parameters are used.
     * @param enable true of explicit parameters are used, otherwise (for implicit parameters) false
     * @return this
     */
    @NotNull
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
    @NotNull
    public SubcommandBuilder parameter(@NotNull String name, @NotNull ParameterParser<?> parser) {
        params.put(name, parser);
        return this;
    }

    /**
     * Finalize this subcommand and return to parent command builder
     * @return parent command builder
     */
    @NotNull
    public CommandBuilder endSubcommand() {
        return commandBuilder;
    }
}
