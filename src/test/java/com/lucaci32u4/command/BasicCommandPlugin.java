package com.lucaci32u4.command;

import com.lucaci32u4.command.parser.BooleanParser;
import com.lucaci32u4.command.parser.DoubleParser;
import com.lucaci32u4.command.parser.EnumParser;
import com.lucaci32u4.command.parser.IntegerParser;
import com.lucaci32u4.command.reader.ParameterMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicCommandPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        SimpleCommandExecutor.build().name("shape").subcommand()
                .name("sphere").explicitParameters(true)
                .parameter("-radius", new IntegerParser(1, () -> IntStream.range(1, 10).map(i -> i * 10)))
                .parameter("-density", new DoubleParser(1.0))
                .endSubcommand().subcommand()
                .name("cube").explicitParameters(false)
                .parameter("size", new IntegerParser(1, () -> IntStream.rangeClosed(1, 10)))
                .parameter("fill", new BooleanParser(true, "fill", "empty"))
                .parameter("color", new EnumParser("none", () -> Stream.of("red", "blue", "green", "yellow", "none")))
                .endSubcommand().endCommand()
                .setHandler(this, BasicCommandPlugin.class)
                .selfInstall(this, true);
    }

    @SubcommandHandler("sphere")
    public void onSphere(CommandSender sender, ParameterMap param) {
        sender.sendMessage(printCommand("sphere", param));
    }

    @SubcommandHandler("cube")
    public void onCube(CommandSender sender, ParameterMap param) {
        sender.sendMessage(printCommand("cube", param));
    }

    private String printCommand(String cmdName, ParameterMap param) {
        StringBuilder s =  new StringBuilder();
        s.append("Received command '" + cmdName + "'\n");
        param.forEach((p, v) -> s.append("Parameter ").append(p).append(": ").append(v.toString()).append("\n"));
        return s.toString();

    }
}
