import java.util.Arrays;

public class Test {

    public static void main (String[] args){

        String filename = "testAudio.wav";

        // create a wave object
        Wave wave = new Wave("TestAudio/testAudio.wav");
       //Wave wave2 = new Wave("TestAudio/testAudio2.wav");
        Wave wave2 = new Wave("TestAudio/PTN.wav");

        // get the fingerprint
        byte[] fingerprint=wave.getFingerprint();
        byte[] fingerprint2 =wave2.getFingerprint();

        // dump the fingerprint
        FingerprintManager fingerprintManager=new FingerprintManager();
        fingerprintManager.saveFingerprintAsFile(fingerprint, "out/"+filename+".fingerprint");

        FingerprintManager fingerprintManager2=new FingerprintManager();
        fingerprintManager2.saveFingerprintAsFile(fingerprint, "out/"+filename+".fingerprint");

        // load fingerprint from file
        byte[] loadedFp=fingerprintManager.getFingerprintFromFile("out/"+filename+".fingerprint");
        byte[] loadedFp2=fingerprintManager2.getFingerprintFromFile("out/"+filename+".fingerprint");
		
		/*
		// fingerprint bytes checking
		for (int i=0; i<fingerprint.length; i++){
			System.out.println(fingerprint[i]+" vs "+loadedFp[i]);
		}
		*/

        String fingerprintStr = Arrays.toString(fingerprint);
        String fingerprintStr2 = Arrays.toString(fingerprint2);

        /*System.out.println(fingerprintStr);
        System.out.println(fingerprintStr2);
        System.out.println(fingerprintStr2.length());*/

        String hash = wave.hash(fingerprint, 1024);
        System.out.println(hash);

        String hash2= wave.hash(fingerprint2, 1024);
        System.out.println(hash2);

        int distance = wave.hammingDist(hash2, hash);
        System.out.println(distance);




    }
}