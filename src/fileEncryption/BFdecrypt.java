package fileEncryption;


import implementation.BlockCipher;
import implementation.Blowfish01;

import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import edu.rit.util.Hex;
import edu.rit.util.Packing;

/**
 * uses Electronic Codebook Mode to decrypt a file
 * @param args	array of length 3 containing the key, ciphertext file to read, and plaintext file to write, respectively
 */
public class BFdecrypt
{
	public static void main(String[] args)
	{
		BlockCipher cipher = new Blowfish01();

		byte[] ciphertext = new byte[8];
		byte[] key = new byte[8];
		
		String[] a=args;

		if(args.length!=3)
		{
			a=new String[3];
			Arrays.fill(key, (byte)0x00);
			a[1]="outfile";	//"testinput.txt";
			a[2]="plaintextfile";
		}
		else key=Hex.toByteArray(args[0]);
		
		cipher.setKey(key);

		FileInputStream read=null;
		FileOutputStream write=null;
		
		try
		{
			read=new FileInputStream(new File(a[1]));
			File outfile=new File(a[2]);
			if(!outfile.exists())
			{
				outfile.createNewFile();
			}
			write=new FileOutputStream(outfile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		try
		{
			while(read.available()>0)
			{
				boolean write0s=false;
		
				int i=read.read(ciphertext);
				/*for(i=0; i<8 && read.available()>0; i++)
				{
					ciphertext[i]=read.read();
				}*/
			
				if(i<8)	//fill block with a 1 then rest 0s
				{
					ciphertext[i++]=1;
					if(i<8)
						for(; i<8; i++) ciphertext[i]=0;
					else	//fill another block with all 0s
						write0s=true;
				}

				cipher.decrypt(ciphertext);		
				System.out.println(Hex.toString(ciphertext));
			
				write.write(ciphertext);
			
				if(write0s)	//add a final plaintext block, padded with 0s
				{
					for(i=0; i<8; i++) ciphertext[i]=0;
					cipher.decrypt(ciphertext);
					write.write(ciphertext);
				}
			
				//cipher.decrypt(plaintext);
				//System.out.println(Hex.toString(plaintext));
			}
			read.close();
			write.close();
		}
		catch(java.io.IOException e)
		{
			System.out.println("Bad stuff: \n");
			e.printStackTrace();
		}
	}
}
