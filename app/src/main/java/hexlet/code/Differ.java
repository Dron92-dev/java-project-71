package hexlet.code;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Утилитарный класс для сравнения двух карт (Map) и генерации различий.
 */
public final class Differ {
    /**
     * Приватный конструктор для утилитарного класса.
     */
    private Differ() {
        throw new UnsupportedOperationException("Утилитарный класс");
    }

    /**
     * Генерирует разницу между двумя файлами.
     *
     * @param filePath1 путь к первому файлу
     * @param filePath2 путь ко второму файлу
     * @return строковое представление изменений
     * @throws Exception если чтение или парсинг звершились ошибкой
     */
    public static String generate(final String filePath1, final String filePath2) throws Exception {
        Map<String, Object> data1 = Parser.parse(filePath1);
        Map<String, Object> data2 = Parser.parse(filePath2);

        return generate(data1, data2);
    }

    /**
     * Генерирует изменения, которые произошли между двумя Map.
     *
     * @param data1 первая Map
     * @param data2 вторая Map
     * @return строковое представление изменений
     */
    public static String generate(
            final Map<String, Object> data1, final Map<String, Object> data2) {
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
