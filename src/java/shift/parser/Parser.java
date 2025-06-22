package shift.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import shift.ast.Node;

import static shift.Utils.*;

public class Parser {

    private Parser() {
        // Prevent instantiation
    }

    private static final String statementEnd = ";";

    public static Iterator<Statement> separateStatements(Iterator<Token> prog) {
        return new FunctionIterator<>(() -> {
            Statement statement = new Statement();
            while (prog.hasNext()) {
                Token token = prog.next();
                if (token.value.equals(statementEnd)) {
                    return statement;
                }
                statement.add(token);
            }
            return null;
        }, Objects::isNull);
    }

    public static Node parse(List<Token> prog) {
        Iterator<Statement> statements = separateStatements(prog.iterator());
        Node 
    }
}
