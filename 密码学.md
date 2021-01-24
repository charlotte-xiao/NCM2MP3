## 密码学基本概念

### 1.古典密码学

>  替换法

- 单表替换
- 多表替换

> 移位法

- 凯撒加密-单表位移
- 维尼吉亚密码(维热纳尔密码)-多表位移

> 古典密码破解方式

- 频率分析法--单表替换/单表位移

```java
//频率分析法
package Kaiser;

import org.junit.Test;

import java.util.*;

/**
 * @author charlottexiao
 */
public class FrequencyAnalysis {
    @Test
    public void Test() throws Exception {
        //文件加密
        String Mainpath=this.getClass().getResource("/").getPath();
        String _old = Utils.file2string(Mainpath+"article1.txt");
        String _new=KaiserDemo.encrypKaiser(_old,3);
        Utils.string2file(Mainpath+"article2.txt",_new);
        ArrayList<Map.Entry<Character, Integer>> entries=printCharCount(_new);
        for (Map.Entry<Character, Integer> entry : entries) {
            if(DE_MAX_FILE==0){
                break;
            }
            Utils.string2file(Mainpath+"pojie"+DE_MAX_FILE+".txt",KaiserDemo.decrypKaiser(_new, entry.getKey()-MAFIC_CHAR));
            DE_MAX_FILE--;
        }
    }

    private static final  char MAFIC_CHAR='e';

    private static  int DE_MAX_FILE=4;

    public static ArrayList<Map.Entry<Character, Integer>> printCharCount(String data){
        char[] chars = data.toCharArray();
        HashMap<Character,Integer> map=new HashMap<Character, Integer>();
        for (char aChar : chars) {
            if(!map.containsKey(aChar)){
                map.put(aChar,1);
            }else{
                map.put(aChar,map.get(aChar)+1);
            }
        }
        ArrayList<Map.Entry<Character, Integer>> entries = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (Map.Entry<Character, Integer> entry : entries) {
            System.out.println("字符"+entry.getKey()+":"+entry.getValue()+"次");
        }
        return entries;

    }



}

//凯撒加密和解密
package Kaiser;

/**
 * @author charlottexiao
 */
public class KaiserDemo {
    //凯撒加密
    public static String encrypKaiser(String s, int key) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder=new StringBuilder();
        for (char aChar : chars) {
            stringBuilder.append((char)(aChar+ key));
        }
        return stringBuilder.toString();
    }
    //凯撒解密
    public static String decrypKaiser(String s, int key) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder=new StringBuilder();
        for (char aChar : chars) {
            stringBuilder.append((char)(aChar- key));
        }
        return stringBuilder.toString();
    }
}


//工具类
package Kaiser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author charlottexiao
 */
public class Utils {
    public static String file2string(String path){
        FileReader reader= null;
        StringBuffer stringBuffer=new StringBuffer();
        try {
            reader = new FileReader(new File(path));
            char[] buffer=new char[1024];
            int len=-1;

            while((len=reader.read(buffer))!=-1){
                stringBuffer.append(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    public static void string2file(String path,String string)throws Exception{
        FileWriter writer=new FileWriter(new File(path));
        writer.write(string);
        writer.close();
    }

}
```

### 2.近代密码学

**恩尼格玛机:**核心使用移位法,替换法,使用机器加密,后被计算机科学之父、人工智能之父**图灵**等人员破解

### 3.现代密码学

> Base64

- Base64不是加密算法,是**可读性算法**

- Base64组成:大写A-Z,小写a-z,数字0-9,符号+和/

- base58:用在比特币中编码方式(没有数字0,大写O,大写I,小写i,+号,/号)

- 注意:

  - 字节个数%3!=0是多余的比特后面填0构成一个分组,再往后面填=

  - 末尾两个等号->字节个数%3==1
  - 末尾一个等号->字节个数%3==2
  - 末尾没有等号->字节个数%3==0

> 散列函数

常见加密方式:`MD5`,`SHA-1`,`SHA-256`,`SHA-512`

- MD5 可以将任意长度的原文生成一个128位（16字节）的哈希值(32位16进制)
- SHA-1可以将任意长度的原文生成一个160位（20字节）的哈希值(40位16进制)

> 消息摘要

- 消息摘要（Message Digest）又称为数字摘要(Digital Digest),它是一个唯一对应一个消息或文本的固定长度的值,由一个单向Hash加密函数对消息进行作用而产生

常见算法 :`MD5`,`SHA-1`,`SHA-256`,`SHA-512`

