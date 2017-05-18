package file;

import optimizer.model.MothContainer;
import optimizer.model.MyPointsContainer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CustomFileWriter {

    public static void writeToFile(MyPointsContainer myPoints, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(myPoints.toString());

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(MothContainer mothContainer, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(mothContainer.toString());

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
