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

        double [] resultFFT= audio.FFT();
        System.out.println(Arrays.toString(resultFFT));


    }
}