package Algorithm;

import javax.swing.*;
import java.util.*;


public class CNF {

    private static final int MAX = 20;
    private static String[][] gram = new String[MAX][MAX]; //to store entered grammar
    private static String[] dpr = new String[MAX];
    private static int p; //np-> number of productions
    private static int np;
    private String input;
    private String string;
    private int lineCount;
    private String epselonFound = "";
    private JTextArea outputTextArea;
    private JTextArea outputCYKTextArea;
    // map variable with production ( variable -> production)
    private Map<String, List<String>> mapVariableProduction = new LinkedHashMap<>();


    public CNF() {

    }

    public void setString(String string) {
        this.string = string;
    }

    public void setInputandLineCount(String input, int lineCount) {
        this.input = input;
        this.lineCount = lineCount;

    }

    public void setOutputCYKTextArea(JTextArea outputCYKTextArea){
        this.outputCYKTextArea = outputCYKTextArea;
    }

    public Map<String, List<String>> getMapVariableProduction() {
        return mapVariableProduction;
    }

    public void convertCFGtoCNF() {

        outputTextArea.setText("");

        Console.redirectOutput(outputTextArea);
        insertNewStartSymbol();
        convertStringtoMap();
        eliminateEpselon();
        removeDuplicateKeyValue();
        eliminateSingleVariable();
        onlyTwoTerminalandOneVariable();
        eliminateThreeTerminal();

        Console.redirectOutput(null);

    }



    public void setOutputTextArea(JTextArea outputTextArea) {
        this.outputTextArea = outputTextArea;
    }


    private void eliminateSingleVariable() {

        System.out.println("Remove Single Variable in Every Production ... ");

        for (int i = 0; i < lineCount; i++) {
            removeSingleVariable();
        }

        printMap();

    }

    private void eliminateThreeTerminal() {

        System.out.println("Replace two terminal variable with new variable ... ");

        for (int i = 0; i < lineCount; i++) {
            removeThreeTerminal();
        }

        printMap();

    }

    private void eliminateEpselon() {

        System.out.println("Remove Epselon....");

        for (int i = 0; i < lineCount; i++) {
            removeEpselon();
        }

        printMap();

    }

