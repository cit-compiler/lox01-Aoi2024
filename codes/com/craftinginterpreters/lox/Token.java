package com.craftinginterpreters.lox;

class Token {
  final TokenType type;  // �g�[�N���^�C�v
  final String lexeme;   // �g�[�N���̕�����i���̃R�[�h�j
  final Object literal;  // ���e�����l�i�Ⴆ�ΐ��l�╶����j
  final int line;        // �g�[�N�����o�������s�ԍ�

  // �R���X�g���N�^�Ńg�[�N���̏���������
  Token(TokenType type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line;
  }

  // �g�[�N���̏��𕶎���Ƃ��ĕԂ�
  @Override
  public String toString() {
    return type + " " + lexeme + " " + literal;
  }
}
