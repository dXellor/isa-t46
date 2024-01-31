package com.isat46.isaback.util;

import java.io.File;

public class FileSystemUtils {

    public static void deleteFile(String path){
        File myObj = new File(path);
        if (myObj.delete()) {
            System.out.println("INFO: Deleted the file: " + myObj.getName());
        } else {
            System.out.println("ERROR: Failed to delete the file.");
        }
    }
}
