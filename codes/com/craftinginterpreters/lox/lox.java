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
            System.exit(64); // エラーコード64を返す
        } else if (args.length == 1) {
            runFile(args[0]); // ファイルを実行
        } else {
            runPrompt(); // 対話型プロンプト（REPL）を実行
        }
    }

    /**
     * ファイルを実行するモード
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset())); // ファイル内容を読み込んで実行

        // エラーがあれば終了コード65でプログラム終了
        if (hadError) System.exit(65);
    }

    /**
     * 対話型プロンプト（REPL）モード
     */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> "); // プロンプト表示
            String line = reader.readLine(); // ユーザー入力を読み込む
            if (line == null) break; // 入力が終了したらループを抜ける
            run(line); // 入力を実行
            hadError = false; // エラー状態をリセット
        }
    }

    /**
     * コードの実行（トークンをスキャンする）
     */
    private static void run(String source) {
        Scanner scanner = new Scanner(source); // Scannerクラス（後で実装）
        List<Token> tokens = scanner.scanTokens(); // トークンをスキャンする

        // スキャンしたトークンを出力する（デバッグ用）
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    /**
     * エラー処理（特定の行番号でエラーを報告）
     */
    static void error(int line, String message) {
        report(line, "", message);
    }

    /**
     * エラーメッセージのフォーマットと表示
     */
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
