package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.example.Constants.*;

public class FileProcessor {
    public void processInputFile(Family family, File file, boolean isInputFile) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String command = sc.nextLine();
                if (isInputFile) {
                    processInitCommand(family, command);
                } else {
                    processInputCommand(family, command);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!! Path must be incorrect");
        }
    }

    private void processInitCommand(Family family, String command) {
        String[] commandParams = command.split(",");
        switch (commandParams[0]) {
            case ADD_FAMILY_HEAD:
                family.addHead(commandParams[1], commandParams[2]);
                break;
            case ADD_SPOUSE:
                family.addSpouse(commandParams[1], commandParams[2], commandParams[3]);
                break;
            case ADD_CHILD:
                family.addChild(commandParams[1], commandParams[2], commandParams[3]);
                break;
            default:
                System.out.println(INVALID_COMMAND);
        }
    }

    private void processInputCommand(Family family, String command) {
        String[] commandParams = command.split(" ");
        String commandResult;
        switch (commandParams[0]) {
            case ADD_CHILD:
                commandResult = family.addChild(commandParams[1], commandParams[2], commandParams[3]);
                break;

            case GET_RELATIONSHIP:
                commandResult = family.getRelationship(commandParams[1], commandParams[2]);
                break;

            default:
                commandResult = INVALID_COMMAND;
                break;
        }

        System.out.println(commandResult);
    }
}
