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
     * Ожидаемая стилизация результата работы диффа в формате stylish.
     */
    private String expectedStylish;
    /**
     * Ожидаемая стилизация результата работы диффа в формате plain.
     */
    private String expectedPlain;

    /**
     * Ожидаемая стилизация результата работы диффа в формате json.
     */
    private String expectedJson;

    /**
     * Подготовка данных перед каждым тестом.
     *
     */
    @BeforeEach
    void setUp() {
        expectedStylish = String.join("\n",
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

        expectedJson = String.join("\n",
                "[",
                "  {",
                "    \"key\": \"chars1\",",
                "    \"type\": \"unchanged\",",
                "    \"value\": [\"a\",\"b\",\"c\"]",
                "  },",
                "  {",
                "    \"key\": \"chars2\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": [\"d\",\"e\",\"f\"],",
                "    \"newValue\": false",
                "  },",
                "  {",
                "    \"key\": \"checked\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": false,",
                "    \"newValue\": true",
                "  },",
                "  {",
                "    \"key\": \"default\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": null,",
                "    \"newValue\": [\"value1\",\"value2\"]",
                "  },",
                "  {",
                "    \"key\": \"id\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": 45,",
                "    \"newValue\": null",
                "  },",
                "  {",
                "    \"key\": \"key1\",",
                "    \"type\": \"removed\",",
                "    \"value\": \"value1\"",
                "  },",
                "  {",
                "    \"key\": \"key2\",",
                "    \"type\": \"added\",",
                "    \"value\": \"value2\"",
                "  },",
                "  {",
                "    \"key\": \"numbers1\",",
                "    \"type\": \"unchanged\",",
                "    \"value\": [1,2,3,4]",
                "  },",
                "  {",
                "    \"key\": \"numbers2\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": [2,3,4,5],",
                "    \"newValue\": [22,33,44,55]",
                "  },",
                "  {",
                "    \"key\": \"numbers3\",",
                "    \"type\": \"removed\",",
                "    \"value\": [3,4,5]",
                "  },",
                "  {",
                "    \"key\": \"numbers4\",",
                "    \"type\": \"added\",",
                "    \"value\": [4,5,6]",
                "  },",
                "  {",
                "    \"key\": \"obj1\",",
                "    \"type\": \"added\",",
                "    \"value\": {\"nestedKey\":\"value\",\"isNested\":true}",
                "  },",
                "  {",
                "    \"key\": \"setting1\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": \"Some value\",",
                "    \"newValue\": \"Another value\"",
                "  },",
                "  {",
                "    \"key\": \"setting2\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": 200,",
                "    \"newValue\": 300",
                "  },",
                "  {",
                "    \"key\": \"setting3\",",
                "    \"type\": \"changed\",",
                "    \"oldValue\": true,",
                "    \"newValue\": \"none\"",
                "  }",
                "]"
        );
    }

    /**
     * Тест для сравнения вложенных JSON структур в формате stylish.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithStylishFormatForJsonFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.json");
        String filePath2 = getResourcePath("fixtures/nested2.json");

        String result = Differ.generate(filePath1, filePath2, "stylish");

        assertThat(result).isEqualTo(expectedStylish);
    }

    /**
     * Тест для сравнения вложенных JSON структур в формате plain.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithPlainFormatForJsonFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.json");
        String filePath2 = getResourcePath("fixtures/nested2.json");

        String result = Differ.generate(filePath1, filePath2, "plain");

        assertThat(result).isEqualTo(expectedPlain);
    }

    /**
     * Тест для сравнения вложенных JSON структур в формате json.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithJsonFormatForJsonFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.json");
        String filePath2 = getResourcePath("fixtures/nested2.json");

        String result = Differ.generate(filePath1, filePath2, "json");

        assertThat(result).isEqualTo(expectedJson);
    }

    /**
     * Тест для сравнения вложенных YAML структур.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithStylishFormatForYamlFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.yml");
        String filePath2 = getResourcePath("fixtures/nested2.yml");

        String result = Differ.generate(filePath1, filePath2, "stylish");

        assertThat(result).isEqualTo(expectedStylish);
    }

    /**
     * Тест для сравнения вложенных YAML структур в формате plain.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithPlainFormatForYamlFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.yml");
        String filePath2 = getResourcePath("fixtures/nested2.yml");

        String result = Differ.generate(filePath1, filePath2, "plain");

        assertThat(result).isEqualTo(expectedPlain);
    }

    /**
     * Тест для сравнения вложенных YAML структур в формате json.
     *
     * @throws Exception если тест не удался
     */
    @Test
    void testGenerateWithJsonFormatForYamlFiles() throws Exception {
        String filePath1 = getResourcePath("fixtures/nested1.yml");
        String filePath2 = getResourcePath("fixtures/nested2.yml");

        String result = Differ.generate(filePath1, filePath2, "json");

        assertThat(result).isEqualTo(expectedJson);
    }

    /**
     * Получение полного пути к файлу тестовых ресурсов.
     *
     * @param resourceName имя ресурса (файла)
     * @return путь к ресурсу
     */
    private String getResourcePath(final String resourceName) {
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
