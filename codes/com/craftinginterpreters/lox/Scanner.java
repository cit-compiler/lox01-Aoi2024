package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

/**
 * �\�[�X�R�[�h���g�[�N���ɕ�������X�L���i�[�N���X�B
 * �g�[�N���̎�ނɊ�Â��A���������͂��ă��X�g�𐶐����܂��B
 */
class Scanner {
    private final String source;                 // �\�[�X�R�[�h�S��
    private final List<Token> tokens = new ArrayList<>(); // �������ꂽ�g�[�N���̃��X�g
    private static final Map<String, TokenType> keywords; // �L�[���[�h��ێ�����}�b�v

    private int start = 0;       // ���݂̃g�[�N���̊J�n�ʒu
    private int current = 0;     // ���݂̕����̈ʒu
    private int line = 1;        // ���݂̍s�ԍ�

    static {
        // �L�[���[�h�Ƃ��̑Ή�����g�[�N���^�C�v�̃}�b�s���O
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
     * �g�[�N���̃��X�g�𐶐�����B
     * @return �������ꂽ�g�[�N���̃��X�g�B
     */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // ���̃g�[�N���̊J�n�ʒu���L�^
            start = current;
            scanToken();
        }

        // �t�@�C���I�[�̃g�[�N����ǉ�
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    /**
     * �\�[�X�R�[�h����͂��A1�̃g�[�N�����X�L��������B
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
                    // �R�����g�̏����F�s���܂ŃX�L�b�v
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;

            case ' ':
            case '\r':
            case '\t':
                // �󔒕����𖳎�
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
     * ���̕������擾���A���݈ʒu��i�߂�B
     * @return ���݂̕����B
     */
    private char advance() {
        return source.charAt(current++);
    }

    /**
     * ���̕��������҂���镶�����ǂ����m�F���A��v����ꍇ�͐i�߂�B
     * @param expected ���҂��镶��
     * @return ��v�����ꍇ��true�A�����łȂ����false�B
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    /**
     * ���݂̕������m�F����i�i�߂Ȃ��j�B
     * @return ���݂̕����B
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    /**
     * ���̕������m�F����i�i�߂Ȃ��j�B
     * @return ���̕����B
     */
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * �g�[�N�������X�g�ɒǉ�����B
     * @param type �g�[�N���̎��
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    /**
     * �g�[�N�������X�g�ɒǉ�����i���e�����l���܂ޏꍇ�j�B
     * @param type �g�[�N���̎��
     * @param literal �g�[�N���̃��e�����l
     */
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * ���l�g�[�N������������B
     */
    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            // �����_������
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    /**
     * ������g�[�N������������B
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

        // �I������������
        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    /**
     * ���ʎq��L�[���[�h����������B
     */
    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    /**
     * �������������ǂ�������B
     * @param c ���肷�镶��
     * @return �����ł����true�B
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * �������A���t�@�x�b�g�܂��̓A���_�[�X�R�A������B
     * @param c ���肷�镶��
     * @return �����𖞂�����true�B
     */
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    /**
     * �������A���t�@�x�b�g�܂��͐���������B
     * @param c ���肷�镶��
     * @return �����𖞂�����true�B
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    /**
     * �\�[�X�R�[�h�̏I�[�ɓ��B����������B
     * @return �I�[�ł����true�B
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }
}
