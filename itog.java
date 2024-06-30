import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class itog {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите данные в произвольном порядке, разделенные пробелом:");
            System.out.println("Фамилия Имя Отчество датарождения номертелефона пол");
            String input = scanner.nextLine();

            String[] data = input.split(" ");
            if (data.length != 6) {
                if (data.length < 6) {
                    System.out.println("Ошибка: Введено меньше данных, чем требуется.");
                } else {
                    System.out.println("Ошибка: Введено больше данных, чем требуется.");
                }
                continue;
            }

            try {
                String lastName = data[0];
                String firstName = data[1];
                String middleName = data[2];
                Date birthDate = parseDate(data[3]);
                long phoneNumber = parsePhoneNumber(data[4]);
                char gender = parseGender(data[5]);

                saveToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);
                System.out.println("Данные успешно записаны в файл.");
            } catch (ParseException e) {
                System.out.println("Ошибка: Неверный формат даты рождения. Должен быть dd.mm.yyyy.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат номера телефона. Должен быть целым беззнаковым числом.");
            } catch (InvalidGenderException e) {
                System.out.println("Ошибка: Неверный пол. Должен быть 'f' или 'm'.");
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.parse(dateString);
    }

    private static long parsePhoneNumber(String phoneNumberString) {
        return Long.parseLong(phoneNumberString);
    }

    private static char parseGender(String genderString) throws InvalidGenderException {
        if (genderString.length() != 1 || (genderString.charAt(0) != 'f' && genderString.charAt(0) != 'm')) {
            throw new InvalidGenderException("Неверный пол. Должен быть 'f' или 'm'.");
        }
        return genderString.charAt(0);
    }

    private static void saveToFile(String lastName, String firstName, String middleName, Date birthDate, long phoneNumber, char gender) throws IOException {
        File file = new File(lastName + ".txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true); // true - добавляет данные в конец файла
            writer.write(lastName + " " + firstName + " " + middleName + " " + birthDate.toString() + " " + phoneNumber + " " + gender);
            writer.write(System.lineSeparator());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

class InvalidGenderException extends Exception {
    public InvalidGenderException(String message) {
        super(message);
    }
}