package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.List;

/**
 * Главный класс форматтера для выбора подходящего формата вывода.
 */
public final class Formatter {

    /**
     * Приватный конструктор.
     */
    private Formatter() {
    }

    /**
     * Форматирует различия в указанном формате.
     * @param diff список представляющий различия
     * @param format формат желаемого вывода (stylish, plain)
     * @return вывод результата отформатированный в String
     */
    public static String format(List<Node> diff, final String format) {
        return switch (format) {
            case "stylish" -> StylishFormatter.format(diff);
            case "plain" -> PlainFormatter.format(diff);
            default -> throw new IllegalArgumentException("Неподдерживаемый формат: " + format);
        };
    }
}
