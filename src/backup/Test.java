package backup;

import org.apache.commons.math3.complex.Complex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

    public static void main(String args[]){

        V1 audio = new V1("TestAudio/testAudio.wav",0);
        try {
            audio.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Complex [][] resultFFT= audio.FFT();
        printMatrix(resultFFT);

        //FingerprintV2 audio = new FingerprintV2("TestAudio/Kate Bush - Running Up That Hill (Song Lyrics).wav");
        //int[] fft =audio.FFT(32,-1);
        //System.out.println(Arrays.toString(fft));


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
    public static void printMatrix(Complex[][] matrix) {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter("out.txt", true); //true tells to append data.
            out = new BufferedWriter(fstream);

            for (Complex[] doubles : matrix) {
                out.write("\n| ");
                for (Complex aDouble : doubles) {
                    out.write("(" + Double.toString(aDouble.getReal()) + ", " + Double.toString(aDouble.getImaginary()) + "i)");
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