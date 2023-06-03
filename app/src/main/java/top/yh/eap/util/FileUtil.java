package top.yh.eap.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @user
 * @date
 */
public class FileUtil {
    /**
     * 把字符串保存到指定路径的文本文件
     *
     * @param path
     */
    public static void saveText(String path, String txt) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(txt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 从指定路径读取文件
     *
     * @param path
     */
    public static String openText(String path) {
        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

}
