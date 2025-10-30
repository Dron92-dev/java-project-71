package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Класс для тестирования Differ. */
final class DifferTest {

    /** Ожидаемая стилизация результата работы диффа. */
    private String expected;

    /** ObjectMapper для парсинга JSON. */
    private ObjectMapper objectMapper;

    /**
     * Подготовка данных перед каждым тестом.
     *
     * @throws Exception если настройка не удалась
     */
    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();

        expected =
                String.join(
                        "\n",
                        "{",
                        "    - follow: false",
                        "      host: hexlet.io",
                        "    - proxy: 123.234.53.22",
                        "    - timeout: 50",
                        "    + timeout: 20",
                        "    + verbose: true",
                        "}");
    }

    /**
     * Тест для сравнения разных файлов.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithDifferentFiles() throws Exception {
        Map<String, Object> data1 = readJsonFile("fixtures/file1.json");
        Map<String, Object> data2 = readJsonFile("fixtures/file2.json");

        String result = Differ.generate(data1, data2);

        assertThat(result).isEqualTo(expected);
    }

    /**
     * Тест сравнения одинаковых файлов.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithSameFiles() throws Exception {
        Map<String, Object> data1 = readJsonFile("fixtures/file3.json");
        Map<String, Object> data2 = readJsonFile("fixtures/file3.json");

        String result = Differ.generate(data1, data2);
        final String expectedForSameFiles =
                String.join("\n", "{", "      host: hexlet.io", "      timeout: 50", "}");
        assertThat(result).isEqualTo(expectedForSameFiles);
    }

    /**
     * Тест сравнения с пустым первым файлом.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithEmptyFirstFile() throws Exception {
        Map<String, Object> data1 = readJsonFile("fixtures/empty.json");
        Map<String, Object> data2 = readJsonFile("fixtures/file2.json");

        String result = Differ.generate(data1, data2);
        final String expectedForEmptyFirst =
                String.join(
                        "\n",
                        "{",
                        "    + host: hexlet.io",
                        "    + timeout: 20",
                        "    + verbose: true",
                        "}");
        assertThat(result).isEqualTo(expectedForEmptyFirst);
    }

    /**
     * Тест сравнения с пустым вторым файлом.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithEmptySecondFile() throws Exception {
        Map<String, Object> data1 = readJsonFile("fixtures/file1.json");
        Map<String, Object> data2 = readJsonFile("fixtures/empty.json");

        String result = Differ.generate(data1, data2);
        final String expectedForEmptySecond =
                String.join(
                        "\n",
                        "{",
                        "    - follow: false",
                        "    - host: hexlet.io",
                        "    - proxy: 123.234.53.22",
                        "    - timeout: 50",
                        "}");
        assertThat(result).isEqualTo(expectedForEmptySecond);
    }

    /**
     * Чтение JSON файла и преобразование в Map.
     *
     * @param resourceName имя ресурса (файла)
     * @return распарсенная карта
     * @throws IOException если чтение не удалось
     */
    private Map<String, Object> readJsonFile(final String resourceName) throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource(resourceName);
        if (fileUrl == null) {
            throw new FileNotFoundException("Файл не найден: " + resourceName);
        }

        Path path = Paths.get("src/test/resources/" + resourceName);
        String jsonContent = Files.readString(path);

        return objectMapper.readValue(jsonContent, Map.class);
    }
}
