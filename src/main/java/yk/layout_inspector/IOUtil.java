package yk.layout_inspector;

import java.io.*;

public class IOUtil {
    public static void saveClose(Closeable closeable){
        if (closeable !=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void saveBytes(String path, byte[] bytes) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            saveClose(fileOutputStream);
        }
    }
}
