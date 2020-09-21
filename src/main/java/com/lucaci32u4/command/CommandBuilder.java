package com.lucaci32u4.command;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    protected String name = "";
    protected List<SubcommandBuilder> subcommands = new ArrayList<>();

    protected CommandBuilder() { }

    /**
     * Set the name of this command
     * @param name the name of this command
     * @return this
     */
    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Creates a subcommand and returns a reference to it
     * @return reference to created subcommand
     */
    public SubcommandBuilder subcommand() {
        SubcommandBuilder sub = new SubcommandBuilder(this);
        subcommands.add(sub);
        return sub;
    }

    /**
     * Finalize the command builder and return a constructed executor
     * @return command executor
     */
    public SimpleCommandExecutor endCommand() {
        return new SimpleCommandExecutor(this);
    }
}
