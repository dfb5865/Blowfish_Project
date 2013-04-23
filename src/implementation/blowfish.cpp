//Eric Warrington

/**
	Blowfish is a variable-length key, 64-bit block cipher. The algorithm consists of two parts: a key-expansion part and a data-encryption part. Key expansion converts a key of at most 448 bits into several subkey arrays totaling 4168 bytes.
	Data encryption occurs via a 16-round Feistel network. Each round consists of a key-dependent permutation, and a key-and data-dependent substitution. All operations are XORs and additions on 32-bit words. The only additional operations are four indexed array data lookups per round.
	Implementations of Blowfish that require the fastest speeds should unroll the loop and ensure that all subkeys are stored in cache.
 */



void genSubkeys(unsigned int_32 key[]);
void F(unsigned int_32 x);
//void swap();

void init()
{
#define INIT
	genSubkeys(0x243f6a88);
}

///@param n	length of the key array
void genSubkeys(unsigned int_32 key[], int n)
{
/**
	Subkeys:

	Blowfish uses a large number of subkeys. These keys must be precomputed before any data encryption or decryption.

	1. The P-array consists of 18 32-bit subkeys:
	P1, P2,..., P18.

	2. There are four 32-bit S-boxes with 256 entries each:
	S1,0, S1,1,..., S1,255;
	S2,0, S2,1,..,, S2,255;
	S3,0, S3,1,..., S3,255;
	S4,0, S4,1,..,, S4,255.
 
	1. Initialize first the P-array and then the four S-boxes, in order, with a fixed string. This string consists of the hexadecimal digits of pi (less the initial 3). For example:

	P1 = 0x243f6a88
	P2 = 0x85a308d3
	P3 = 0x13198a2e
	P4 = 0x03707344 */
	
	unsigned int_32* P={0x243f6a88, 0x85a308d3, 0x13198a2e, 0x03707344, 0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	unsigned int_32 s[4][255];

	///2. XOR P1 with the first 32 bits of the key, XOR P2 with the second 32-bits of the key, and so on for all bits of the key (possibly up to P14). Repeatedly cycle through the key bits until the entire P-array has been XORed with key bits. (For every short key, there is at least one equivalent longer key; for example, if A is a 64-bit key, then AA, AAA, etc., are equivalent keys.)
	for(int i=0; i<n; i++)
	{
		p[i] ^= key[i];
	}
	
	///3. Encrypt the all-zero string with the Blowfish algorithm, using the subkeys described in steps (1) and (2).
	
	
	///4. Replace P1 and P2 with the output of step (3).

	///5. Encrypt the output of step (3) using the Blowfish algorithm with the modified subkeys.

	///6. Replace P3 and P4 with the output of step (5).

	///7. Continue the process, replacing all entries of the P-array, and then all four S-boxes in order, with the output of the continuously-changing Blowfish algorithm.

	///In total, 521 iterations are required to generate all required subkeys. Applications can store the subkeys rather than execute this derivation process multiple times. 

}

unsigned int_64 encrypt(unsigned int_64 x)
{
#ifndef INIT
	init();
#endif
	
	///Blowfish is a Feistel network consisting of 16 rounds (see Figure 1). The input is a 64-bit data element, x.

	//Divide x into two 32-bit halves: xL, xR
	unsigned int_32 xL=x/pow(2,32), xR=x%pow(2,32);
	
	//For i = 1 to 16:
	for(int i=0; i<16; i++)
	{
		//xL = xL XOR Pi
		xL^=P[i];
		//xR = F(xL) XOR xR
		xR^=F(xL);
		//Swap xL and xR
		int_32 temp=xL;
		xL=xR;
		xR=temp;
	}
	
	//Swap xL and xR (Undo the last swap.)
	int_32 temp=xL;
	xL=xR;
	xR=temp;
	//xR = xR XOR P17
	xR^=P[17];
	//xL = xL XOR P18
	xL^=P[18]
	//Recombine xL and xR
	return xL*pow(2,32)+xR;

	///Decryption is exactly the same as encryption, except that P1, P2,..., P18 are used in the reverse order.
}


void F(unsigned int_32 x)
{
	//Function F (see Figure 2):
	//Divide x into four eight-bit quarters: a, b, c, and d
	unsigned int_8 a=x/256, d=x%256;
	//F(xL) = ((S1,a + S2,b mod 2^32) XOR S3,c) + S4,d mod 2^32
	return ((S1,a + S2,b mod 2^32) XOR S3,c) + S4,d mod 2^32;
}






