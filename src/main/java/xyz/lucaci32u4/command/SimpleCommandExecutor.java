package xyz.lucaci32u4.command;

import xyz.lucaci32u4.command.parser.ParameterParser;
import xyz.lucaci32u4.command.reader.ParameterMap;
import xyz.lucaci32u4.command.reader.ExplicitReader;
import xyz.lucaci32u4.command.reader.ImplicitReader;
import xyz.lucaci32u4.command.reader.SubcommandReader;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleCommandExecutor implements CommandExecutor, TabCompleter {
    private final Map<String, SubcommandReader> readers;
    private final Map<String, Map<String, ParameterParser<?>>> subcmd;
    private final String commandName;
    private final String usage;
    private Map<String, BiConsumer<CommandSender, ParameterMap>> handlers;

    protected SimpleCommandExecutor(CommandBuilder builder) {
        commandName = builder.name;
        SubcommandReader explicitReader = new ExplicitReader();
        SubcommandReader implicitReader = new ImplicitReader();
        readers = builder.subcommands.stream().collect(Collectors.toMap(s -> s.name, s -> s.explicitParameters ? explicitReader : implicitReader));
        subcmd = builder.subcommands.stream().collect(Collectors.toMap(s -> s.name, s -> new LinkedHashMap<>(s.params)));
        handlers = Collections.emptyMap();
        usage = builder.subcommands.stream().map(s -> s.name + " " + (s.explicitParameters
                    ? s.params.keySet().stream().map(k -> k + " <>")
                    : s.params.keySet().stream().map(k -> "<" + k + ">")
                ).collect(Collectors.joining(" "))).map(s -> "        " + s)
                .collect(Collectors.joining("\n", "Usage: /" + commandName + "\n", ""));
    }

    /**
     * Execute a command
     * @param commandSender sender
     * @param command command
     * @param alias alias used
     * @param rawArgs list of arguments
     * @return whether the command has executed successfully
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] rawArgs) {
        if (alias.equals(commandName)) {
            Queue<String> args = makeArgumentQueue(rawArgs);
            Optional<String> first = findSubcommand(args).findFirst();
            if (!first.isPresent()) {
                return false;
            }
            String subcommandName = first.get();
            try {
                ParameterMap argmap = readers.get(subcommandName).readArguments(args, subcmd.get(subcommandName));
                if (handlers.containsKey(subcommandName)) {
                    handlers.get(subcommandName).accept(commandSender, argmap);
                }
                return true;
            } catch (ParseException parseException) {
                commandSender.sendMessage(parseException.getMessage() + ".");
                return false;
            }
        }
        return false;
    }

    /**
     * Provide tab completion
     * @param commandSender sender
     * @param command command
     * @param alias alias used
     * @param rawArgs list of arguments
     * @return list of possible completions
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] rawArgs) {
        if (alias.equals(commandName)) {
            Queue<String> args = makeArgumentQueue(rawArgs);
            Stream<String> possibleSubcommands = findSubcommand(args);
            if (args.isEmpty()) {
                return possibleSubcommands.collect(Collectors.toList());
            }
            Optional<String> first = possibleSubcommands.findFirst();
            if (!first.isPresent()) {
                return Collections.emptyList();
            }
            String subcommandName = first.get();
            return readers.get(subcommandName).completer(args, subcmd.get(subcommandName)).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /**
     * Find all possible subcommands that match
     * @param args argument queue
     * @return stream of subcommands name
     */
    @NotNull
    private Stream<String> findSubcommand(@NotNull Queue<String> args) {
        Stream<String> options = Stream.empty();
        try {
            String candidate = args.remove();
            options = subcmd.keySet().stream().filter(sub -> sub.startsWith(candidate)).sorted(Comparator.comparing(opt -> opt.equals(candidate) ? 0 : 1));
        } catch (NoSuchElementException ignored) {

        }
        return options;
    }

    @NotNull
    private Queue<String> makeArgumentQueue(@NotNull String[] rawArgs) {
        return Arrays.stream(rawArgs).map(String::toLowerCase).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Set the handler (consumer) for successful commands sent
     * @param handler Handler object
     * @param clazz Handler class object, used for extracting annotated methods
     * @return this
     */
    public <T> @NotNull SimpleCommandExecutor setHandler(@NotNull T handler, @NotNull Class<T> clazz) {
        Map<String, BiConsumer<CommandSender, ParameterMap>> handlers = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            SubcommandHandler sch = method.getAnnotation(SubcommandHandler.class);
            if (sch != null) {
                if (method.getParameterCount() == 2) {
                    if (method.getParameterTypes()[0].equals(CommandSender.class) && method.getParameterTypes()[1].equals(ParameterMap.class)) {
                        String sub = sch.value();
                        if (subcmd.containsKey(sub)) {
                            handlers.put(sub, (snd, arg) -> {
                                try {
                                    method.invoke(handler, snd, arg);
                                } catch (Exception e) {
                                    throw new IllegalStateException("Command handler error", e);
                                }
                            });
                        }
                    }
                }
            }
        }
        this.handlers = handlers;
        return this;
    }

    /**
     * Automatically set itself as executor for this command and overwrite the usage string
     * @param plugin auto-set
     * @param overwriteUsage whether to overwrite the usage string
     * @return this
     */
    public @NotNull SimpleCommandExecutor selfInstall(@NotNull JavaPlugin plugin, boolean overwriteUsage) {
        PluginCommand cmd = plugin.getCommand(commandName);
        if (cmd == null) {
            throw new CommandRegisterException("Command '" + commandName + "' not found");
        }
        cmd.setExecutor(this);
        if (overwriteUsage) {
            cmd.setUsage(usage);
        }
        return this;
    }

    public @NotNull String getUsage() {
        return usage;
    }

    /**
     * Build a new command
     * @return command builder
     */
    @NotNull
    public static CommandBuilder build() {
        return new CommandBuilder();
    }


    public static class CommandRegisterException extends RuntimeException {
        public CommandRegisterException(String message) {
            super(message);
        }
    }
}
