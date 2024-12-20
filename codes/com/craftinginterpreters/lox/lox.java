package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

  // �G���g���[�|�C���g�B�R�}���h���C�������ɉ����ăt�@�C�������s���邩�AREPL���N������B
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      // ������1�ȏ�̏ꍇ�A�g������\��
      System.out.println("Usage: jlox [script]");
      System.exit(64); // UNIX�X�^�C���̏I���R�[�h
    } else if (args.length == 1) {
      // ������1�̏ꍇ�A�t�@�C�������s
      runFile(args[0]);
    } else {
      // �������Ȃ��ꍇ�AREPL���N��
      runPrompt();
    }
  }

  // �t�@�C����ǂݍ���ł��̓��e�����s����
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
    // �G���[������ΏI���R�[�h65�ŏI��
    if (hadError) System.exit(65);
  }

  // �C���^���N�e�B�u��REPL���N������
  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (;;) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) break; // EOF�iCtrl-D�j�ŏI��
      run(line);
      hadError = false; // �G���[�������Ă�REPL�Z�b�V�������I�����Ȃ��悤�Ƀt���O�����Z�b�g
    }
  }

  // �\�[�X�R�[�h���󂯎���Ď��s����
  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    // �����_�ł̓g�[�N����\�����邾��
    for (Token token : tokens) {
      System.out.println(token);
    }
  }

  // �G���[�����F�G���[�����������Ƃ��ɌĂ΂��
  static void error(int line, String message) {
    report(line, "", message);
  }

  // �G���[���|�[�g�̏o��
  private static void report(int line, String where, String message) {
    System.err.println("[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }

  // �G���[�������������ǂ����������t���O
  static boolean hadError = false;
}
