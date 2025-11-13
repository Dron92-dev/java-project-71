package hexlet.code.formatters;

import hexlet.code.Node;

import java.util.List;

/**
 * Утилитарный класс для вывода в формате plain.
 */
public final class PlainFormatter {

    /**
     * Конструктор.
     */
    private PlainFormatter() {
    }

    /**
     * Форматирует различия в формате plain.
     *
     * @param diff список узлов представляющих различия
     * @return отформатированная строка
     */
    public static String format(List<Node> diff) {
        StringBuilder result = new StringBuilder();

        for (Node node : diff) {
            String key = node.getKey();
            String type = node.getType();

            switch (type) {
                case "added":
                    result.append("Property '")
                            .append(key)
                            .append("' was added with value: ")
                            .append(formatePlainValue(node.getNewValue()))
                            .append("\n");
                    break;
                case "removed":
                    result.append("Property '")
                            .append(key)
                            .append("' was removed")
                            .append("\n");
                    break;
                case "changed":
                    result.append("Property '")
                            .append(key)
                            .append("' was updated. From ")
                            .append(formatePlainValue(node.getOldValue()))
                            .append(" to ")
                            .append(formatePlainValue(node.getNewValue()))
                            .append("\n");
                    break;
                case "unchanged":
                    // Не выводим неизмененные свойства в plain формате
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный тип узла: " + type);
            }
        }
        if (!result.isEmpty()) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    /**
     * Форматирует значение для вывода в строковый plain формат.
     *
     * @param value значение
     * @return строковый вывод значения
     */
    private static String formatePlainValue(Object value) {
        if (value == null) {
            return null;
        }

        // Проверяем является ли значение сложным (массив или объект)
        if (isComplexValue(value)) {
            return "[complex value]";
        }

        // Для строк добавляем кавычки
        if (value instanceof String) {
            return "'" + value + "'";
        }

        // Для примитивов и простых значений возращаем как есть
        return value.toString();
    }

    /**
     * Проверяет является ли значение сложным (массив или Map).
     *
     * @param value значение
     * @return true или false
     */
    private static boolean isComplexValue(Object value) {
        return value instanceof java.util.List || value instanceof java.util.Map;
    }
}
