package utils;

import erros.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import interfaces.general.IObjectFactory;
import static utils.Constants.*;

// ToDo Melhorar a forma de saida dos formularios e no geral
public class DataInput {

    public record ChoiseOption<T> (String label, T option) {

        public String getLabel() {
            return label;
        }

        public T getOption () {
            return option;
        }

    }

    public static <T> T getDataByUser(String labelInput, Function<String, T> convertToType, Consumer<T> validInput) {

        Scanner input = Global.getScanner();
        T finalOutput = null, converted;

        do {
            try {
                System.out.println(labelInput + " ou %s para sair".formatted(exitInputString));
                String currentInput = input.nextLine().trim();

                checkUserLeftMenu(currentInput);

                converted = convertToType.apply(currentInput);
                validInput.accept(converted);
                finalOutput = converted;

            } catch(IllegalArgumentException err) {
                System.out.printf("Error: %s\n", err.getMessage());
            }


        } while (finalOutput == null);

        return finalOutput;
    }

    public static <T> T getDataByUser(String labelInput, Function<String, T> validInput) {

        Scanner input = Global.getScanner();
        T finalOutput = null;

        do {
            try {
                System.out.println(labelInput + " ou %s para sair".formatted(exitInputString));
                String currentInput = input.nextLine().trim();

                checkUserLeftMenu(currentInput);
                finalOutput = validInput.apply(currentInput);

            } catch(IllegalArgumentException err) {
                System.out.printf("Error: %s\n", err.getMessage());
            }


        } while (finalOutput == null);

        return finalOutput;
    }

    public static String getDataByUser(String labelInput, Consumer<String> validInput) {

        Scanner input = Global.getScanner();
        String finalOutput = null, currentOutput;

        do {
            try {
                System.out.println(labelInput + " ou %s para sair".formatted(exitInputString));

                currentOutput = input.nextLine().trim();
                checkUserLeftMenu(currentOutput);

                validInput.accept(currentOutput);
                finalOutput = currentOutput;

            } catch(IllegalArgumentException err) {
                System.out.printf("Error: %s\n", err.getMessage());
            }

        } while (finalOutput == null);

        return finalOutput;
    }

    public static <T> void showOptions(List<ChoiseOption<T>> options) {
        int index = startOptionsIndex;

        for(ChoiseOption<T> option : options)
            System.out.printf("%d. %s\n", index++, option.getLabel());
    }

    public static void checkUserLeftMenu(String string) {
        if (string.equals(exitInputString))
            throw new LeftMenuException();
    }

    public static Boolean getConfirmationByUser(String label){

        List<ChoiseOption<Boolean>> options = List.of(
                new ChoiseOption<>("Sim", true),
                new ChoiseOption<>("Não", false)
        );

        return getElementFromListByUser(options,ChoiseOption::getLabel ,label).option();
    }

    public static <T> T getElementFromListByUser(List<T> list, Function<T,String> getLabelOption, String label) {

        if (list.size() == 0)
            throw new LeftMenuException("Lista vazia");

        Scanner scanner = Global.getScanner();
        List<ChoiseOption<T>> listOptions = list
                .stream()
                .map(option -> new ChoiseOption<>(getLabelOption.apply(option), option))
                .toList();

        T optionSelected = null;
        int intOption;

        do {
            try {
                System.out.println(label);
                showOptions(listOptions);
                System.out.println("Digite a opção desejada: ");
                String stringOption = scanner.nextLine();

                checkUserLeftMenu(stringOption);

                intOption = Integer.parseInt(stringOption);

                if (intOption < startOptionsIndex || intOption >= listOptions.size() + startOptionsIndex) {
                    System.out.println("Opção inválida");
                } else {
                    optionSelected = listOptions.get(intOption - startOptionsIndex).getOption();
                }

            } catch(NumberFormatException err) {
                Decoration.clearScreen();
                System.out.println("Forneça um número inteiro válido.");
            }

        } while (optionSelected == null);

        return optionSelected;


    }

    public static <T> List<T> getElementsFromListByUser(List<T> list, Function<T,String> getLabelOption, String label) {

        if (list.size() == 0)
            throw new LeftMenuException("Lista vazia");

        List<ChoiseOption<T>> listOptions = list
                .stream()
                .map(option -> new ChoiseOption<>(getLabelOption.apply(option), option))
                .toList();

        List<T> optionsSelected = new ArrayList<>();
        Scanner scanner = Global.getScanner();
        int intOption;
        T option;

        do {
            try {
                System.out.println(label);
                showOptions(listOptions);
                System.out.println("Digite a opção desejada: ");
                String stringOption = scanner.nextLine();

                if (stringOption.equals(exitInputString))
                    break;

                intOption = Integer.parseInt(stringOption);

                if (intOption < startOptionsIndex || intOption >= listOptions.size() + startOptionsIndex) {
                    System.out.println("Opção inválida");
                    continue;
                }

                option = listOptions.get(intOption - startOptionsIndex).getOption();

                if (optionsSelected.contains(option)) {
                    System.out.println("Opção já selecionada");
                    continue;
                }

                optionsSelected.add(option);

                Decoration.clearScreen();

            } catch(NumberFormatException err) {
                Decoration.clearScreen();
                System.out.println("Forneça um número inteiro válido.");
            }

        } while (true);

        return optionsSelected;
    }

    public static <T> List<T> getElementsFromListByUser(List<T> list, Function<T,String> getLabelOption, String label, int maximumSize) {

        if (list.size() == 0)
            throw new LeftMenuException("Lista vazia");


        List<T> optionsSelected = new ArrayList<>();

        List<ChoiseOption<T>> listOptions = list
                .stream()
                .map(option -> new ChoiseOption<>(getLabelOption.apply(option), option))
                .toList();

        Scanner scanner = Global.getScanner();

        int intOption;
        T option;

        do {
            try {

                if (optionsSelected.size() == maximumSize)
                    return optionsSelected;

                System.out.println(label);
                System.out.printf("Itens Escolhidos: %d/%d\n\n", optionsSelected.size(), maximumSize);
                showOptions(listOptions);
                System.out.println("Digite a opção desejada: ");

                String stringOption = scanner.nextLine();

                if (stringOption.equals(exitInputString))
                    return optionsSelected;

                intOption = Integer.parseInt(stringOption);

                if (intOption < startOptionsIndex || intOption >= listOptions.size() + startOptionsIndex) {
                    System.out.println("Opção inválida");
                    continue;
                }

                option = listOptions.get(intOption - startOptionsIndex).getOption();

                if (optionsSelected.contains(option)) {
                    System.out.println("Opção já selecionada");
                    continue;
                }

                optionsSelected.add(option);

                Decoration.clearScreen();

            } catch(NumberFormatException err) {
                Decoration.clearScreen();
                System.out.println("Forneça um número inteiro válido.");
            }

        } while (true);

    }

    public static <T> List<T> getObjectInstancesByUser(IObjectFactory<T> function, int limit) {
        ArrayList<T> data = new ArrayList<>();
        T currentData;

        while (data.size() != limit) {
            try {
               currentData = function.generate();
               data.add(currentData);
            } catch (LeftMenuException err) {
                if(data.size() == 0)
                    throw new LeftMenuException();
                return data;
            }
        }

        return data;
    }

    public static <T> List<T> getObjectInstancesByUser(IObjectFactory<T> function) {
        ArrayList<T> data = new ArrayList<>();
        T currentData;

        while (true) {
            try {
                currentData = function.generate();
                data.add(currentData);
            } catch (LeftMenuException err) {
                if(data.size() == 0)
                    throw new LeftMenuException();
                return data;
            }
        }

    }

}
