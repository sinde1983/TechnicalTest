package com.identity.test.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileReader {
    public List<String> readInputFile() throws IOException {
        String line="";
        List<String> regNumbers = new ArrayList<>();
        String pattern  = "([A-Z]{2}[0-9]{2}\\s?[A-Z]{3})|"
                + "(^[A-Z][0-9]{1,3}\\s?[A-Z]{3}$)|"
                + "(^[A-Z]{3}[0-9]{1,3}[A-Z]$)|"
                + "(^[0-9]{1,4}[A-Z]{1,2}$)|"
                + "(^[0-9]{1,3}[A-Z]{1,3}$)|"
                + "(^[A-Z]{1,2}[0-9]{1,4}$)|"
                + "(^[A-Z]{1,3}[0-9]{1,3}$)|"
                + "(^[A-Z]{1,3}[0-9]{1,4}$)|"
                + "(^[0-9]{3}[DX]{1}[0-9]{3}$)";

        Pattern p = Pattern.compile(pattern);
        File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/inputs");
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        for(File file : filesList) {
            try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
                while((line = bf.readLine()) !=null) {
                    Matcher m = p.matcher(line);
                    while(m.find()) {
                        regNumbers.add(m.group());
                    }
                }

            } catch (IOException e) {
                throw new IOException("Problem with reading files");
            }
        }
        return regNumbers;
    }

}
