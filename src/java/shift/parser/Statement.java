package shift.parser;

import static shift.Utils.*;

import java.util.ArrayList;
import java.util.Set;

public class Statement extends ArrayList<Token> {

    public static final Set<String> invalidTypeNames = Set.of(
        "for", "while", "if", "else"
    );

    @Override
    public String toString() {
        return String.join(" ", stream().<String>map(t -> t.value).toList()) + ';';
    }

    public boolean isVariableDeclaration() {
        return 
            size() >= 2 &&
            !invalidTypeNames.contains(get(0).value) &&
            isIdentifier(get(1).value)
        ;
    }


}
