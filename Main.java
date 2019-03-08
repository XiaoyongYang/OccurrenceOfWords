
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Developed by Xiaoyong Yang
 * tempest.txt should be put under C:\Users
 */
public class Main {

    public static void main(String[] args) throws IOException {

        int listTopOccurrences = 10;
        File file = new File("/Users/tempest.txt");
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(file));
        String inputLineFromFile;
        Map<String, Integer> Map = new HashMap<>();

        try {
            while ((inputLineFromFile = bufferedReader.readLine()) != null) {
                String[] wordsFromFile = getRegex(inputLineFromFile);

                for (String s : wordsFromFile) {
                    String key = s.toLowerCase();
                    if (key.length() > 0) {
                        if (Map.get(key) == null) {
                            Map.put(key, 1);
                        } else {
                            int value = Map.get(key).intValue();
                            value++;
                            Map.put(key, value);
                        }
                    }
                }
            }

            Set<Map.Entry<String, Integer>> entrySet = Map.entrySet();
            Map.Entry<String, Integer>[] entryArray  = occurancesSort(entrySet);
            System.out.println("The occurances of words from text file are listed as the following sorting:");
            System.out.println("Words" + " " + "(Occurances):");
            for (int i=0; i<listTopOccurrences; i++){
                System.out.println(entryArray[i].getKey() + " (" + entryArray[i].getValue()+ ")" );
            }
        }

        catch (IOException error) {
            System.out.println("IOException Error message:" + error.getMessage());
        } finally {
            bufferedReader.close();
        }
    }

    /**
     * The sorting values are the occurances of the words.
     * This sorting method will list the sorting values from maximum value to minimum value.
     */
    private static Map.Entry<String, Integer>[] occurancesSort(Set<Map.Entry<String, Integer>> entrySet) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList(entrySet);
        Map.Entry<String, Integer> temp;
        Map.Entry<String, Integer>[] entryArray = entryList.toArray(new Entry[0]);
        for (int i=0; i<entryList.size() - 1; i++) { // length of sorting for each time
            for (int j=0; j<entryList.size() - 1; j++) { // from the first element to number i element
                if (entryArray[j].getValue() < entryArray[j+1].getValue()) {
                    temp = entryArray[j];
                    entryArray[j] = entryArray[j+1];
                    entryArray[j+1] = temp;
                }
            }
        }
        return entryArray;
    }

    /**
     * Define the regular expression in searching text strings for processing the text file
     */
    private static String[] getRegex(String inputLineFromFile) {
        return inputLineFromFile.split("[] \\[\n\t\r.,;:!?(){}]");
    }
}