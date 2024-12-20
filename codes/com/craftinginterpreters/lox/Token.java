package com.craftinginterpreters.lox;

class Token {
  final TokenType type;  // トークンタイプ
  final String lexeme;   // トークンの文字列（元のコード）
  final Object literal;  // リテラル値（例えば数値や文字列）
  final int line;        // トークンが出現した行番号

  // コンストラクタでトークンの情報を初期化
  Token(TokenType type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line;
  }

  // トークンの情報を文字列として返す
  @Override
  public String toString() {
    return type + " " + lexeme + " " + literal;
  }
}
