package com.lucaci32u4.command;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    protected String name = "";
    protected List<SubcommandBuilder> subcommands = new ArrayList<>();

    protected CommandBuilder() { }

    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SubcommandBuilder subcommand() {
        SubcommandBuilder sub = new SubcommandBuilder(this);
        subcommands.add(sub);
        return sub;
    }

    public SimpleCommandExecutor endCommand() {
        return new SimpleCommandExecutor(this);
    }
}
