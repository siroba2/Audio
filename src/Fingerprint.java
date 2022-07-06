import org.apache.commons.math3.complex.Complex;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URL;

public class Fingerprint {

    //Attributes
    private String name;
    private int time;

    private AudioInputStream audio;


    //Constructor
    public Fingerprint(String str , int time){
        this.name=str;
        this.time = time;
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(getName()));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTime() {
        return time;
    }

    public AudioInputStream getAudio(){
        return audio;
    }

    public String getName(){
        return name;
    }

    //Convert from file to bytes
    public byte[] toByteArray () throws IOException {
        File file=new File(getName());
        AudioInputStream ais= null;
        try {
            ais = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        byte[] data=new byte[ais.available()];
        ais.read(data);
        return data;
    }

    //FFT fast fourier transformation
    public double [] FFT () {
        byte[] audioBytes;
        try {
            audioBytes = toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final int totalSize = audioBytes.length;

        int chunkSize = 8 ;//TODO known which is the chunksize
        int amountPossible = totalSize / chunkSize;

        //When turning into frequency domain we'll need complex numbers:
        //Complex[][] results = new Complex[amountPossible][];
        double[] realVlas = new double[amountPossible];
        double[] imagVals = new double[amountPossible];
        Complex[] complex = new Complex[chunkSize];
        //For all the chunks:
        for (int times = 0; times < amountPossible; times++) {
            for (int i = 0; i < chunkSize; i++) {
                //Put the time domain data into a complex number with imaginary part as 0:
                complex[i] = new Complex(audioBytes[(times * chunkSize) + i], 0);
            }
            realVlas[times] = complex[times].getReal();
            imagVals[times] = complex[times].getImaginary();
        }

        double [] resultFFT = fft(realVlas,imagVals,true);
        return resultFFT;

    }






    // method from: https://stackoverflow.com/questions/3287518/reliable-and-fast-fft-in-java
        public static double[] fft(final double[] inputReal, double[] inputImag,
        boolean DIRECT) {
            // - n is the dimension of the problem
            // - nu is its logarithm in base e
            int n = inputReal.length;

            // If n is a power of 2, then ld is an integer (_without_ decimals)
            double ld = Math.log(n) / Math.log(2.0);

            // Here I check if n is a power of 2. If exist decimals in ld, I quit
            // from the function returning null.
            if (((int) ld) - ld != 0) {
                System.out.println("The number of elements is not a power of 2.");
                return null;
            }

            // Declaration and initialization of the variables
            // ld should be an integer, actually, so I don't lose any information in
            // the cast
            int nu = (int) ld;
            int n2 = n / 2;
            int nu1 = nu - 1;
            double[] xReal = new double[n];
            double[] xImag = new double[n];
            double tReal, tImag, p, arg, c, s;

            // Here I check if I'm going to do the direct transform or the inverse
            // transform.
            double constant;
            if (DIRECT)
                constant = -2 * Math.PI;
            else
                constant = 2 * Math.PI;

            // I don't want to overwrite the input arrays, so here I copy them. This
            // choice adds \Theta(2n) to the complexity.
            for (int i = 0; i < n; i++) {
                xReal[i] = inputReal[i];
                xImag[i] = inputImag[i];
            }

            // First phase - calculation
            int k = 0;
            for (int l = 1; l <= nu; l++) {
                while (k < n) {
                    for (int i = 1; i <= n2; i++) {
                        p = bitreverseReference(k >> nu1, nu);
                        // direct FFT or inverse FFT
                        arg = constant * p / n;
                        c = Math.cos(arg);
                        s = Math.sin(arg);
                        tReal = xReal[k + n2] * c + xImag[k + n2] * s;
                        tImag = xImag[k + n2] * c - xReal[k + n2] * s;
                        xReal[k + n2] = xReal[k] - tReal;
                        xImag[k + n2] = xImag[k] - tImag;
                        xReal[k] += tReal;
                        xImag[k] += tImag;
                        k++;
                    }
                    k += n2;
                }
                k = 0;
                nu1--;
                n2 /= 2;
            }

            // Second phase - recombination
            k = 0;
            int r;
            while (k < n) {
                r = bitreverseReference(k, nu);
                if (r > k) {
                    tReal = xReal[k];
                    tImag = xImag[k];
                    xReal[k] = xReal[r];
                    xImag[k] = xImag[r];
                    xReal[r] = tReal;
                    xImag[r] = tImag;
                }
                k++;
            }

            // Here I have to mix xReal and xImag to have an array (yes, it should
            // be possible to do this stuff in the earlier parts of the code, but
            // it's here to readibility).
            double[] newArray = new double[xReal.length * 2];
            double radice = 1 / Math.sqrt(n);
            for (int i = 0; i < newArray.length; i += 2) {
                int i2 = i / 2;
                // I used Stephen Wolfram's Mathematica as a reference so I'm going
                // to normalize the output while I'm copying the elements.
                newArray[i] = xReal[i2] * radice;
                newArray[i + 1] = xImag[i2] * radice;
            }
            return newArray;
        }

        /**
         * The reference bit reverse function.
         */
        private static int bitreverseReference(int j, int nu) {
            int j2;
            int j1 = j;
            int k = 0;
            for (int i = 1; i <= nu; i++) {
                j2 = j1 / 2;
                k = 2 * k + j1 - 2 * j2;
                j1 = j2;
            }
            return k;
        }


}