```java
package Digest;

import java.security.MessageDigest;

/**
 * @author charlottexiao
 */
public class DigestDemo {
    public static void main(String[] args)throws Exception  {
        String input="aa";
        String md5 = digest(input, "MD5");
        System.out.println(md5);

        String sha1 = digest(input, "SHA-1");
        System.out.println(sha1);

        String sha256 = digest(input, "SHA-256");
        System.out.println(sha256);

        String sha512 = digest(input, "SHA-512");
        System.out.println(sha512);
        
        //文件
        String path=this.getClass().getResource("/article1.txt").getPath();
        System.out.println(digestFile(path, "MD5"));
    }

     /**
     * @param str 原文
     * @param algorithm 加密算法
     * @return 加密后哈希值
     * @throws Exception
     */
    public static String digest(String str,String algorithm)throws Exception{
        MessageDigest digest=MessageDigest.getInstance(algorithm);
        byte[] bytes = digest.digest(str.getBytes());
        StringBuilder stringBuilder=new StringBuilder();
        for (byte b : bytes) {
            //把密文转换为16进制
            //b&0xff的原因:需要把byte数据的比特位数控制为8位即2位16进制(负数显示的是32位)
            String s = Integer.toHexString(b&0xff);
            //前面补充0
            stringBuilder.append(s.length()==1?"0"+s:s);
        }
        return stringBuilder.toString();

    }

    /**
     * @param str 文件名
     * @param algorithm 加密算法
     * @return 加密后哈希值
     * @throws Exception
     */
    public static String digestFile(String str,String algorithm)throws Exception{
        FileReader fileReader=new FileReader(new File((str)));
        StringBuilder stringBuilder=new StringBuilder();
        char[] buffer=new char[1024];
       for(int len=-1;(len=fileReader.read(buffer))>0;){
           stringBuilder.append(buffer,0,len);
       }
        MessageDigest digest=MessageDigest.getInstance(algorithm);
        byte[] bytes = digest.digest(stringBuilder.toString().getBytes());
        stringBuilder.delete(0,stringBuilder.capacity());
        for (byte b : bytes) {
            //把密文转换为16进制
            //b&0xff的原因:需要把byte数据的比特位数控制为8位即2位16进制(负数显示的是32位)
            String s = Integer.toHexString(b&0xff);
            //前面补充0
            stringBuilder.append(s.length()==1?"0"+s:s);
        }
        return stringBuilder.toString();

    }
}

```

> 对称加密

使用的加密和解密的方式是同一把密钥

1. 分类:

   - 流加密
   - 块加密

2. 常见的加密算法

   - **DES:Data Encryption Standard** ,数据加密标准,使用密钥的块加密算法
     - **密钥必须是8个字节**
   - **AES:Advanced Encryption Standard**,高级加密标准,又称Rijndael加密法,区块加密标准
     - **密钥为16个字节或者32个字节**
   - **RC4(Rivest Cipher 4)**,流加密算法，密钥长度可变
     - KSA:秘钥调度算法,用于打乱S盒的初始排列
     - PRGA:伪随机数生成算法,输出随机序列并修改S的当前顺序

   ```java
   //程序开始
   #include<stdio.h>
   #include<string.h>
   typedef unsigned longULONG;
    
   /*初始化函数*/
   void rc4_init(unsigned char*s, unsigned char*key, unsigned long Len)
   {
       int i = 0, j = 0;
       char k[256] = { 0 };
       unsigned char tmp = 0;
       for (i = 0; i<256; i++)
       {
           s[i] = i;
           k[i] = key[i%Len];
       }
   
       for (i = 0; i<256; i++)
       {
           j = (j + s[i] + k[i]) % 256;
           tmp = s[i];
           s[i] = s[j];//交换s[i]和s[j]
           s[j] = tmp;
   
       }
   }
    
   /*加解密*/
   void rc4_crypt(unsigned char*s, unsigned char*Data, unsigned long Len)
   {
       int i = 0, j = 0, t = 0;
       unsigned long k = 0;
       unsigned char tmp;
       for (k = 0; k<Len; k++)
       {
           i = (i + 1) % 256;
           j = (j + s[i]) % 256;
           tmp = s[i];
           s[i] = s[j];//交换s[x]和s[y]
           s[j] = tmp;
           t = (s[i] + s[j]) % 256;
           Data[k] ^= s[t];
       }
       printf("\n\n");
   }
    
   int main()
   {
       unsigned char s[256] = { 0 }, s2[256] = { 0 };//S-box
       char key[256] = { "justfortest" };
       char pData[512] = "这是一个用来加密的数据Data";
       unsigned long len = strlen(pData);
       int i;
       printf("pData=%s\n", pData);
       printf("key=%s,length=%d\n\n", key, strlen(key));
       rc4_init(s, (unsigned char*)key, strlen(key));//已经完成了初始化
       printf("完成对S[i]的初始化，如下：\n\n");
       for (i = 0; i<256; i++)
       {
           printf("%02X ", s[i]);
           if (i && (i + 1) % 16 == 0)putchar('\n');
       }
       printf("\n\n");
       for (i = 0; i<256; i++)//用s2[i]暂时保留经过初始化的s[i]，很重要的！！！
       {
           s2[i] = s[i];
       }
       printf("已经初始化，现在加密:\n\n");
       rc4_crypt(s, (unsigned char*)pData, len);//加密
   
       printf("pData=%s\n\n", pData);
       printf("已经加密，现在解密:\n\n");
       //rc4_init(s,(unsignedchar*)key,strlen(key));//初始化密钥
       rc4_crypt(s2, (unsigned char*)pData, len);//解密
       printf("pData=%s\n\n", pData);
       return 0;
   }
   
   //程序完
   ```