    private String[] splitEnter(String input) {

        String[] tmpArray = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            tmpArray = input.split("\\n");
        }
        return tmpArray;
    }

    private void printMap() {

        Iterator it = mapVariableProduction.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }

        System.out.println(" ");
    }

    private void convertStringtoMap() {

        String[] splitedEnterInput = splitEnter(input);


        for (int i = 0; i < splitedEnterInput.length; i++) {

            String[] tempString = splitedEnterInput[i].split("->|\\|");
            String variable = tempString[0].trim();

            String[] production = Arrays.copyOfRange(tempString, 1, tempString.length);
            List<String> productionList = new ArrayList<String>();

            // trim the empty space
            for (int k = 0; k < production.length; k++) {
                production[k] = production[k].trim();
            }

            // import array into ArrayList
            for (int j = 0; j < production.length; j++) {
                productionList.add(production[j]);
            }

            //insert element into map
            mapVariableProduction.put(variable, productionList);
        }
    }

    private void insertNewStartSymbol() {

        String newStart = "S0";
        ArrayList<String> newProduction = new ArrayList<>();
        newProduction.add("S");

        mapVariableProduction.put(newStart, newProduction);
    }


    private void removeEpselon() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.contains("e")) {
                if (productionRow.size() > 1) {
                    productionRow.remove("e");
                    epselonFound = entry.getKey().toString();


                } else {

                    // remove if less than 1
                    epselonFound = entry.getKey().toString();
                    mapVariableProduction.remove(epselonFound);
                }
            }
        }

        // find B and eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {
                    if (epselonFound.equals(Character.toString(productionList.get(i).charAt(j)))) {

                        if (temp.length() == 2) {

                            // remove specific character in string
                            temp = temp.replace(epselonFound, "");

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(temp);
                            }

                        } else if (temp.length() == 3) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }

                        } else if (temp.length() == 4) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }
                        } else {

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains("e")) {
                                mapVariableProduction.get(entry.getKey().toString()).add("e");
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeDuplicateKeyValue() {

        System.out.println("Remove Duplicate Key Value ... ");

        Iterator itr3 = mapVariableProduction.entrySet().iterator();

        while (itr3.hasNext()) {
            Map.Entry entry = (Map.Entry) itr3.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionRow.size(); i++) {
                if (productionRow.get(i).contains(entry.getKey().toString())) {
                    productionRow.remove(entry.getKey().toString());
                }
            }
        }

        printMap();
    }

    private void removeSingleVariable() {

        Iterator itr4 = mapVariableProduction.entrySet().iterator();
        String key = null;


        while (itr4.hasNext()) {

            Map.Entry entry = (Map.Entry) itr4.next();
            Set set = mapVariableProduction.keySet();
            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    for (int k = 0; k < keySet.size(); k++) {
                        if (keySet.get(k).equals(temp)) {

                            key = entry.getKey().toString();
                            List<String> productionValue = mapVariableProduction.get(temp);
                            productionList.remove(temp);

                            for (int l = 0; l < productionValue.size(); l++) {
                                mapVariableProduction.get(key).add(productionValue.get(l));
                            }
                        }
                    }
                }
            }
        }
    }

    private Boolean checkDuplicateInProductionList(Map<String, List<String>> map, String key) {

        Boolean notFound = true;

        Iterator itr = map.entrySet().iterator();
        outerloop:

        while (itr.hasNext()) {

            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                if (productionList.size() < 2) {

                    if (productionList.get(i).equals(key)) {
                        notFound = false;
                        break outerloop;
                    } else {
                        notFound = true;
                    }
                }
            }
        }

        return notFound;
    }

    private void onlyTwoTerminalandOneVariable() {

        System.out.println("Assign new variable for two non-terminal or one terminal ... ");

        Iterator itr5 = mapVariableProduction.entrySet().iterator();
        String key = null;
        int asciiBegin = 71; //G

        Map<String, List<String>> tempList = new LinkedHashMap<>();

        while (itr5.hasNext()) {

            Map.Entry entry = (Map.Entry) itr5.next();
            Set set = mapVariableProduction.keySet();

            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();
            Boolean found1 = false;
            Boolean found2 = false;
            Boolean found = false;


            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    if (temp.length() == 3) {

                        String newProduction = temp.substring(1, 3); // SA

                        if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(mapVariableProduction, newProduction)) {
                            found = true;
                        } else {
                            found = false;
                        }

                        if (found) {

                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }

                    } else if (temp.length() == 2) { // if only two substring

                        for (int k = 0; k < keySet.size(); k++) {

                            if (!keySet.get(k).equals(Character.toString(productionList.get(i).charAt(j)))) { // if substring not equals to keySet
                                found = false;

                            } else {
                                found = true;
                                break;
                            }

                        }

                        if (!found) {
                            String newProduction = Character.toString(productionList.get(i).charAt(j));

                            if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(mapVariableProduction, newProduction)) {

                                ArrayList<String> newVariable = new ArrayList<>();
                                newVariable.add(newProduction);
                                key = Character.toString((char) asciiBegin);

                                tempList.put(key, newVariable);

                                asciiBegin++;

                            }
                        }
                    } else if (temp.length() == 4) {

                        String newProduction1 = temp.substring(0, 2); // SA
                        String newProduction2 = temp.substring(2, 4); // SA

                        if (checkDuplicateInProductionList(tempList, newProduction1) && checkDuplicateInProductionList(mapVariableProduction, newProduction1)) {
                            found1 = true;
                        } else {
                            found1 = false;
                        }

                        if (checkDuplicateInProductionList(tempList, newProduction2) && checkDuplicateInProductionList(mapVariableProduction, newProduction2)) {
                            found2 = true;
                        } else {
                            found2 = false;
                        }


                        if (found1) {

                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction1);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }

                        if (found2) {
                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction2);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }
                    }
                }
            }
        }
        mapVariableProduction.putAll(tempList);
        printMap();
    }

    private void removeThreeTerminal() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        ArrayList<String> keyList = new ArrayList<>();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        // obtain key that use to eliminate two terminal and above
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.size() < 2) {
                keyList.add(entry.getKey().toString());
            }
        }

        // find more than three terminal or combination of variable and terminal to eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            if (productionList.size() > 1) {
                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    for (int j = 0; j < temp.length(); j++) {

                        if (temp.length() > 2) {
                            String stringToBeReplaced1 = temp.substring(j, temp.length());
                            String stringToBeReplaced2 = temp.substring(0, temp.length() - j);

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];

                                if (stringToBeReplaced1.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced1, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                } else if (stringToBeReplaced2.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced2, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        } else if (temp.length() == 2) {

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];


                                for (int pos = 0; pos < temp.length(); pos++) {
                                    String tempChar = Character.toString(temp.charAt(pos));


                                    if (value.equals(tempChar)) {

                                        mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                        temp = temp.replace(tempChar, key);

                                        if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                            mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } else if (productionList.size() == 1) {

                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    if (temp.length() == 2) {

                        for (String key : keyList) {

                            List<String> keyValues = new ArrayList<>();
                            keyValues = mapVariableProduction.get(key);
                            String[] values = keyValues.toArray(new String[keyValues.size()]);
                            String value = values[0];


                            for (int pos = 0; pos < temp.length(); pos++) {
                                String tempChar = Character.toString(temp.charAt(pos));


                                if (value.equals(tempChar)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(tempChar, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public String concat(String a, String b) //concatenates unique non-terminals
    {
        String r = a;
        r = r.concat(b);
        return r;
    }

    public String search_prod(String p) //returns a concatenated String of variables which can produce string p
    {
        int j, k;
        String r = "";

        for (j = 0; j < np; j++) {
            k = 1;

            while (gram[j][k] != null) {
                if (gram[j][k].equals(p)) {
                    r = concat(r, gram[j][0]);
                }
                k++;
            }
        }
        return r;
    }

    public String gen_comb(String a, String b) //creates every combination of variables from a and b . For eg: BA * AB = {BA, BB, AA, BB}
    {
        String pri = a;
        String re = "";
        for (int i = 0; i < a.length(); i++)
            for (int j = 0; j < b.length(); j++) {
                pri = "";
                pri = pri + a.charAt(i) + b.charAt(j);
                re = re + search_prod(pri); //searches if the generated productions can be created or not
            }
        return re;
    }

    public void trialRun() {

        Console.redirectOutput(outputCYKTextArea);

        String str;
        String r;
        String pr;
        String start = "S";

        np = mapVariableProduction.size();

        Iterator itr = mapVariableProduction.entrySet().iterator();
        ArrayList<String> keyList = new ArrayList<>();

        while (itr.hasNext()) {

            Map.Entry entry = (Map.Entry) itr.next();

            if(entry.getKey().toString() != "S0"){
                keyList.add(entry.getKey().toString());
            }
        }

        for (int i = 0; i < keyList.size(); i++) {
            gram[i][0] = keyList.get(i);

            List<String> productionList = mapVariableProduction.get(keyList.get(i));

            for (int j = 0; j < productionList.size(); j++) {
                gram[i][j + 1] = productionList.get(j);
            }
        }

        String[][] matrix = new String[MAX][MAX];
        String st;
        str = string;
        System.out.println("String obtained: " + str);


        for (int i = 0; i < str.length(); i++) //Assigns values to principal diagonal of matrix
        {
            r = "";
            st = "";
            st += str.charAt(i);
            for (int j = 0; j < np; j++) {

                int k = 1;

                while (gram[j][k] != null) {
                    if (gram[j][k].equals(st)) {
                        r = concat(r, gram[j][0]);
                    }
                    k++;
                }
            }
            matrix[i][i] = r;
        }

        int ii;
        int kk;
        for (int k = 1; k < str.length(); k++) //Assigns values to upper half of the matrix
        {
            for (int j = k; j < str.length(); j++) {
                r = " ";
                for (int l = j - k; l < j; l++) {
                    pr = gen_comb(matrix[j - k][l], matrix[l + 1][j]);
                    r = concat(r, pr);
                }
                matrix[j - k][j] = r;
            }
        }

        for (int i = 0; i < str.length(); i++) //Prints the matrix
        {
            int k = 0;
            int l = str.length() - i - 1;
            for (int j = l; j < str.length(); j++) {
                String formattedOutput = String.format("%8s", matrix[k++][j] + " | ");
                System.out.printf(formattedOutput);
            }
            System.out.println(" ");
        }

        for (int i = 0; i < start.length(); i++)
            if (matrix[0][str.length() - 1].indexOf(start.charAt(i)) <= matrix[0][str.length() - 1].length()) //Checks if last element of first row contains a Start variable
            {
                System.out.println("String can be generated");
                return;
            }

            System.out.println("String cannot be generated");
        return;
    }
}



