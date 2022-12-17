package com.epam.esm.rule;


import java.util.function.Supplier;

public class Rule<T> {
  public Supplier<Boolean> condition;
  public Supplier<T> process;

    public Rule(Supplier<Boolean> condition, Supplier<T> process) {
        this.condition = condition;
        this.process = process;
    }


}