3. 加密模式

   - **ECB: Electronic codebook, 电子密码本**. 需要加密的消息按照块密码的块大小被分为数个块，并对每个块进行独立加密
   - **CBC:Cipher-block chaining,密码块链接**,每个明文块先与前一个密文块进行异或后，再进行加密。在这种方法中，每个密文块都依赖于它前面的所有明文
     - 加密时需要**iv向量**:DES加密的iv向量长度为8字节,AES加密的iv向量长度为16字节

4. 填充模式

   - **NoPadding:不填充**
     - DES加密算法下,要求原文长度必须bu是8byte的整数倍
     - AES加密算法下,要i去原文长度必须是16byte的整数倍
   - PKCS7Padding:假设每个区块大小为blockSize,若最后需要补充的字节个数为n，则填充一个长度为n且每个字节均为n的数据
   - PKCS5Padding:相对于PKCS7Padding,其限制数据块大小为8位

```java
package DES_AES;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author charlottexiao
 */
public class DesAesDemo {
    public static void main(String[] args)throws Exception{
        //加密算法(加密类型/加密模式/填充模式)
        String transformation="AES/ECB/PKCS5Padding";
        //密钥加密类型
        String key="12345678123456781234567812345678";
        //加密类型
        String algorithn="AES";
        //iv向量
        String iv="8848884888488848";
        String input = "加密文章";
        input=encrypt(input,key,transformation,algorithn,iv);
        System.out.println("加密:"+input);
        input=decrypt(input,key,transformation,algorithn,iv);
        System.out.println("解密:"+input);
        //NF7G7LENdr+MI8clQRrA4w==
    }

    /**
     * @param str 原文
     * @param key 密钥
     * @param transformation 加密算法(加密类型/加密模式/填充模式)
     * @param algorithm 加密类型
     * @param iv iv向量(CBC加密模式需要)
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String str,String key,String transformation,String algorithm,String iv) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        if(transformation.contains("CBC")){
            //加密模式Cipher.ENCRYPT_MODE,规则SecretKeySpec,iv向量(只有CBC模式才需要)
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,new IvParameterSpec(iv.getBytes()));
        }else{
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        }
        //调用加密方法
        byte[] bytes = cipher.doFinal(str.getBytes());
        return   Base64.encode(bytes);
    }

    /**
     * @param str 密文
     * @param key 密钥
     * @param transformation 加密算法(加密类型/加密模式/填充模式)
     * @param algorithm 加密类型
     * @param iv iv向量(CBC加密模式需要)
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String str,String key,String transformation,String algorithm,String iv) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        if(transformation.contains("CBC")){
            //解密模式Cipher.DECRYPT_MODE,规则SecretKeySpec,iv向量(只有CBC模式才需要)
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,new IvParameterSpec(iv.getBytes()));
        }else{
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        }

        //调用解密方法
        byte[] bytes = cipher.doFinal(Base64.decode(str));
        return  new String(bytes);
    }
}

```

> 非对称加密

- 非对称加密又称现代加密算法
- 使用公钥加密,必须使用私钥机密;使用私钥加密,必须使用公钥解密

- 常见非对称加密算法
  - RSA
    - 公钥规范:RSAPublicKeySpec and X509EncodedKeySpec
    - 密钥规范:RSAPrivateKeySpec and PKCS8EncodedKeySpec
  - ECC

