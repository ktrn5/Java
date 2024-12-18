import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // ввод фио и даты рождения
        System.out.println("Введите ФИО (фамилия имя отчество): ");
        String fullName = scanner.nextLine();
        for (char i: fullName.toCharArray()) {
            if (!Character.isLetter(i)  && i != ' ') {
                throw new Exception("ФИО может содержать только буквы и пробелы.");
            }
        }

        System.out.println("Введите дату рождения (дд.мм.гггг или дд/мм/гггг):");
        String birthDateInput = scanner.nextLine();
        Person person = new Person(fullName, birthDateInput);
        if (person.calculateAge() < 0) {
            throw new Exception("Дата рождения не может быть больше сегодняшнего дня");
        }

        System.out.println(person);
        scanner.close();
    }
}

// Чуркина Екатерина Николаевна
// 07.12.2005
