package com.example.mountaineerback.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// MessageDigest 是 Java 中的核心類，用於執行雜湊函數操作。
// 它屬於 java.security 套件，提供加密散列算法（如 MD5、SHA-1、SHA-256）的功能，這些算法主要用於生成固定長度的雜湊值。

// 常用方法
// 1. getInstance(String algorithm)：用於創建 MessageDigest 的實例
// 2. update(byte[] input)：向雜湊演算法輸入資料，如果需要加鹽，在這裡加入鹽值
//      md.update("saltValue".getBytes());
//      md.update("password".getBytes());
// 3. digest()：計算並返回最終的雜湊值，回傳值是位元組數組 (byte[])
public class Hash {
    // 產生含鹽雜湊
    public static String getHash(String password, String salt) {
        try {
            // 加密演算法: SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 加鹽
            md.update(salt.getBytes());
            // 進行加密
            byte[] bytes = md.digest(password.getBytes());
            //System.out.println(Arrays.toString(bytes));
            // 將 byte[] 透過 Base64 編碼方便儲存
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 產生鹽
    public static String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 產生雜湊
    public static String getHash(String password) {
        try {
            // 加密演算法: SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 進行加密
            byte[] bytes = md.digest(password.getBytes());
            //System.out.println(Arrays.toString(bytes));
            // 將 byte[] 透過 Base64 編碼方便儲存
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
