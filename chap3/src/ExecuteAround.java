import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader bufferedReader) throws IOException;
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("chap3\\data.txt"))) {
            return p.process(br);
        }
    }

    public static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("chap3\\data.txt"))) {
            return br.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("---");

        String oneLine = processFile((BufferedReader b) -> b.readLine());
        System.out.println(oneLine);

        String twoLines = processFile((BufferedReader b) -> b.readLine() + "\t" + b.readLine());
        System.out.println(twoLines);
    }
}
