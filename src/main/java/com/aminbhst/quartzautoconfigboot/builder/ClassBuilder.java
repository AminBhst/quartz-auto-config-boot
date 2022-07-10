package com.aminbhst.quartzautoconfigboot.builder;

import java.util.Collection;
import java.util.Map;

public class ClassBuilder {
    public final String INDENT = "    ";
    public final String INDENT2x = "        ";
    protected StringBuilder str;

    public ClassBuilder() {
        str = new StringBuilder();
    }

    public ClassBuilder pkg(String pkg) {
        str.append("package ").append(pkg).append(";");
        nextLine(2);
        return this;
    }

    public ClassBuilder clazz(String className, boolean isFinal) {
        return clazz(null, className, isFinal);
    }

    public ClassBuilder clazz(Collection<String> annotations, String className, boolean isFinal) {
        if (isCollectionNotEmpty(annotations)) {
            annotations.forEach(s -> str.append("@").append(s).append("\n"));
        }

        str.append("public ").append(isFinal ? "final " : "")
                .append("class ").append(className).append(" ").append("{");
        nextLine(2);
        return this;
    }

    public ClassBuilder field(Class<?> type, String name) {
        str.append(INDENT).append(type.getName()).append(" ").append(name)
                .append(";");
        nextLine(2);
        return this;
    }

    public ClassBuilder field(Class<?> type, String name, String value) {
        str.append(INDENT).append(type.getName()).append(" ").append(name)
                .append(" = ").append(value).append(";");
        nextLine(1);
        return this;
    }

    public ClassBuilder method(String returnType, String name, Map<String, String> args, String body) {
        method(null, "public", returnType, name, args, body, null);
        return this;
    }

    public ClassBuilder method(String returnType, String name, Map<String, String> args, String body, Collection<String> exceptions) {
        method(null, "public", returnType, name, args, body, exceptions);
        return this;
    }


    public ClassBuilder method(Collection<String> annotations, String accessType, String returnType, String name,
                               Map<String, String> args, String body, Collection<String> exceptions) {

        if (isCollectionNotEmpty(annotations)) {
            str.append(INDENT);
            annotations.forEach(s -> str.append("@").append(s).append("\n"));
        }

        str.append(INDENT).append(accessType).append(" ").append(returnType)
                .append(" ").append(name).append("(");
        if (args != null) {
            args.forEach((type, argName) -> str.append(type).append(" ").append(argName).append(","));
            str.deleteCharAt(str.lastIndexOf(","));
        }
        str.append(")");
        if (isCollectionNotEmpty(exceptions)) {
            str.append(" throws ");
            exceptions.forEach(exception -> str.append(exception).append(", "));
            str.deleteCharAt(str.lastIndexOf(","));
        }
        str.append("{").append("\n").append(INDENT2x).append(body).append("\n").append(INDENT).append("}");
        nextLine(2);
        return this;
    }

    protected void nextLine(final int level) {
        for (int i = 0; i < level; i++) {
            str.append("\n");
        }
    }

    public String build() {
        return str.append("}").toString();
    }

    private boolean isCollectionNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }
}
