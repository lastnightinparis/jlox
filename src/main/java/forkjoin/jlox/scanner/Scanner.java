package forkjoin.jlox.scanner;

import static forkjoin.jlox.tokens.TokenType.BANG;
import static forkjoin.jlox.tokens.TokenType.BANG_EQUAL;
import static forkjoin.jlox.tokens.TokenType.COMMA;
import static forkjoin.jlox.tokens.TokenType.DOT;
import static forkjoin.jlox.tokens.TokenType.EOF;
import static forkjoin.jlox.tokens.TokenType.LEFT_BRACE;
import static forkjoin.jlox.tokens.TokenType.LEFT_PAREN;
import static forkjoin.jlox.tokens.TokenType.MINUS;
import static forkjoin.jlox.tokens.TokenType.PLUS;
import static forkjoin.jlox.tokens.TokenType.RIGHT_BRACE;
import static forkjoin.jlox.tokens.TokenType.RIGHT_PAREN;
import static forkjoin.jlox.tokens.TokenType.SEMICOLON;
import static forkjoin.jlox.tokens.TokenType.*;

import forkjoin.jlox.Lox;
import forkjoin.jlox.tokens.Token;
import forkjoin.jlox.tokens.TokenType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            default:
                Lox.error(line, "Unexpected character.");
                break;
        }
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

}
