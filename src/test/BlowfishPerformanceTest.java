package test;

import implementation.BlockCipher;
import implementation.Blowfish01;
import implementation.Blowfish02;

import java.util.Arrays;

public class BlowfishPerformanceTest {
	public static void main(String[] args)
	{
		if(args.length != 2){
			usage();
			System.exit(1);
		}
		if(args[0].equals("Blowfish01") || args[0].equals("Blowfish02")){
			BlockCipher cipher;
			if(args[0] == "Blowfish01"){
				cipher = new Blowfish01();
				System.out.println("Using Blowfish01");
			}else{
				cipher = new Blowfish02();
				System.out.println("Using Blowfish02");
			}
			try{
				int numEncryptions = Integer.parseInt(args[1]);
				byte[] plaintext = new byte[8];
				byte[] key = new byte[8];
				Arrays.fill(plaintext, (byte)0x00);
				Arrays.fill(key, (byte)0x00);

				final long startTime = System.currentTimeMillis();

				cipher.setKey(key);
				for(int i=0; i<numEncryptions; i++){
					cipher.encrypt(plaintext);
				}

				final long endTime = System.currentTimeMillis();
				
				System.out.println("Total encryptions: " + numEncryptions);
				System.out.println("Total execution time: " + (endTime - startTime) + " msecs");
				
			}catch(NumberFormatException e){
				usage();
				System.exit(1);
			}
		}else{
			usage();
			System.exit(1);
		}

	}

	public static void usage(){
		System.err.println("Usage: java BlowfishPerformanceTest <Blowfish01|Blowfish02> <number of encryptions>");
	}
}
