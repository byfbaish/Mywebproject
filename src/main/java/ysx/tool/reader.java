package ysx.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class reader {

    public static String getString(InputStream inputStream2) {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = inputStream2;
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
        }

        return builder.toString();
    }

    public static String getString(BufferedReader reader2) {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {

            reader = reader2;
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
        }

        return builder.toString();
    }
}
