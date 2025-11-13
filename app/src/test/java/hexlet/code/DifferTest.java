package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования Differ.
 */
final class DifferTest {

    /**
     * Ожидаемая стилизация результата работы диффа.
     */
    private String expectedNestedStylish;
    private String expectedPlain;

    /**
     * Подготовка данных перед каждым тестом.
     *
     */
    @BeforeEach
    void setUp() {
        expectedNestedStylish = String.join("\n",
                "{",
                "    chars1: [a, b, c]",
                "  - chars2: [d, e, f]",
                "  + chars2: false",
                "  - checked: false",
                "  + checked: true",
                "  - default: null",
                "  + default: [value1, value2]",
                "  - id: 45",
                "  + id: null",
                "  - key1: value1",
                "  + key2: value2",
                "    numbers1: [1, 2, 3, 4]",
                "  - numbers2: [2, 3, 4, 5]",
                "  + numbers2: [22, 33, 44, 55]",
                "  - numbers3: [3, 4, 5]",
                "  + numbers4: [4, 5, 6]",
                "  + obj1: {nestedKey=value, isNested=true}",
                "  - setting1: Some value",
                "  + setting1: Another value",
                "  - setting2: 200",
                "  + setting2: 300",
                "  - setting3: true",
                "  + setting3: none",
                "}"
        );

        expectedPlain = String.join("\n",
                "Property 'chars2' was updated. From [complex value] to false",
                "Property 'checked' was updated. From false to true",
                "Property 'default' was updated. From null to [complex value]",
                "Property 'id' was updated. From 45 to null",
                "Property 'key1' was removed",
                "Property 'key2' was added with value: 'value2'",
                "Property 'numbers2' was updated. From [complex value] to [complex value]",
                "Property 'numbers3' was removed",
                "Property 'numbers4' was added with value: [complex value]",
                "Property 'obj1' was added with value: [complex value]",
                "Property 'setting1' was updated. From 'Some value' to 'Another value'",
                "Property 'setting2' was updated. From 200 to 300",
                "Property 'setting3' was updated. From true to 'none'"
        );
    }

    /**
     * Тест для сравнения вложенных JSON структур.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithNestedJsonFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.json");
        String filePath2 = getResourcePath("fixtures/nested2.json");

        String result = Differ.generate(filePath1, filePath2, "stylish");

        assertThat(result).isEqualTo(expectedNestedStylish);
    }

    /**
     * Тест для сравнения вложенных JSON структур в формате plain.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithPlainFormatJson() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.json");
        String filePath2 = getResourcePath("fixtures/nested2.json");

        String result = Differ.generate(filePath1, filePath2, "plain");

        assertThat(result).isEqualTo(expectedPlain);
    }

    /**
     * Тест для сравнения вложенных YAML структур.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithNestedYamlFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.yml");
        String filePath2 = getResourcePath("fixtures/nested2.yml");

        String result = Differ.generate(filePath1, filePath2);

        assertThat(result).isEqualTo(expectedNestedStylish);
    }

    /**
     * Тест для сравнения вложенных YAML структур в формате plain.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithPlainFormatYaml() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.yml");
        String filePath2 = getResourcePath("fixtures/nested2.yml");

        String result = Differ.generate(filePath1, filePath2, "plain");

        assertThat(result).isEqualTo(expectedPlain);
    }

    /**
     * Получение полного пути к файлу тестовых ресурсов.
     *
     * @param resourceName имя ресурса (файла)
     * @return путь к ресурсу
     */
    private String getResourcePath(String resourceName) {
        try {
            URL recourseUrl = getClass().getClassLoader().getResource(resourceName);
            if (recourseUrl == null) {
                throw new FileNotFoundException("Ресурс не найден: " + resourceName);
            }
            return Paths.get(recourseUrl.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить путь к ресурсу: " + resourceName, e);
        }
    }
}
