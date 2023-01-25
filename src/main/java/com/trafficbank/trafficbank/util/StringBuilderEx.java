package com.trafficbank.trafficbank.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringBuilderEx {

    private final StringBuilder sb;

    public static String format(String format, Object... arguments) {
        if (!StringUtils.hasText(format)) {
            return null;
        }

        StringBuilder formatter = new StringBuilder(format);
        List<Object> valueList = new ArrayList<>();

        Matcher matcher = Pattern.compile("\\{(\\w+)}").matcher(format);

        Map<String, Object> values = new HashMap<>();
        for (int index = 0; index < arguments.length; index++) {
            values.put(String.format("%d", index), arguments[index]);
        }

        while (matcher.find()) {
            String key = matcher.group(1);

            String formatKey = String.format("{%s}", key);
            int index = formatter.indexOf(formatKey);

            if (index != -1) {
                formatter.replace(index, index + formatKey.length(), "%s");
                Object value = values.get(key);
                if (null == value) {
                    int nKey = Integer.parseInt(key);
                    if (nKey >= arguments.length) {
                        throw new MissingFormatArgumentException(formatKey);
                    }
                }
                valueList.add(value);
            }
        }

        return String.format(formatter.toString(), valueList.toArray());
    }

    public StringBuilderEx() {
        sb = new StringBuilder();
    }

    public StringBuilderEx append(String str) {
        sb.append(str);
        return this;
    }

    public StringBuilderEx append(Object argument) {
        sb.append(argument);
        return this;
    }

    public StringBuilderEx append(String format, Object... arguments) {
        sb.append(format(format, arguments));
        return this;
    }

    public StringBuilderEx appendLine(String str) {
        sb.append(str).append("\n");
        return this;
    }

    public StringBuilderEx appendLine(String format, Object... arguments) {
        sb.append(format(format, arguments)).append("\n");
        return this;
    }

    public int length() {
        return sb.length();
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
