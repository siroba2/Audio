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
    public void FFT () {
        byte[] audioBytes;
        try {
            audioBytes = toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final int totalSize = audioBytes.length;

        int chunkSize = 8; //TODO known which is the chunksize
        int amountPossible = totalSize/chunkSize;


    }


}