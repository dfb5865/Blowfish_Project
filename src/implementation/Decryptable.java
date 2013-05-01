package implementation;

public interface Decryptable extends BlockCipher{
	public void decrypt(byte[] text);
}
