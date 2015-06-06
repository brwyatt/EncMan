import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Wrapper Java MessageDigest class providing access to MD2, MD5, SHA-1, SHA-256, SHA-384, and SHA-512
 * 
 * @author Bryan Wyatt - brwyatt@gmail.com
 * From modified code from AnyExample.com
 */
public class Hashes{
	public static String getMD2(String text){//Generates the MD2 hash of a given string. Returns a 32 character String.
		return getHash(text,"MD2");
	}
	public static String getMD5(String text){//Generates the MD5 hash of a given string. Returns a 32 character String.
		return getHash(text,"MD5");
	}
	public static String getSHA1(String text){//Generates the SHA-1 hash of a given string. Returns a 40 character String.
		return getHash(text,"SHA-1");
	}
	public static String getSHA256(String text){//Generates the SHA-256 hash of a given string. Returns a 64 character String.
		return getHash(text,"SHA-256");
	}
	public static String getSHA384(String text){//Generates the SHA-384 hash of a given string. Returns a 96 character String.
		return getHash(text,"SHA-384");
	}
	public static String getSHA512(String text){//Generates the SHA-512 hash of a given string. Returns a 128 character String.
		return getHash(text,"SHA-512");
	}
	private static String getHash(String text, String algorithm){
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		try {
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
		} catch (UnsupportedEncodingException e) {
			md.update(text.getBytes(), 0, text.length());
		}
		return binToHex(md.digest());
	}
	private static String binToHex(byte[] data){
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	        	halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
}