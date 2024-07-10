package br.com.fiap.msbatch.utils;

import java.io.File;

public class Utilitarios {

    public String findFileByExtension(String directoryPath, String extension) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(extension)) {
                        return file.getName();
                    }
                }
            }
        }
        return null;
    }

    public String getNomeArquivo(String path) {
        String directoryPath = path;
        String extension = ".csv";

        String foundFile = findFileByExtension(directoryPath, extension);

        if (foundFile != null) {
            System.out.println("Arquivo encontrado: " + foundFile);
            return (directoryPath + foundFile);
        } else {
            return "";
        }
    }
}