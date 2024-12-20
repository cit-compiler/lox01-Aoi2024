package com.craftinginterpreters.lox;

enum TokenType {
  // 1�����̃g�[�N��
  LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
  COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

  // 1�����܂���2�����̃g�[�N��
  BANG, BANG_EQUAL,
  EQUAL, EQUAL_EQUAL,
  GREATER, GREATER_EQUAL,
  LESS, LESS_EQUAL,

  // ���e����
  IDENTIFIER, STRING, NUMBER,

  // �L�[���[�h
  AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
  PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

  // �t�@�C���̏I���iEOF�j
  EOF
}
