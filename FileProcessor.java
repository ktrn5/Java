import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {
    private final String path; // path
    private final int[] uppercaseUsage; // array for uppercase
    private final int[] lowercaseUsage; // array for lowercase
    private final int[] digitsUsage; // array for digits

    // constructor
    public FileProcessor(String path) {
        this.path = path;
        uppercaseUsage = new int[26]; // 26 letters in eng alphabet
        lowercaseUsage = new int[26];
        digitsUsage = new int[10]; // 0123456789
    }

    // method to check the existance
    public boolean exists() {
        File file = new File(path);
        return file.exists();
    }

    // method to read and analyze the file (count everything)
    public void processFile(String output_path) throws IOException {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
            int char_read; // to store the character that will be read from the file
            char_read = reader.read();
            while (char_read != -1) { // until the end of the file is reached

                // convert the read character into array and extraction
                char[] characters = Character.toChars(char_read);
                char character = characters[0];
                char_read = reader.read();

                // uppercase
                if (character >= 'A' && character <= 'Z') { //
                    int index = character - 'A'; //finding the index for character by subtracting the code of the character 'A' from the code of the current character
                    //(--> getting the index for this character in the alphabet, like: a - 1 letter, b - 2......)
                    uppercaseUsage[index]++; // tracks the number of occurrences of each letter in uppercase
                }
                // lowercase
                else if (character >= 'a' && character <= 'z') {
                    int index = character - 'a';
                    lowercaseUsage[index]++;
                }
                // digits
                else if (character >= '0' && character <= '9') {
                    int index = character - '0';
                    digitsUsage[index]++;
                }
            }
            writeToFile(output_path);
        }
        catch (IOException e) {
            System.out.println("Error during the reading of the file " );
        }
        finally {
                if (reader != null) {
                    reader.close(); // close filereader
                }
        }
    }
    // Method to write the result to a file
    public void writeToFile(String output_path) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(output_path);
            writer.write(this.toString());
            System.out.println("Results have been written to the given file (" + output_path + ")");
        }
        catch (IOException e) {
            System.out.println("Error during writing to the file");
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    // to string (method to print)
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("There are in the file:\n");

        for (int i = 0; i < 26; i++) { // uppercase
            if (uppercaseUsage[i] > 0) {
                result.append(Character.toChars('A' + i)).append(": ").append(uppercaseUsage[i]);
                result.append("\n");
                }
            }
        result.append("\n");
        // lowercase
        for (int i = 0; i < 26; i++) {
            if (lowercaseUsage[i] > 0) {
                result.append(Character.toChars('a' + i)).append(": ").append(lowercaseUsage[i]);
                result.append("\n");
                }
            }
        result.append("\n");
        // digits
        for (int i = 0; i < 10; i++) {
            if (digitsUsage[i] > 0) {
                result.append(i).append(": ").append(digitsUsage[i]).append("\n");
            }
        }
        result.append("\n");
        return result.toString();
    }
}
