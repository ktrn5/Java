import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    private String lastName;
    private String firstName;
    private String patronymic;
    private LocalDate birthDate;

    public Person(String name, String birthDateInput) {
        String[] fullName = name.split(" ");

        this.lastName = fullName[0];
        this.firstName = fullName[1];
        this.patronymic = fullName[2];
        this.birthDate = getBirthDate(birthDateInput);
    }

    private LocalDate getBirthDate(String date) {
        // поддерживает 2 формата
        DateTimeFormatter dotesFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter slashFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (date.contains(".")) {
            return LocalDate.parse(date, dotesFormat);
        }
        else if (date.contains("/")) {
            return LocalDate.parse(date, slashFormat);
        }
        else {
            throw new IllegalArgumentException("неверный формат даты: используйте дд.мм.гггг или дд/мм/гггг");
        }

    }

    public int calculateAge() {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();

        // проверка: исполнилось ли уже в этом году?
        // например: 07.12.2005 --> 18 лет (в декабре исполнится), 07.10.2005 --> 19 лет
        if (today.getDayOfYear() < birthDate.getDayOfYear()) {
            age--;
        }
        return age;
    }

    // определение пола по отчеству
    public String getGender() {
        if (patronymic.endsWith("на")) {
            return "женский"; // николаеВНА, ильичНА, никитичнНА
        }
        else if (patronymic.endsWith("ич")) {
            return "мужской"; // николаевИЧ, никитИЧ, олеговИЧ
        }
        else {
            return "определить не удалось";
        }
    }

    // метод для получения инициалов
    public String getInitials() {
        String initials = firstName.split("")[0] + "." + patronymic.split("")[0] + ".";
        return lastName + " " + initials;
    }

    // грамматически верное окончание для возраста: лет\год\года
    public String correctAgeWord() {
        int age = calculateAge();
        String ending = "";

        int lastDigit = age % 10;
        if ((lastDigit >= 5  && lastDigit <= 9 || lastDigit == 0) || age == 11) {
            ending = "лет"; // 5 лет, 18 лет, 29 лет..
        }
        else if (lastDigit == 1 && age != 11) {
                ending = "год"; // 1 год, 21 год....
        }
        else if (lastDigit == 2 || lastDigit == 3 || lastDigit == 4) {
            ending = "года";
        }
        return age + " " + ending;
    }

    // результат
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Инициалы: ").append(getInitials()).append("\n");
        str.append("Пол: ").append(getGender()).append("\n");
        str.append("Возраст: ").append(correctAgeWord()).append("\n");

        return str.toString();
    }
}
