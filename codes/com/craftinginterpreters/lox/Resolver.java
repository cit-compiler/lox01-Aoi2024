package com.craftinginterpreters.lox;

import java.util.List;

public class Resolver {
    private final Interpreter interpreter;

    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void resolve(List<Expr> expressions) {
        for (Expr expr : expressions) {
            resolve(expr);
        }
    }

    private void resolve(Expr expr) {
        // resolveˆ—‚ğÀ‘•
    }
}
