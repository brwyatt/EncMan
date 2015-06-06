import java.util.ArrayList;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Bryan Wyatt - brwyatt@gmail.com
 */
public class RSA {
	private static final int SIZE=1024;
	/**
	 * Creates a new set of 1024bit (the default) RSA keys and returns an {@link ArrayList}
	 * containing the private and public RSA keys.
	 * 
	 * @return an {@link ArrayList} containing the {@link String} representations of the private and public RSA keys
	 */
	public static String[] createKeys(){
		return createKeys(SIZE);
	}
	/**
	 * Creates a new set of RSA keys of size s and returns an {@link ArrayList}
	 * containing the private and public RSA keys.
	 * 
	 * @param s			the size of the RSA keys
	 * @return an {@link ArrayList} containing the {@link String} representations of the private and public RSA keys
	 */
	public static String[] createKeys(int s){
		String[] keys=new String[2];
		BigInteger p, q;
		BigInteger n;
		BigInteger PhiN;
		BigInteger e, d;
		
		/* Step 1: Select two large prime numbers. Say p and q. */
		p = new BigInteger(s, 50, new SecureRandom());
		q = new BigInteger(s, 50, new SecureRandom());
		
		/* Step 2: Calculate n = p.q */
		n = p.multiply(q);
		
		/* Step 3: Calculate ø(n) = (p - 1).(q - 1) */
		PhiN = p.subtract(BigInteger.valueOf(1));
		PhiN = PhiN.multiply( q.subtract( BigInteger.valueOf(1)));
		
		/* Step 4: Find e such that gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
		do{
			e = new BigInteger(2*SIZE, new SecureRandom());
		}
		while((e.compareTo(PhiN) != 1)||(e.gcd(PhiN).compareTo(BigInteger.valueOf(1)) != 0));
		
		/* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
		d = e.modInverse(PhiN);

		keys[0]="("+d.toString(16).toUpperCase()+","+n.toString(16).toUpperCase()+")";//private
		keys[1]="("+e.toString(16).toUpperCase()+","+n.toString(16).toUpperCase()+")";//public
		return keys;
	}
	/**
	 * Returns an encrypted hex {@link String} of the given text using the given RSA key. The
	 * size of the text {@link String} (in bits) must be no longer than the RSA key length.
	 * 
	 * @param plaintext	the {@link String} to be encrypted
	 * @param key		the {@link String} representation of the RSA key
	 * @return a {@link String} of hex values representing the encrypted text
	 */
	public static String encrypt(String plaintext, String key){
		int blocksize=(key.length()-3)/4;
		String enc="";
		String tmp="";
		while(plaintext.length()>0){
			if(plaintext.length()>blocksize){
				tmp=plaintext.substring(0,blocksize);
				plaintext=plaintext.substring(blocksize,plaintext.length());
				enc+=runEncrypt(tmp, key);
			}else{
				enc+=runEncrypt(plaintext, key);
				plaintext="";
			}
		}
		return enc;
	}
	private static String runEncrypt(String plaintext, String key){
		return pad(((new BigInteger(plaintext.getBytes())).modPow((new BigInteger(key.substring(1,key.indexOf(",")),16)),(new BigInteger(key.substring(key.indexOf(",")+1,key.length()-1),16)))).toString(16),key.length()-key.indexOf(",")-2).toUpperCase();
	}
	/**
	 * Returns the decrypted string of the given  hex {@link String} using the given RSA key. The
	 * encrypted data hex {@link String} ) must be no longer than the RSA key length.
	 * 
	 * @param ciphertext	the {@link String} of encrypted hex values
	 * @param key			the {@link String} representation of the RSA key
	 * @return a {@link String} of decrypted text
	 */
	public static String decrypt(String ciphertext, String key){
		int blocksize=(key.length()-3)/2;
		String dec="";
		String tmp="";
		while(ciphertext.length()>0){
			if(ciphertext.length()>blocksize){
				tmp=ciphertext.substring(0,blocksize);
				ciphertext=ciphertext.substring(blocksize,ciphertext.length());
				dec+=runDecrypt(tmp, key);
			}else{
				dec+=runDecrypt(ciphertext, key);
				ciphertext="";
			}
		}
		return dec;
	}
	private static String runDecrypt(String ciphertext, String key){
		return new String(((new BigInteger(ciphertext,16)).modPow((new BigInteger(key.substring(1,key.indexOf(",")),16)),(new BigInteger(key.substring(key.indexOf(",")+1,key.length()-1),16)))).toByteArray());
	}
	
	private static String pad(String s, int keylength){
		while(s.length()<keylength){
			s="0"+s;
		}
		return s;
	}
}