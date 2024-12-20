package com.craftinginterpreters.lox;

enum TokenType {
  // 1文字のトークン
  LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
  COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

  // 1文字または2文字のトークン
  BANG, BANG_EQUAL,
  EQUAL, EQUAL_EQUAL,
  GREATER, GREATER_EQUAL,
  LESS, LESS_EQUAL,

  // リテラル
  IDENTIFIER, STRING, NUMBER,

  // キーワード
  AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
  PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

  // ファイルの終わり（EOF）
  EOF
}
