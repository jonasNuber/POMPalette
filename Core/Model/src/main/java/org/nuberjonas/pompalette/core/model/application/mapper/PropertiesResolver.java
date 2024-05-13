package org.nuberjonas.pompalette.core.model.application.mapper;

import java.util.Properties;
import java.util.regex.Pattern;

public final class PropertiesResolver {

    public PropertiesResolver() {
        throw new IllegalAccessError("This is a Utility Class and cannot be instantiated.");
    }

    private static final Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");

    public static String resolve(String input, Properties variables) {
        var matcher = pattern.matcher(input);
        var output = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = variables.getProperty(key, "");
            matcher.appendReplacement(output, replacement);
        }

        matcher.appendTail(output);
        return output.toString();
    }
}
