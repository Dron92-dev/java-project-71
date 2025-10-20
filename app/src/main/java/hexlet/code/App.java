package hexlet.code;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true
)


public class App implements Callable<Integer> {

    @Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filepath1"
    )
    private String filePath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filepath2"
    )
    private String filePath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format [default: stylish]",
            paramLabel = "format",
            defaultValue = "stylish"
    )
    private String format;

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Show this help message and exit."
    )
    private boolean helpRequested = false;

    @Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = "Print version information and exit."
    )
    private boolean versionRequested = false;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    public Integer call() throws Exception {
        Map<String, Object> data1 = readFile(filePath1);
        Map<String, Object> data2 = readFile(filePath2);

        System.out.println("File 1 data: " + data1);
        System.out.println("File 2 data: " + data2);
        return 0;
    }

    private Map<String, Object> readFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        String content = Files.readString(path);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, Map.class);
    }
}
