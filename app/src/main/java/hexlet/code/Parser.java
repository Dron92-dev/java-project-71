package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Утилитарный класс для парсинга файлов различных форматов.
 */
public final class Parser {
    /**
     * Приватный конструктор для утилитарного класса.
     */
    private Parser() {

    }

    /**
     * Парсит файл в Map на основе расширения файла.
     *
     * @param filePath путь к файлу
     * @return распарсенные данные в виде Map
     * @throws IOException если чтение или парсинг завершились ошибкой
     */
    public static Map<String, Object> parse(final String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        String extension = getFileExtension(filePath);

        return switch (extension.toLowerCase()) {
            case "json" -> parseJson(content);
            case "yaml", "yml" -> parseYaml(content);
            default -> throw new IllegalArgumentException("Неподдерживаемый формат файла: "
                    + extension);
        };
    }

    /**
     * Парсит JSON содержимое в Map.
     *
     * @param content JSON содержимое
     * @return распарсенный Map
     * @throws IOException если парсинг завершился ошибкой
     */
    private static Map<String, Object> parseJson(final String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    /**
     * Парсит YAML содержимое в Map.
     *
     * @param content YAML содержимое
     * @return распарсенный Map
     * @throws IOException если парсинг завершился ошибкой
     */
    private static Map<String, Object> parseYaml(final String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }

    /**
     * Получает расширение файла из пути к файлу.
     *
     * @param filePath путь к файлу
     * @return расширение файла
     */
    private static String getFileExtension(final String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }
}
