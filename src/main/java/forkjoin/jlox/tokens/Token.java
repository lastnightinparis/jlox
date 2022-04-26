package forkjoin.jlox.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Token {
    private final TokenType type;
    private final String lexeme;
    private final Object literal;
    private final int line;

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
