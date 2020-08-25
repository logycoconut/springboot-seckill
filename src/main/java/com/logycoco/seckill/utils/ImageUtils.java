package com.logycoco.seckill.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author hall
 * @date 2020/8/25
 */
public class ImageUtils {

    /**
     * 0-9 a-Z
     */
    private static final char[] LETTERS = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                    'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final Random RANDOM = new Random();

    private ImageUtils() {
        throw new IllegalStateException("这是一个工具类");
    }

    /**
     * 生成随机验证码
     *
     * @return 图片缓冲
     */
    public static BufferedImage createVerifyCodeImage(String verifyCode) {
        // 定义宽高
        int width = 80;
        int height = 32;

        //生成图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 背景
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);

        // 背景上生成矩形框
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        // 生成干扰点
        for (int i = 0; i < 50; i++) {
            int x = RANDOM.nextInt(width);
            int y = RANDOM.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }

        //
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 10, 25);
        g.dispose();

        return image;
    }

    /**
     * 生成验证码
     *
     * @param codeLength 要生成的验证码长度
     * @return 验证码
     */
    public static String generateVerifyCode(int codeLength) {
        int length = LETTERS.length;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            buffer.append(LETTERS[RANDOM.nextInt(length)]);
        }

        return buffer.toString();
    }

}
