package com.epam.esm.rule;

import java.util.function.Supplier;

public class RuleEngine {

    public static Rule<String> createRuleForName(String value, String process) {
        return createRule(
                () -> value != null,
                () -> process + " WHERE lower(gc.name) LIKE concat('%', lower(:name), '%')"
        );
    }

    public static Rule<String> createRuleForDescription(String value, String process) {
        return createRule(
                () -> value != null,
                () ->
                        process.contains("WHERE")
                                ? process + " AND lower(gc.description) LIKE concat('%', lower(:description), '%')"
                                : process + " WHERE lower(gc.description) LIKE concat('%', lower(:description), '%')"
        );
    }

    public static Rule<String> createRuleForTagName(String value, String process) {
        return createRule(
                () -> value != null,
                () ->
                        process.contains("WHERE")
                                ? process + " AND lower(coalesce(t.name,'')) = lower(coalesce(:tagName,''))"
                                : process + " WHERE lower(coalesce(t.name,'')) = lower(coalesce(:tagName,''))"
        );
    }

    public static Rule<String> createRule(Supplier<Boolean> supplier, Supplier<String> process) {
        return new Rule<>(supplier, process);
    }
}
