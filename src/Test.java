import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Test {

    public static void main(String args[]){

        Fingerprint audio = new Fingerprint("TestAudio/Kate Bush - Running Up That Hill (Song Lyrics).wav",0);
        try {
            audio.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double [][] resultFFT= audio.FFT();
        printMatrix(resultFFT);
    }

    public static void printMatrix(double [][] matrix) {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter("out.txt", true); //true tells to append data.
            out = new BufferedWriter(fstream);

            for (double[] doubles : matrix) {
                out.write("\n| ");
                for (double aDouble : doubles) {
                    out.write(Double.toString(aDouble));
                    out.write(" ");
                }
                out.write("|");
            }

            out.write("\n========================== EOF ===========================\n");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        if(out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}