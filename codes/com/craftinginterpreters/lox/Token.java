package com.craftinginterpreters.lox;

/**
 * �g�[�N����\���N���X�B
 */
class Token {
    final TokenType type;      // �g�[�N���̎��
    final String lexeme;       // �g�[�N���̌��̕�����
    final Object literal;      // �g�[�N���̃��e�����l
    final int line;            // �g�[�N�����o�������s�ԍ�

    /**
     * �R���X�g���N�^�i4�����o�[�W�����j
     * @param type �g�[�N���̎��
     * @param lexeme �g�[�N���̌��̕�����
     * @param literal �g�[�N���̃��e�����l
     * @param line �g�[�N�����o�������s�ԍ�
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
