package implementation;


import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import edu.rit.util.Hex;
import edu.rit.util.Packing;

/**
 * uses Electronic Codebook Mode to encrypt a file
 * @param args	array of length 3 containing the key, plaintext file to read, and ciphertext file to write, respectively
 */
public class BFencrypt
{
	public static void main(String[] args)
	{
		BlockCipher cipher = new Blowfish01();
		byte[] plaintext;
		byte[] key;
		
		if(args.length!=4)
		{
			/*TODO: Print help and exit*/
		}

		
		//second arg: Key		
		
		try{			
			if(args[1].length() != 16){
				throw new IllegalArgumentException();
			}
			key=Hex.toByteArray(args[0]);
		} catch (IllegalArgumentException e) {
			System.err.println("Argument 2: Key must be a string of hexadecimal characters.");
			return;
		}
		
		
		
		//third arg source file
		
		FileInputStream srcfile=null;
		try
		{
			srcfile = new FileInputStream(new File(args[2]));
		}
		catch( FileNotFoundException e){
			System.err.printf("inputfile: File not found: %s",args[2]);
			return;
		} 
		
		
		//fourth arg dest file
		
		FileOutputStream dstfile=null;
		try
		{
			dstfile = new FileOutputStream(args[3]);
		}
		catch( FileNotFoundException e ){
			System.err.printf("outputfile: Unable to open file for writing: \"%s\"%n%s%n", args[2],e.getMessage());
			return;
		} 
		
		
		cipher.setKey(key);

		
		
		
		
		try
		{
			while(read.available()>0)
			{
				boolean write0s=false;
		
				int i=read.read(plaintext);
				/*for(i=0; i<8 && read.available()>0; i++)
				{
					plaintext[i]=read.read();
				}*/
			
				if(i<8)	//fill block with a 1 then rest 0s
				{
					plaintext[i++]=1;
					if(i<8)
						for(; i<8; i++) plaintext[i]=0;
					else	//fill another block with all 0s
						write0s=true;
				}

				cipher.encrypt(plaintext);		
				System.out.println(Hex.toString(plaintext));
			
				write.write(plaintext);
			
				if(write0s)	//add a final plaintext block, padded with 0s
				{
					for(i=0; i<8; i++) plaintext[i]=0;
					cipher.encrypt(plaintext);
					write.write(plaintext);
				}
			
				//cipher.decrypt(plaintext);
				//System.out.println(Hex.toString(plaintext));
			}
			read.close();
			write.close();
		}
		
		
		//first arg mode
		
		if(args[0].equals("-d") || args[0].equals("--decrypt") || args[0].equals("d") || args[0].equals("decrypt")){
			
		}else if(args[0].equals("-d") || args[0].equals("--decrypt") || args[0].equals("d") || args[0].equals("decrypt")){
			
		} else {
			
		}
		
		
		
	}
	
	
	private void encryptECB(BlockCipher cipher, byte[] text){
		cipher.encrypt(text);
	}
	
	private void decryptECB(){Blowfish cipher, byte[] text
		cipher.decrypt(text)
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
