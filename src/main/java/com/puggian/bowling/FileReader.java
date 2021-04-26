package com.puggian.bowling;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private String path;

    public FileReader(String filePath) {
        this.path = filePath;
    }

    public List<String> readAsStringList() throws IOException {
        InputStream inputStream = new FileInputStream(path);
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

}
