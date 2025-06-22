package shift.parser;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static shift.Utils.*;

public class Tokenizer implements Iterator<Token> {
    private static final int EOF = -1;

    private static final String[] symbols = {
        "+", "-", "*", "/", "%", "$+", "$",
        "++", "--",
        "==", "!=", ">", "<", ">=", "<=",
        "&&", "||", "??", "!",
        "&", "|", "^", "~", "<<", ">>", ">>>",
        "=", "#",
        "(", ")", "[", "]", "{", "}",
        ",", ";", ":", ".", "?", ".?",
        "..", "..&", "..->",
        "->", "=>", "@",
    };

    private static final List<Set<String>> symbolSubstrings;

    static {
        symbolSubstrings = new ArrayList<>();
        int maxLength = Arrays.stream(symbols).mapToInt(String::length).max().orElse(0);

        for (int i = 1; i <= maxLength; i++) {
            Set<String> substringSet = new HashSet<>();
            for (String symbol : symbols) {
                if (symbol.length() >= i) {
                    substringSet.add(symbol.substring(0, i));
                }
            }
            symbolSubstrings.add(substringSet);
        }
    }

    private static final Set<Integer> symbolStarts = symbolSubstrings.get(0).stream()
        .mapToInt(s -> s.charAt(0))
        .collect(HashSet::new, HashSet::add, HashSet::addAll)
    ;

    private File file = null;
    private int line = 1, column = 1;

    private Reader r;
    private int next = -1;
    private int nextnext = -1;

    public Tokenizer(File file) throws FileNotFoundException {
        this(new FileReader(file));
        this.file = file;
    }
    
    public Tokenizer(Reader reader) {
        try {
            r = reader;
            next = r.read();
            nextnext = r.read();
        } catch (IOException e) {

        }
    }

    private int read() throws IOException {
        int res = next;
        next = nextnext;
        nextnext = r.read();
        
        if (res == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        
        if (res == EOF) {
            throw new EOFException();
        }
        
        return res;
    }

    @Override
    public boolean hasNext() {
        try {
            while ((next == ' ' || next == '\n') && next != EOF) {
                read();
            }
    
            return next != EOF;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Token next() {

        StringBuilder res = new StringBuilder();
        int startLine, startColumn;

        try {
            while (next == ' ' || next == '\n') {
                read();
            }

            int c = read();

            // Identifier
            if (isIdentifierStart(c)) {
                startLine = line;
                startColumn = column;

                res.appendCodePoint(c);
                while (isIdentifierPart(next)) {
                    res.appendCodePoint(read());
                }

                return new Token(res.toString(), startLine, startColumn, Token.Type.identifier);
            }

            // String literal
            if (c == '"') {
                startLine = line;
                startColumn = column;
                
                res.appendCodePoint(c);
                while (next != '"') {
                    res.appendCodePoint(c = read());
                    if (c == '\\' && next == '"') {
                        res.appendCodePoint(read());
                        res.appendCodePoint(read());
                    }

                    if (c == EOF) {
                        throw new IllegalStateException("Unterminated string literal at line " + line + ", column " + column);
                    }
                }
                res.appendCodePoint(read());

                return new Token(res.toString(), startLine, startColumn, Token.Type.stringLiteral);
            }

            // Integer or float literal
            if (isDigit(c)) {
                startLine = line;
                startColumn = column;
                
                boolean isFloat = false;

                res.appendCodePoint(c);
                while (isIdentifierPart(next)) {
                    res.appendCodePoint(c = read());
                }

                if (next == '.' && isDigit(nextnext)) {
                    isFloat = true;

                    res.appendCodePoint(read());
                    while (isIdentifierPart(next)) {
                        res.appendCodePoint(c = read());
                    }
                }

                if (c == 'e' && (next == '+' || next == '-') && isDigit(nextnext)) {
                    isFloat = true;

                    res.appendCodePoint(read());
                    while (isIdentifierPart(next)) {
                        res.appendCodePoint(read());
                    }
                }

                return new Token(res.toString(), startLine, startColumn, isFloat ? Token.Type.floatLiteral : Token.Type.intLiteral);
            }

            if (symbolStarts.contains(c)) {
                startLine = line;
                startColumn = column;
                res.appendCodePoint(c);

                int len = 1;
                while (len < symbolSubstrings.size() && symbolSubstrings.get(len++).contains(res.toString() + (char)next)) {
                    res.appendCodePoint(read());
                }

                return new Token(res.toString(), startLine, startColumn, Token.Type.symbol);
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }

    public static void main(String[] args) {
        Iterable<Statement> it = () -> Parser.separateStatements(new Tokenizer(new StringReader("int a = 5; String b = \"Hello\"; if (a > 0) { a++; } else { b = \"World\"; }")));
        it.forEach(s -> System.out.println(s.toString() + s.isVariableDeclaration()));
    }
}