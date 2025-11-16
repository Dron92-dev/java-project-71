package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Node;

import java.util.List;

/**
 * Утилитарный класс для вывода в формате json.
 */
public final class JsonFormatter {

    /**
     * ObjectMapper для сереализации в JSON.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Конструктор.
     */
    private JsonFormatter() {
    }

    /**
     * Форматирует различия в формате json.
     *
     * @param diff список узлов предстваляющих различия
     * @return отформатированная строка
     */
    public static String format(final List<Node> diff) {
        StringBuilder result = new StringBuilder();
        result.append("[\n");

        for (int i = 0; i < diff.size(); i++) {
            Node node = diff.get(i);
            String key = node.getKey();
            String type = node.getType();

            result.append("  {\n");
            result.append("    \"key\": \"").append(key).append("\",\n");
            result.append("    \"type\": \"").append(type).append("\",\n");

            switch (type) {
                case "added":
                    result.append("    \"value\": ")
                            .append(formatJsonValue(node.getNewValue()));
                    break;
                case "removed", "unchanged":
                    result.append("    \"value\": ")
                            .append(formatJsonValue(node.getOldValue()));
                    break;
                case "changed":
                    result.append("    \"oldValue\": ")
                            .append(formatJsonValue(node.getOldValue())).append(",\n");
                    result.append("    \"newValue\": ")
                            .append(formatJsonValue(node.getNewValue()));
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный тип узла: " + type);
            }
            result.append("\n  }");
            if (i < diff.size() - 1) {
                result.append(",");
            }
            result.append("\n");
        }

        result.append("]");
        return result.toString();
    }

    /**
     * Форматирует значение для вывода в строковый json формат.
     *
     * @param value значение
     * @return строковый вывод значения
     */
    private static String formatJsonValue(final Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при форматировании значения JSON: " + value, e);
        }
    }
}
