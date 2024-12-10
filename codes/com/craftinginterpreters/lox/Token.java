package com.craftinginterpreters.lox;

/**
 * トークンを表すクラス。
 */
class Token {
    final TokenType type;      // トークンの種類
    final String lexeme;       // トークンの元の文字列
    final Object literal;      // トークンのリテラル値
    final int line;            // トークンが出現した行番号

    /**
     * コンストラクタ（4引数バージョン）
     * @param type トークンの種類
     * @param lexeme トークンの元の文字列
     * @param literal トークンのリテラル値
     * @param line トークンが出現した行番号
     */
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
