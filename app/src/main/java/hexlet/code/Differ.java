package hexlet.code;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Differ {
    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        TreeMap<String, String> sortedKeys = new TreeMap<>();
        for (String key : allKeys) {
            sortedKeys.put(key, key);
        }

        for (String key : sortedKeys.keySet()) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);
            Object value1 = data1.get(key);
            Object value2 = data2.get(key);

            if (inFirst && !inSecond) {
                result.append("    - ").append(key).append(": ").append(value1).append("\n");
            } else if (!inFirst && inSecond) {
                result.append("    + ").append(key).append(": ").append(value2).append("\n");
            } else if (value1.equals(value2)) {
                result.append("      ").append(key).append(": ").append(value1).append("\n");
            } else {
                result.append("    - ").append(key).append(": ").append(value1).append("\n");
                result.append("    + ").append(key).append(": ").append(value2).append("\n");
            }
        }
        result.append("}");
        return result.toString();
    }
}
