package utils;
import java.lang.reflect.Field;

import general.Room;
import general.Subject;

import java.lang.reflect.Modifier;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    public static class ConsoleTable{

        /*
         * Modify these to suit your use
         */
        private final int TABLEPADDING = 4;
        private final char SEPERATOR_CHAR = '-';

        private ArrayList<String> headers;
        private ArrayList<ArrayList<String>> table;
        private ArrayList<Integer> maxLength;
        private int rows,cols;

        /*
         * Constructor that needs two arraylist
         * 1: The headersIs is one list containing strings with the columns headers
         * 2: The content is an matrix of Strings build up with ArrayList containing the content
         *
         * Call the printTable method to print the table
         */

        public ConsoleTable(ArrayList<String> headersIn, ArrayList<ArrayList<String>> content){
            this.headers = headersIn;
            this.maxLength =  new ArrayList<Integer>();
            //Set headers length to maxLength at first
            for(int i = 0; i < headers.size(); i++){
                maxLength.add(headers.get(i).length());
            }
            this.table = content;
            calcMaxLengthAll();
        }
        /*
         * To update the matrix
         */
        public void updateField(int row, int col, String input){
            //Update the value
            table.get(row).set(col,input);
            //Then calculate the max length of the column
            calcMaxLengthCol(col);
        }
        /*
         * Prints the content in table to console
         */
        public void printTable(){
            //Take out the
            StringBuilder sb = new StringBuilder();
            StringBuilder sbRowSep = new StringBuilder();
            String padder = "";
            int rowLength = 0;
            String rowSeperator = "";

            //Create padding string containing just containing spaces
            for(int i = 0; i < TABLEPADDING; i++){
                padder += " ";
            }

            //Create the rowSeperator
            for(int i = 0; i < maxLength.size(); i++){
                sbRowSep.append("|");
                for(int j = 0; j < maxLength.get(i)+(TABLEPADDING*2); j++){
                    sbRowSep.append(SEPERATOR_CHAR);
                }
            }
            sbRowSep.append("|");
            rowSeperator = sbRowSep.toString();

            sb.append(rowSeperator);
            sb.append("\n");
            //HEADERS
            sb.append("|");
            for(int i = 0; i < headers.size(); i++){
                sb.append(padder);
                sb.append(headers.get(i));
                //Fill up with empty spaces
                for(int k = 0; k < (maxLength.get(i)-headers.get(i).length()); k++){
                    sb.append(" ");
                }
                sb.append(padder);
                sb.append("|");
            }
            sb.append("\n");
            sb.append(rowSeperator);
            sb.append("\n");

            //BODY
            for(int i = 0; i < table.size(); i++){
                ArrayList<String> tempRow = table.get(i);
                //New row
                sb.append("|");
                for(int j = 0; j < tempRow.size(); j++){
                    sb.append(padder);
                    sb.append(tempRow.get(j));
                    //Fill up with empty spaces
                    for(int k = 0; k < (maxLength.get(j)-tempRow.get(j).length()); k++){
                        sb.append(" ");
                    }
                    sb.append(padder);
                    sb.append("|");
                }
                sb.append("\n");
                sb.append(rowSeperator);
                sb.append("\n");
            }
            System.out.println(sb.toString());
        }
        /*
         * Fills maxLenth with the length of the longest word
         * in each column
         *
         * This will only be used if the user dont send any data
         * in first init
         */
        private void calcMaxLengthAll(){
            for(int i = 0; i < table.size(); i++){
                ArrayList<String> temp = table.get(i);
                for(int j = 0; j < temp.size(); j++){
                    //If the table content was longer then current maxLength - update it
                    if(temp.get(j).length() > maxLength.get(j)){
                        maxLength.set(j, temp.get(j).length());
                    }
                }
            }
        }
        /*
         * Same as calcMaxLength but instead its only for the column given as inparam
         */
        private void calcMaxLengthCol(int col){
            for(int i = 0; i < table.size(); i++){
                if(table.get(i).get(col).length() > maxLength.get(col)){
                    maxLength.set(col, table.get(i).get(col).length());
                }
            }
        }
    }
    public static <T extends Subject> Subject getSubjectWithBiggerName(List<T> subjects) {
        return subjects
                .stream()
                .reduce(subjects.get(0), (prev, curr) -> prev.getName().length() > curr.getName().length() ? prev : curr);
    }

    public static String formatDayOfWeak(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public static String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static <T> void printTable(List<T> list, Function<T, List<?>> getRow, List<String> headers) {
        ArrayList<ArrayList<String>> body = new ArrayList<>();
        ArrayList<String> arrayListHeaders = new ArrayList<>(headers);
        Integer count = 1;

        arrayListHeaders.add(0, "#");

        for (T data : list) {
            List<String> currentRow = getRow
                    .apply(data)
                    .stream()
                    .map(Object::toString)
                    .toList();

            ArrayList<String> arrayListCurrentRow = new ArrayList<>(currentRow);

            arrayListCurrentRow.add(0, count + "°");

            count++;

            body.add(arrayListCurrentRow);
        }

        new ConsoleTable(arrayListHeaders, body).printTable();
    }

    public static String numberToString(Number number) {
        return number == null ? "--" : number.toString();
    }

}
