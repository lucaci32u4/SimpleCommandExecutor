package xyz.lucaci32u4.command.parser;

import xyz.lucaci32u4.command.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class StringParser implements  ParameterParser<String> {
    protected final String defaultValue;
    protected final Supplier<Stream<String>> completions;

    /**
     * Constructs a String parser with a default value and possible completions
     * @param defaultValue the default value
     * @param completions possible completions
     */
    public StringParser(@Nullable String defaultValue, @NotNull Supplier<Stream<String>> completions) {
        this.defaultValue = defaultValue;
        this.completions = completions;
    }

    /**
     * Constructs a String parser with a default value and po completions
     * @param defaultValue the default value
     */
    public StringParser(@Nullable String defaultValue) {
        this(defaultValue, Stream::empty);
    }

    /**
     * Parse a string
     * @param str the string to be parsed
     * @return the string
     * @throws ParseException never
     */
    @Override
    public @Nullable String parse(@NotNull String str) throws ParseException {
        return str;
    }

    /**
     * Returns the default string
     * @return the default string
     */
    @Override
    public String defaultValue() {
        return defaultValue;
    }

    /**
     * Creates completions for given current string
     * @param partialString current text fragment
     * @return possible completions
     */
    @Override
    public @NotNull Stream<String> completer(@NotNull String partialString) {
        return completions.get().filter(s -> s.startsWith(partialString));
    }
}
