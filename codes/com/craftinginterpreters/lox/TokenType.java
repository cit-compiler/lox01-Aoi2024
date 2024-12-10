package com.craftinginterpreters.lox;

/**
 * Lox言語のトークンタイプを定義する列挙型。
 * 各トークンタイプは、ソースコード中の特定の種類の記号やキーワードを表します。
 */
enum TokenType {
    // シングルキャラクターのトークン
    LEFT_PAREN,    // '('
    RIGHT_PAREN,   // ')'
    LEFT_BRACE,    // '{'
    RIGHT_BRACE,   // '}'
    COMMA,         // ','
    DOT,           // '.'
    MINUS,         // '-'
    PLUS,          // '+'
    SEMICOLON,     // ';'
    SLASH,         // '/'
    STAR,          // '*'

    // 1文字または2文字のトークン
    BANG,          // '!'
    BANG_EQUAL,    // '!='
    EQUAL,         // '='
    EQUAL_EQUAL,   // '=='
    GREATER,       // '>'
    GREATER_EQUAL, // '>='
    LESS,          // '<'
    LESS_EQUAL,    // '<='

    // リテラル
    IDENTIFIER,    // 識別子（例: 変数名）
    STRING,        // 文字列リテラル（例: "lox"）
    NUMBER,        // 数値リテラル（例: 123, 45.67）

    // キーワード
    AND,           // 'and'
    CLASS,         // 'class'
    ELSE,          // 'else'
    FALSE,         // 'false'
    FUN,           // 'fun'
    FOR,           // 'for'
    IF,            // 'if'
    NIL,           // 'nil'
    OR,            // 'or'
    PRINT,         // 'print'
    RETURN,        // 'return'
    SUPER,         // 'super'
    THIS,          // 'this'
    TRUE,          // 'true'
    VAR,           // 'var'
    WHILE,         // 'while'

    // ファイルの終わりを示すトークン
    EOF            // ファイルの終端
}
