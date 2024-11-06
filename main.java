import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to file, file should ends with .txt");
        //'C:\\Users\\user\\IdeaProjects\\churkinaTask2\\src\\try.txt'
        // 'C:\Users\\user\OneDrive\Рабочий стол\qtf.txt'
        String current_file = scanner.nextLine();
        System.out.print("Enter the path to the result file, file should ends with .txt");
        String output_path = scanner.nextLine();

        // check whether it is .txt
        if (!current_file.endsWith(".txt") || !output_path.endsWith(".txt")) {
            throw new IllegalArgumentException("Error, file should ends with .txt");
        }
        FileProcessor fileProcessor = new FileProcessor(current_file);

        if (fileProcessor.exists()) {
                fileProcessor.processFile(output_path); // Everything's fine --> program starts
        }
        else {
            System.out.println("File was not found: " + current_file);
        }
    }
}
