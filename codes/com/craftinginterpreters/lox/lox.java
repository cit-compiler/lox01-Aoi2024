package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64); // �G���[�R�[�h64��Ԃ�
        } else if (args.length == 1) {
            runFile(args[0]); // �t�@�C�������s
        } else {
            runPrompt(); // �Θb�^�v�����v�g�iREPL�j�����s
        }
    }

    /**
     * �t�@�C�������s���郂�[�h
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset())); // �t�@�C�����e��ǂݍ���Ŏ��s

        // �G���[������ΏI���R�[�h65�Ńv���O�����I��
        if (hadError) System.exit(65);
    }

    /**
     * �Θb�^�v�����v�g�iREPL�j���[�h
     */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> "); // �v�����v�g�\��
            String line = reader.readLine(); // ���[�U�[���͂�ǂݍ���
            if (line == null) break; // ���͂��I�������烋�[�v�𔲂���
            run(line); // ���͂����s
            hadError = false; // �G���[��Ԃ����Z�b�g
        }
    }

    /**
     * �R�[�h�̎��s�i�g�[�N�����X�L��������j
     */
    private static void run(String source) {
        Scanner scanner = new Scanner(source); // Scanner�N���X�i��Ŏ����j
        List<Token> tokens = scanner.scanTokens(); // �g�[�N�����X�L��������

        // �X�L���������g�[�N�����o�͂���i�f�o�b�O�p�j
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    /**
     * �G���[�����i����̍s�ԍ��ŃG���[��񍐁j
     */
    static void error(int line, String message) {
        report(line, "", message);
    }

    /**
     * �G���[���b�Z�[�W�̃t�H�[�}�b�g�ƕ\��
     */
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
