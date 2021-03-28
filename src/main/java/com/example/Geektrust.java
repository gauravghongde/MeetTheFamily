package com.example;

import java.io.File;

import static com.example.Constants.INIT_FILE_PATH;

//java -jar ./build/libs/geektrust.jar src/main/resources/input/input1.txt

public class Geektrust {

    public static void main(String... args) {
        Family family = new Family();
        Geektrust gt = new Geektrust();
        try {
            gt.fileToProcess(family);
            gt.fileToProcess(family, args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter file location(s)!");
        }
    }

    public void fileToProcess(Family family) {
        FileProcessor processor = new FileProcessor();
        processor.processInputFile(family, new File(INIT_FILE_PATH), true);
    }

    public void fileToProcess(Family family, String filePath) {
        File file = new File(filePath);
        FileProcessor processor = new FileProcessor();
        processor.processInputFile(family, file, false);
    }
}