import java.io.IOException;

public class Test {

    public static void main(String args[]){

        Fingerprint File = new Fingerprint("TestAudio/Kate Bush - Running Up That Hill (Song Lyrics).wav",0);
        try {
            File.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}