package com.logycoco.seckill.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author hall
 * @date 2020-07-01 22:08
 */
public class RsaUtils {

    private RsaUtils() {
        throw new IllegalStateException("这是一个工具类");
    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     *
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     * @throws IOException              IO异常
     * @throws NoSuchAlgorithmException 此算法未找到
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws NoSuchAlgorithmException, IOException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    /**
     * 将秘钥写入硬盘
     *
     * @param fileName 文件全路径
     * @param bytes    秘钥字节钥
     * @throws IOException IO异常
     */
    private static void writeFile(String fileName, byte[] bytes) throws IOException {
        File file = new File(fileName);
        Files.write(file.toPath(), bytes);
    }

    /**
     * 读取文件
     *
     * @param fileName 文件全路径
     * @return 文件字节流
     * @throws IOException IO异常
     */
    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    /**
     * 获取公钥
     *
     * @param fileName 文件全路径
     * @return 公钥
     * @throws NoSuchAlgorithmException 算法名称错误
     * @throws InvalidKeySpecException  密钥格式不规范
     * @throws IOException              IO异常
     */
    public static PublicKey getPublicKey(String fileName) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] bytes = readFile(fileName);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    /**
     * 获取私钥
     *
     * @param fileName 文件全路径
     * @return 私钥
     * @throws NoSuchAlgorithmException 算法名称错误
     * @throws InvalidKeySpecException  密钥格式不规范
     * @throws IOException              IO异常
     */
    public static PrivateKey getPrivateKey(String fileName) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] bytes = readFile(fileName);
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePrivate(encPriKeySpec);
    }

}
