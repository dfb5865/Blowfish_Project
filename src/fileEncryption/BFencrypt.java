package fileEncryption;


import implementation.BlockCipher;
import implementation.Blowfish01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import edu.rit.util.Hex;

/**
 * uses Electronic Codebook Mode to encrypt a file
 * @param args	array of length 3 containing the key, plaintext file to read, and ciphertext file to write, respectively
 */
public class BFencrypt
{
	public static void main(String[] args)
	{
		BlockCipher cipher = new Blowfish01();
		byte[] key;

		if(args.length!=3)
		{
			/*TODO: Print help and exit*/
		}

		//First arg: Key
		try{			
			if(args[0].length() != 16){
				throw new IllegalArgumentException();
			}
			key=Hex.toByteArray(args[0]);
		} catch (IllegalArgumentException e) {
			System.err.println("Argument 2: Key must be a string of hexadecimal characters.");
			return;
		}

		//Second arg: source file
		FileInputStream srcfile=null;
		try
		{
			srcfile = new FileInputStream(new File(args[1]));
		}
		catch( FileNotFoundException e){
			System.err.printf("inputfile: File not found: %s",args[1]);
			return;
		} 


		//Third arg: dest file
		FileOutputStream dstfile=null;
		try
		{
			dstfile = new FileOutputStream(args[2]);
		}
		catch( FileNotFoundException e ){
			System.err.printf("outputfile: Unable to open file for writing: \"%s\"%n%s%n", args[2],e.getMessage());
			return;
		} 


		cipher.setKey(key);
		byte[] plaintext = new byte[8];
		
		try
		{
			while(srcfile.available()>0)
			{
				boolean write0s=false;

				int i=srcfile.read(plaintext);

				if(i<8)	//fill block with a 1 then rest 0s
				{
					plaintext[i]=1;
					i++;
					if(i<8)
						for(; i<8; i++){
							plaintext[i]=0;
						}
					else	//fill another block with all 0s
						write0s=true;
				}

				cipher.encrypt(plaintext);
				System.out.println(Hex.toString(plaintext));
				dstfile.write(plaintext);

				if(write0s)	//add a final plaintext block, padded with 0s
				{
					for(i=0; i<8; i++) plaintext[i]=0;
					cipher.encrypt(plaintext);
					dstfile.write(plaintext);
				}

			}
			srcfile.close();
			dstfile.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
