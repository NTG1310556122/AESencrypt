package cn.gz.lk.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES Coder<br/>
 * secret key length:	128bit, default:	128 bit<br/>
 * mode:	ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding:	Nopadding/PKCS5Padding/ISO10126Padding/
 * @author NTG
 * 
 */
public class AES{
	
	/**
	 * 密钥算法
	*/
	private static final String KEY_ALGORITHM = "AES";
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥 
	 * @throws Exception
	 */
	public static byte[] initSecretKey() {
		//返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		//初始化此密钥生成器，使其具有确定的密钥大小
		//AES 要求密钥长度为 128
		kg.init(128);
		//生成一个密钥
		SecretKey  secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 转换密钥
	 * 
	 * @param key	二进制密钥
	 * @return 密钥
	 */
	public Key toKey(byte[] key){
		//生成密钥
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	二进制密钥
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data,byte[] key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	二进制密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
	
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	二进制密钥
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data,byte[] key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	密钥
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	二进制密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
	private static String  showByteArray(byte[] data){
		if(null == data){
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for(byte b:data){
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		AES aes=new AES();
		byte[] key = initSecretKey();//通过给定的算法，由该内部的算法实现密钥的初始化
		Key k = aes.toKey(key);//将byte类型的密钥转换成Key对象。
		Tool tool=new Tool();
		File img=new File("C:\\Users\\LK\\Desktop\\watermarked.jpg");
		String imgString=img.toString();
		FileInputStream inputStream=new FileInputStream(img);
		FileOutputStream fil=new FileOutputStream("C:\\Users\\LK\\Desktop\\解密后.jpg");
		FileOutputStream fil1=new FileOutputStream("C:\\Users\\LK\\Desktop\\加密后.txt");
		byte [] content = tool.getImageOrFile(imgString);
		byte[] encryptData1 = encrypt(content, k);// 
		fil1.write(encryptData1,0,encryptData1.length);
		byte[] decryptData = decrypt(encryptData1, k);
		fil.write(decryptData,0,decryptData.length);
	}
	public String byteToString(byte[] b) {
		String result = "";
		String temp;
		for (int i = 0; i < b.length; i++) {
			temp = Byte.toString(b[i]);
			result = result + temp + ",";
		}
		return result;
	}
}