```java
package RSA;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author charlottexiao
 */
public class RSADemo {
    @Test
    public void test() throws Exception {
        //保存公钥和密钥
        String algorithm="RSA";
        String mainPath=this.getClass().getResource("/").getPath();

        generateKeyToFile(algorithm,mainPath+"PublicKey.txt",mainPath+"PrivateKey.txt");
        String input="你好";
        String encode = encryptRSA(algorithm, getPrivateKey(algorithm, mainPath + "PrivateKey.txt"), input);
        System.out.println(encode);
        String decode=decryptRSA(algorithm, getPublicKey(algorithm, mainPath + "PublicKey.txt"), encode);
        System.out.println(decode);


    }

    /**
     *
     * @param algorithm 算法
     * @param publicPath 公钥路径
     * @param privatePath 私钥路由
     */
    public static void generateKeyToFile(String algorithm,String publicPath,String privatePath)throws Exception{
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(algorithm);
        //生成一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();
        FileUtils.writeStringToFile(new File(publicPath),Base64.encode(aPublic.getEncoded()), Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(privatePath),Base64.encode(aPrivate.getEncoded()), Charset.forName("UTF-8"));
    }

    /**
     * 公钥解密
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param input 密文
     * @return 明文
     */
    public static String decryptRSA(String algorithm, Key publicKey, String input)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] bytes  = cipher.doFinal(Base64.decode(input));
        return new String(bytes);
    }

    /**
     * 密钥加密
     * @param algorithm 算法
     * @param privateKey 私钥
     * @param input 明文
     * @return 密文
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte[] bytes  = cipher.doFinal(input.getBytes());
        return new String(Base64.encode(bytes));
    }

    /**
     *  获取私钥
     * @param algorithm 算法
     * @param path 私钥路径
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String algorithm,String path)throws Exception{

        KeyFactory keyFactory=KeyFactory.getInstance(algorithm);
        //私钥规则:PKCS8EncodedKeySpec
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(FileUtils.readFileToString(new File(path), Charset.defaultCharset())));
        return keyFactory.generatePrivate(keySpec);

    }

    /**
     * 获取公钥
     * @param algorithm 算法
     * @param path 公钥路径
     * @return 公钥
     */
    public static PublicKey getPublicKey(String algorithm,String path)throws Exception{
        KeyFactory keyFactory=KeyFactory.getInstance(algorithm);
        //公钥规则:X509EncodedKeySpec
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(FileUtils.readFileToString(new File(path), Charset.defaultCharset())));
        return keyFactory.generatePublic(keySpec);
    }

}
```

> 数字签名

数字签名:公钥数字签名

```java
package RSA;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author charlottexiao
 */
public class RSADemo {
    @Test
    public void test() throws Exception {
        //保存公钥和密钥
        String algorithm="RSA";
        String mainPath=this.getClass().getResource("/").getPath();

        generateKeyToFile(algorithm,mainPath+"PublicKey.txt",mainPath+"PrivateKey.txt");
        String input="你好";
        String encode = encryptRSA(algorithm, getPrivateKey(algorithm, mainPath + "PrivateKey.txt"), input);
        System.out.println(encode);
        String decode=decryptRSA(algorithm, getPublicKey(algorithm, mainPath + "PublicKey.txt"), encode);
        System.out.println(decode);


    }

    /**
     *
     * @param algorithm 算法
     * @param publicPath 公钥路径
     * @param privatePath 私钥路由
     */
    public static void generateKeyToFile(String algorithm,String publicPath,String privatePath)throws Exception{
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(algorithm);
        //生成一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();
        FileUtils.writeStringToFile(new File(publicPath),Base64.encode(aPublic.getEncoded()), Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(privatePath),Base64.encode(aPrivate.getEncoded()), Charset.forName("UTF-8"));
    }

    /**
     * 公钥解密
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param input 密文
     * @return 明文
     */
    public static String decryptRSA(String algorithm, Key publicKey, String input)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] bytes  = cipher.doFinal(Base64.decode(input));
        return new String(bytes);
    }

    /**
     * 密钥加密
     * @param algorithm 算法
     * @param privateKey 私钥
     * @param input 明文
     * @return 密文
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte[] bytes  = cipher.doFinal(input.getBytes());
        return new String(Base64.encode(bytes));
    }

    /**
     *  获取私钥
     * @param algorithm 算法
     * @param path 私钥路径
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String algorithm,String path)throws Exception{

        KeyFactory keyFactory=KeyFactory.getInstance(algorithm);
        //私钥规则:PKCS8EncodedKeySpec
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(FileUtils.readFileToString(new File(path), Charset.defaultCharset())));
        return keyFactory.generatePrivate(keySpec);

    }

    /**
     * 获取公钥
     * @param algorithm 算法
     * @param path 公钥路径
     * @return 公钥
     */
    public static PublicKey getPublicKey(String algorithm,String path)throws Exception{
        KeyFactory keyFactory=KeyFactory.getInstance(algorithm);
        //公钥规则:X509EncodedKeySpec
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(FileUtils.readFileToString(new File(path), Charset.defaultCharset())));
        return keyFactory.generatePublic(keySpec);
    }

}
```











