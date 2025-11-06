package hexlet.code;

import java.util.List;

/**
 * Утилитарный класс для форматирования различий.
 */
public class Formatter {

    /**
     * Приватный конструктор.
     */
    private Formatter() {
    }

    public static String stylish(List<Node> diff) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (Node node : diff) {
            String key = node.getKey();
            String type = node.getType();

            switch (type) {
                case "added":
                    result.append("  + ").append(key).append(": ").append(formateValue(node.getNewValue())).append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ").append(formateValue(node.getOldValue())).append("\n");
                    break;
                case "changed":
                    result.append("  - ").append(key).append(": ").append(formateValue(node.getOldValue())).append("\n");
                    result.append("  + ").append(key).append(": ").append(formateValue(node.getNewValue())).append("\n");
                    break;
                case "unchanged":
                    result.append("    ").append(key).append(": ").append(formateValue(node.getOldValue())).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный тип узла: " + type);
            }
        }
        result.append("}");
        return  result.toString();
    }

    private static String formateValue(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
