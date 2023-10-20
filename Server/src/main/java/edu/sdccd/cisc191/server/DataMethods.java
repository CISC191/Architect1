package edu.sdccd.cisc191.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;

public class DataMethods {
    public static double[][] invert2DArray(double[][] inputArray){
        int rows = inputArray.length;
        int columns = inputArray[0].length;

        double[][] newArray = new double[columns][rows];
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                newArray[j][i] = inputArray[i][j];
            }
        }

        return newArray;
    }

    // JSON String to Object
    public static JsonNode decodeJson(String rawData) throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        JsonNode root = map.readTree(rawData);
        return root;
    }

    // JSON Object to String
    public static String encodeJson(JsonNode root) throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        String output = map.writeValueAsString(root);
        return output;
    }

    // mergeJson returns a single JSON String with elements from both API calls,
    // used for internal methods and class interactions
    public static String mergeStockJson(String json1, String json2) throws JsonProcessingException {
        JsonNode root1 = decodeJson(json1);
        JsonNode root2 = decodeJson(json2);

        ObjectMapper map = new ObjectMapper();
        ObjectNode root3 = map.createObjectNode();

        root3.set("name",root1.get("name"));
        root3.set("sector",root1.get("finnhubIndustry"));
        root3.set("description",root1.get("name"));
        root3.set("price",root2.get("c"));
        root3.put("last_updated",System.currentTimeMillis());
        root3.put("stock_version",StockBuilder.stockJsonVersion);

        return encodeJson(root3);
    }

    public static String mergeJson(String json1, String json2, String[] inKeys1, String[] inKeys2) throws JsonProcessingException {
        return mergeJson(json1,json2,inKeys1,inKeys1,inKeys2,inKeys2);
    }

    public static String mergeJson(String json1, String json2, String[] inKeys1, String[] inKeys2, String[] outKeys1, String[] outKeys2) throws JsonProcessingException {
        JsonNode root1 = decodeJson(json1);
        JsonNode root2 = decodeJson(json2);

        ObjectMapper map = new ObjectMapper();
        ObjectNode root3 = map.createObjectNode();

        for (int i = 0; i < inKeys1.length; i++){
            root3.set(outKeys1[i],root1.get(inKeys1[i]));
        }

        for (int i = 0; i < inKeys2.length; i++){
            root3.set(outKeys2[i],root2.get(inKeys2[i]));
        }

        return encodeJson(root3);
    }

    public static void createFile(String path, String filename, String content) throws FileNotFoundException{
        createFile(path + "\\" + filename, content);
    }

    public static void createFile(String reference, String content) throws FileNotFoundException {
        PrintWriter result = new PrintWriter(reference);
        result.print(content);
        result.close();
    }

    public static String readFile(String path, String filename) throws IOException {
        return readFile(path + "\\" + filename);
    }

    public static String readFile(String reference) throws IOException {
        FileInputStream stream = new FileInputStream(reference);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String content = reader.readLine();

        reader.close();
        stream.close();

        return content;
    }
}
