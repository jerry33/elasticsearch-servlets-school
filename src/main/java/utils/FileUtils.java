package utils;

import java.io.*;

/**
 * Created by Jerry on 25/10/15.
 */
public class FileUtils {

    public static String readFile(String path) {
        BufferedReader bufferReader = null;
        String everything = "";
        try {
            bufferReader = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = bufferReader.readLine();
            while (line != null) {
                sb.append(line);
                line = bufferReader.readLine();
            }
            everything = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferReader != null)
                    bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return everything;
    }

    public static File[] getAllFilesInDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        return folder.listFiles();
    }

}
