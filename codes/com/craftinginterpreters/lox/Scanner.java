package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

/**
 * ソースコードをトークンに分割するスキャナークラス。
 * トークンの種類に基づき、文字列を解析してリストを生成します。
 */
class Scanner {
    private final String source;                 // ソースコード全体
    private final List<Token> tokens = new ArrayList<>(); // 生成されたトークンのリスト
    private static final Map<String, TokenType> keywords; // キーワードを保持するマップ

    private int start = 0;       // 現在のトークンの開始位置
    private int current = 0;     // 現在の文字の位置
    private int line = 1;        // 現在の行番号

    static {
        // キーワードとその対応するトークンタイプのマッピング
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    Scanner(String source) {
        this.source = source;
    }

    /**
     * トークンのリストを生成する。
     * @return 生成されたトークンのリスト。
     */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // 次のトークンの開始位置を記録
            start = current;
            scanToken();
        }

        // ファイル終端のトークンを追加
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    /**
     * ソースコードを解析し、1つのトークンをスキャンする。
     */
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

            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;

            case '/':
                if (match('/')) {
                    // コメントの処理：行末までスキップ
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;

            case ' ':
            case '\r':
            case '\t':
                // 空白文字を無視
                break;

            case '\n':
                line++;
                break;

            case '"': string(); break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character.");
                }
                break;
        }
    }

    /**
     * 次の文字を取得し、現在位置を進める。
     * @return 現在の文字。
     */
    private char advance() {
        return source.charAt(current++);
    }

    /**
     * 次の文字が期待される文字かどうか確認し、一致する場合は進める。
     * @param expected 期待する文字
     * @return 一致した場合はtrue、そうでなければfalse。
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    /**
     * 現在の文字を確認する（進めない）。
     * @return 現在の文字。
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    /**
     * 次の文字を確認する（進めない）。
     * @return 次の文字。
     */
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * トークンをリストに追加する。
     * @param type トークンの種類
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    /**
     * トークンをリストに追加する（リテラル値を含む場合）。
     * @param type トークンの種類
     * @param literal トークンのリテラル値
     */
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * 数値トークンを処理する。
     */
    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            // 小数点を処理
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    /**
     * 文字列トークンを処理する。
     */
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // 終了文字を消費
        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    /**
     * 識別子やキーワードを処理する。
     */
    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    /**
     * 文字が数字かどうか判定。
     * @param c 判定する文字
     * @return 数字であればtrue。
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 文字がアルファベットまたはアンダースコアか判定。
     * @param c 判定する文字
     * @return 条件を満たせばtrue。
     */
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    /**
     * 文字がアルファベットまたは数字か判定。
     * @param c 判定する文字
     * @return 条件を満たせばtrue。
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    /**
     * ソースコードの終端に到達したか判定。
     * @return 終端であればtrue。
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }
}
