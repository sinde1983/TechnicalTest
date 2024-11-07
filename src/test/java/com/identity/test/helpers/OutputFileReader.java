package com.identity.test.helpers;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputFileReader {
    public List<Map<String,String>> readOutputFile() throws IOException {
        String line="";
        List<Map<String,String>> expectedResults = new ArrayList<>();

        File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/outputs");
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        for(File file : filesList) {
            try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
                String[] outputKey = bf.readLine().split(",");
                while((line = bf.readLine()) !=null) {
                String[] outputValues = bf.readLine().split(",");
                expectedResults.add(constructOutput(outputKey,outputValues));
                }

            } catch (IOException e) {
                throw new IOException("Problem with reading files");
            }
        }
        expectedResults.forEach(System.out::println);
        return expectedResults;
    }

    public Map<String,String> constructOutput(String[] keys, String[] values){
        Map<String,String> output = new HashMap<>();
        for (int i=0; i<keys.length; i++){
            output.put(StringUtils.capitalize(keys[i]),values[i]);
        }return output;
    }
}
