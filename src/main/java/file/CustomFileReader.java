package file;

import java.io.*;
import java.util.Scanner;

public class CustomFileReader {

    public double[] readFile(int length, String filename) {
        double[] data = new double[length];

        Scanner scanner;
        File file = new File(filename);
        try {

            scanner = new Scanner(file);
            scanner.useDelimiter("//s+");
            boolean temp = scanner.hasNext();
            String t = scanner.next();
            String[] tk = t.split("  ");
            String[] nk = new String[tk.length-1];
            System.arraycopy(tk, 1, nk, 0, nk.length);
            for (int i = 0; i < length; i++) {
                data[i] = Double.valueOf(nk[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
