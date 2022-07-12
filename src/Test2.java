import org.apache.commons.math3.complex.Complex;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Test2{

        public static void main(String args[]){

            try {
                AudioFile audio = new AudioFile(new File("TestAudio/testAudio.wav"));

                Hash hash = new Hash();
                

                System.out.println(audio.getFileHashString());


            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
}
