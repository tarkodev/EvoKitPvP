package fr.tarkod.kitpvp.kit;

import java.io.*;

public class FileUtilsKit {


    public static String load(File file) {
        StringBuilder sb = new StringBuilder();

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void save(File file, String content) {
        try {
            file.getParentFile().getParentFile().mkdir();
            file.getParentFile().mkdir();
            file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
