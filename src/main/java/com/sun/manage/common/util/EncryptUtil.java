package com.sun.manage.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.MessageDigest;

/**
 * 描述:MD5加密工具类
 * User derek
 * Date 2015/12/11.
 * Version: V1.0
 */
public class EncryptUtil {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 10;

    /**
     * 采用java自带的MD5加密
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String source,byte[] salt) {
        //构造方法中：
        //第一个参数：明文，原始密码
        //第二个参数：盐，通过使用随机数
        //第三个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
        Md5Hash md5Hash = new Md5Hash(source, salt, HASH_INTERATIONS);
        return md5Hash.toString();
    }

    public static void main(String[] args) {
        System.out.println("123456 加密后:"+ EncryptUtil.MD5("123456"));
        System.out.println("root 加密后:"+ EncryptUtil.MD5("root"));
        System.out.println(EncryptUtil.MD5("加密"));
        System.out.println(EncryptUtil.MD5("2000000000161007999995soxan201403271409"));
        String salt = UUIDUtil.generateShortUuid();
        System.out.println(salt);
        System.out.println(entryptPassword("123456",salt.getBytes()));
    }
}

