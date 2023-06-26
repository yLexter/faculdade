package utils;

import general.EntranceExam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataEntryValidator {
    public static void validNumberIsPositive(Number number) {
        if (number.doubleValue() < 0)
            throw new IllegalArgumentException("Número precisa ser postivio");
    }
    public static void validNote(float note) {
        if (note < 0 || note > 10)
            throw new IllegalArgumentException("Nota invalida");
    }
    public static void validStringIsNotEmpty(String string) {
        if (string.trim().equals(""))
            throw new IllegalArgumentException("A String não pode ser vazia");
    }
    public static void validAge(int age) {
        if (age <= 0)
            throw new IllegalArgumentException("O número fornecido precisa ser positivo");
    }
    public static void validCpf(String cpf) {
        if (!cpf.matches("(\\d{3}).(\\d{3}).(\\d{3})-(\\d{2})"))
            throw new IllegalArgumentException("O cpf não está no formato XXX.XXX.XXX-XX");
    }
    public static LocalDate validDate(String date) {
        Pattern regex = Pattern.compile("(0[0-9]|[1-2][0-9]|3[0-1])/(0?[1-9]|1[0-2])/(\\d{4})");
        Matcher match = regex.matcher(date);

        if (!match.find())
            throw new IllegalArgumentException("A Data não está no formato XX/XX/XXXX");

        return LocalDate.of(
                Integer.parseInt(match.group(3)),
                Integer.parseInt(match.group(2)),
                Integer.parseInt(match.group(1))
        );

    }
    public static LocalDate validDateOfBirth(String date) {
        LocalDate dateValided = validDate(date);

        if (dateValided.isAfter(LocalDate.now()))
            throw new Error("A data não pode ser no futuro");

        return dateValided;
    }
    public static void entranceExamCcore(double score) {
        if (score < EntranceExam.minimumScore || score > EntranceExam.maximumScore)
            throw new IllegalArgumentException("Nota invalida");
    }
    public static LocalTime validTime(String time) {

        if (!time.matches("(0?[0-9]|[0-1][0-9]|2[0-3]):([0-5][0-9])"))
            throw new IllegalArgumentException("o Horario não está no formato XX/XX/XXXX");

        List<Integer> times = Arrays.stream(time.split(":"))
                .map(Integer::parseInt)
                .toList();

        return LocalTime.of(
           times.get(0),
           times.get(1)
        );

    }

}


