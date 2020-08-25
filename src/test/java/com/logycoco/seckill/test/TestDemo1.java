package com.logycoco.seckill.test;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author hall
 * @date 2020-06-29 23:17
 */
public class TestDemo1 {

    @Test
    public void testRateLimiter() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
        /**
         * output: 基本上都是0.2s执行一次，符合一秒发放5个令牌的设定。
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }

    @Test
    public void createSecKillVerifyCode() {

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
        // 随机数
        Random rdm = new Random();
        // 生成干扰点
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // 生成验证码
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 10, 25);
        g.dispose();
        //输出图片
        File outputfile = new File("./image.jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * 生成验证码公式
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        Random rand = new Random();
        char[] letters=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','r',
                '0','1','2','3','4','5','6','7','8','9'};
        int length = letters.length;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 4; i++) {

            char letter = letters[rdm.nextInt(length)];
            buffer.append(letter);
        }
        return buffer.toString();
    }
}
