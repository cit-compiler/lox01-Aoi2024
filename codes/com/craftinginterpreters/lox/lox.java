package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

  // エントリーポイント。コマンドライン引数に応じてファイルを実行するか、REPLを起動する。
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      // 引数が1つ以上の場合、使い方を表示
      System.out.println("Usage: jlox [script]");
      System.exit(64); // UNIXスタイルの終了コード
    } else if (args.length == 1) {
      // 引数が1つの場合、ファイルを実行
      runFile(args[0]);
    } else {
      // 引数がない場合、REPLを起動
      runPrompt();
    }
  }

  // ファイルを読み込んでその内容を実行する
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
    // エラーがあれば終了コード65で終了
    if (hadError) System.exit(65);
  }

  // インタラクティブなREPLを起動する
  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (;;) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) break; // EOF（Ctrl-D）で終了
      run(line);
      hadError = false; // エラーがあってもREPLセッションを終了しないようにフラグをリセット
    }
  }

  // ソースコードを受け取って実行する
  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    // 現時点ではトークンを表示するだけ
    for (Token token : tokens) {
      System.out.println(token);
    }
  }

  // エラー処理：エラーが発生したときに呼ばれる
  static void error(int line, String message) {
    report(line, "", message);
  }

  // エラーレポートの出力
  private static void report(int line, String where, String message) {
    System.err.println("[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }

  // エラーが発生したかどうかを示すフラグ
  static boolean hadError = false;
}
