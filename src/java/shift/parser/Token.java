package shift.parser;

import static shift.Utils.*;
import java.util.Map;
import static java.util.Map.entry;

public class Token {

    public static final int EOF = -1;

    public final int line, column;
    public final String value;
    public final Type type;

    public enum Type {
        stringLiteral,
        floatLiteral,
        intLiteral,
        identifier,
        symbol
    }

    public Token(String value, int line, int column, Type type) {
        this.value = value;
        this.line = line;
        this.column = column - value.length();
        this.type = type;
    }
    
    public Token(String value, int line, int column) {
        this(value, line, column, null);
    }

    public Token(String value) {
        this(value, 0, 0);
    }

    @Override
    public String toString() {
        return "[" + type + " \"" + value + "\" " + locationString() + "]";
    }

    public boolean equals(Token other) {
        return this.line == other.line && this.column == other.column && this.value.equals(other.value) && this.type == other.type;
    }

    public boolean equals(String otherValue) {
        return this.value.equals(otherValue);
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        if (this == obj) return true;

        switch (obj) {
            case Token token -> {
                return equals(token);
            }
            case String string -> {
                return equals(string);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode() + 31 * (line + 31 * column);
    }

    public String locationString() {
        return "(" + line + ':' + column + ')';
    }

    public boolean isStartBracket() {
        return value.equals("(") || value.equals("[") || value.equals("{");
    }

    public boolean isEndBracket() {
        return value.equals(")") || value.equals("]") || value.equals("}");
    }

    public Long longValue() {
        int first = value.charAt(0);
        if (!isDigit(first)) return null;
        
        int radix = 10;
        if (first == '0') {
            radix = switch (value.codePointAt(1) | ('a' - 'A')) {
                case 'x' -> 16;
                case 'o' -> 8;
                case 'b' -> 2;
                default -> 10;
            };
        }

        return Long.valueOf(value, radix);
    }

    public int getLiteralSize() {
        long n = longValue();
        return (int)Math.round(Math.log(n) / Math.log(2));
    }

    private static final Map<String, String> escapeSequences = Map.ofEntries(
        entry("n", "\n"),
        entry("t", "\t"),
        entry("r", "\r"),
        entry("b", "\b"),
        entry("f", "\f"),
        entry("\"", "\""),
        entry("\\", "\\"),
        entry("'", "'"),
        entry("a", "\u0007"),
        entry("v", "\u000B")
    );

    public String stringValue() {
        if (type != Type.stringLiteral) return null;
        String str = value.substring(1, value.length() - 1);
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\\') {
                StringBuilder escape = new StringBuilder();
                while (i + 1 < str.length() && isIdentifierPart(str.charAt(i + 1))) {
                    escape.append(str.charAt(++i));
                }
                String replacement = escapeSequences.get(escape.toString());
                if (replacement != null) {
                    res.append(replacement);
                } else {
                    res.append(c).append(escape);
                }
            } else {
                res.append(c);
            }
        }

        return res.toString();
    }
}

//