package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;

/** Основной класс приложения для сравнения файлов. */
@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {

    /** Путь к первому файлу. */
    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filePath1;

    /** Путь ко второму файлу. */
    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filePath2;

    /** Формат вывода результатов. */
    @Option(
            names = {"-f", "--format"},
            description = "output format [default: stylish]",
            paramLabel = "format",
            defaultValue = "stylish")
    private String format;

    /** Опция запроса справки. */
    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Show this help message and exit.")
    private boolean helpRequested = false;

    /** Опция запроса версии. */
    @Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionRequested = false;

    /**
     * Основной метод приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    /**
     * Основной метод выполнения сравнения файлов.
     *
     * @return код возврата приложения
     * @throws Exception в случае ошибок чтения файлов или парсинга
     */
    public Integer call() throws Exception {
        String diff = Differ.generate(filePath1, filePath2);
        System.out.println(diff);
        return 0;
    }

    /**
     * Метод читает и парсит файл в Map.
     *
     * @param filePath путь к файлу
     * @return распарсенные данные из файла
     * @throws Exception в случае ошибок чтения или парсинга
     */
    private Map<String, Object> readFile(final String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        String content = Files.readString(path);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, Map.class);
    }
}
