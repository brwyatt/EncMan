import java.io.PrintStream;

/**
 * @author Bryan Wyatt - brwyatt@gmail.com
 */
public class Crypto {
	public static final String VERSION="2.0";
	public static final String AVAILABLE_FUNCTIONS="RSA.encrypt(), RSA.decrypt(), RSA.createKeys()\nTEAV.encrypt(), TEAV.decrypt(), TEAV.generateKeys()\nHashes.getMD2(), Hashes.getMD5(), Hashes.getSHA1()\nHashes.getSHA256(), Hashes.getSHA384(), Hashes.getSHA512()";
	
	public static final int VERBOSITY_ALL=0;
	public static final int VERBOSITY_HIGH=1;
	public static final int VERBOSITY_LOW=2;
	public static final int VERBOSITY_NONE=3;
	public static final int VERBOSITY_DEFAULT=VERBOSITY_LOW;
	
	public static void main(String[] args){
		System.out.println("Crypto Library - Version "+VERSION);
		System.out.println("Written by: Bryan Wyatt - brwyatt@gmail.com");
		System.out.println(" -TEAV Algorithm by Michael Lecuyer - axlradius.com");
		System.out.println("\nClasses:");
		System.out.println("\tRSA:\tImplimentation of the RSA algorithm.");
		System.out.println("\t\tContains methods for generating key pairs, encrypting, and decrypting.");
		System.out.println("\tTEAV:\tMichael Lecuyer's implimentation of the TEA (Variant) algorithm.");
		System.out.println("\t\tContains methods for generating a  random key, encrypting, and decrypting.");
		System.out.println("\tHashes:\tWrapper class for various hashing algorithms supported by Java.");
		System.out.println("\t\tContains methods for generating hashes using MD2, MD5, SHA-1, SHA-256, SHA-384, and SHA-512.");
	}

	//Test ALL Algorithms
	public static boolean test(){
		return test(System.out, VERBOSITY_DEFAULT);
	}
	public static boolean test(PrintStream output){
		return test(output, VERBOSITY_DEFAULT);
	}
	public static boolean test(int verbosity){
		return test(System.out, verbosity);
	}
	public static boolean test(PrintStream output, int verbosity){
		return (testRSA(output, verbosity) && testTEAV(output, verbosity));
	}
	
	//Test RSA Algorithm
	public static boolean testRSA(){
		return testRSA(System.out, VERBOSITY_DEFAULT);
	}
	public static boolean testRSA(PrintStream output){
		return testRSA(output, VERBOSITY_DEFAULT);
	}
	public static boolean testRSA(int verbosity){
		return testRSA(System.out, verbosity);
	}
	public static boolean testRSA(PrintStream output, int verbosity){
		if(verbosity<=VERBOSITY_LOW){output.println("RSA Test");}
		String[] RSAKeys=RSA.createKeys(1024);
		if(verbosity<=VERBOSITY_ALL){output.println("\tPRIV_KEY: "+RSAKeys[0]);}
		if(verbosity<=VERBOSITY_ALL){output.println("\tPUB_KEY: "+RSAKeys[1]);}
		String msg="TEST";
		if(verbosity<=VERBOSITY_HIGH){output.println("\tString: "+msg);}
		String encmsg=RSA.encrypt(msg,RSAKeys[0]);
		if(verbosity<=VERBOSITY_ALL){output.println("\tEncrypted: "+encmsg);}
		String decmsg=RSA.decrypt(encmsg,RSAKeys[1]);
		if(verbosity<=VERBOSITY_ALL){output.println("\tDecrypted: "+decmsg);}
		if(msg.equals(decmsg)){
			if(verbosity<=VERBOSITY_LOW){output.println("\tPass");}
			return true;
		}else{
			if(verbosity<=VERBOSITY_LOW){output.println("\tFail");}
			return false;
		}
	}
	
	//Test TEAV Algorithm
	public static boolean testTEAV(){
		return testTEAV(System.out, VERBOSITY_DEFAULT);
	}
	public static boolean testTEAV(PrintStream output){
		return testTEAV(output, VERBOSITY_DEFAULT);
	}
	public static boolean testTEAV(int verbosity){
		return testTEAV(System.out, verbosity);
	}
	public static boolean testTEAV(PrintStream output, int verbosity){
		if(verbosity<=VERBOSITY_LOW){output.println("TEAV Test");}
		String TEAVKEY=TEAV.generateKey();
		if(verbosity<=VERBOSITY_ALL){output.println("\tKEY: "+TEAVKEY);}
		String msg="TEST";
		if(verbosity<=VERBOSITY_HIGH){output.println("\tString: "+msg);}
		String encmsg=TEAV.encrypt(msg, TEAVKEY);
		if(verbosity<=VERBOSITY_ALL){output.println("\tEncrypted: "+encmsg);}
		String decmsg=TEAV.decrypt(encmsg, TEAVKEY);
		if(verbosity<=VERBOSITY_ALL){output.println("\tDecrypted: "+decmsg);}
		if(msg.equals(decmsg)){
			if(verbosity<=VERBOSITY_LOW){output.println("\tPass");}
			return true;
		}else{
			if(verbosity<=VERBOSITY_LOW){output.println("\tFail");}
			return false;
		}
	}
}