package hexlet.code;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
        return generate(filePath1, filePath2, "stylish");
    }

    /**
     * Генерирует разницу между двумя файлами.
     *
     * @param filePath1 путь к первому файлу
     * @param filePath2 путь ко второму файлу
     * @param format    желаемый формат вывода
     * @return строковое представление изменений
     * @throws Exception если чтение или парсинг звершились ошибкой
     */
    public static String generate(final String filePath1, final String filePath2,
                                  final String format) throws Exception {
        Map<String, Object> data1 = Parser.parse(filePath1);
        Map<String, Object> data2 = Parser.parse(filePath2);

        List<Node> diff = buildDiff(data1, data2);
        return Formatter.format(diff, format);
    }

    /**
     * Генерирует изменения, которые произошли между двумя Map.
     *
     * @param map1 первая Map
     * @param map2 вторая Map
     * @return список узлов показывающих различия
     */
    public static List<Node> buildDiff(
            final Map<String, Object> map1, final Map<String, Object> map2) {
        List<Node> diff = new ArrayList<>();
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                diff.add(new Node(key, null, value2, "added"));
            } else if (!map2.containsKey(key)) {
                diff.add(new Node(key, value1, null, "removed"));
            } else if (isEqual(value1, value2)) {
                diff.add(new Node(key, value1, value2, "unchanged"));
            } else {
                diff.add(new Node(key, value1, value2, "changed"));
            }
        }
        return diff;
    }

    /**
     * Проверяет равны ли два значения.
     * Для сложных объектов (arrays, maps), использует toString().
     *
     * @param value1 первое значение
     * @param value2 второе значение
     * @return true если значения равны
     */
    private static boolean isEqual(final Object value1, final Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 == null || value2 == null) {
            return false;
        }
        return value1.toString().equals(value2.toString());
    }
}
