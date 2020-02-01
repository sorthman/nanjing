package org.linlinjava.litemall.core.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptcahCode {

	/**
	 * 验证码的生成方法
	 * 
	 * @param response
	 * @return
	 */
	public static String drawImage(HttpServletResponse response) {

		// 1、使用stringbuilder类，对字符串进行处理。不用String，因为其赋值后不能改变。
		StringBuilder builder = new StringBuilder();
		// 2、产生随机数，长度为4
		for (int i = 0; i < 4; i++) {
			builder.append(randomChar());
		}

		String code = builder.toString();
		// 3、定义图片的宽度和高度，使用BufferedImage对象。
		int width = 120;
		int height = 25;

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		// 4、获取Graphics2D 绘制对象，开始绘制验证码
		Graphics2D g = bi.createGraphics();
		// 5、定义文字的字体和大小
		Font font = new Font("微软雅黑", Font.PLAIN, 20);
		// 6、定义字体的颜色
		Color color = new Color(0, 0, 0);
		// 设置字体
		g.setFont(font);
		// 设置颜色
		g.setColor(color);
		// 设置背景
		g.setBackground(new Color(226, 226, 240));
		// 开始绘制对象
		g.clearRect(0, 0, width, height);
		// 绘制形状，获取矩形对象
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		// 计算文件的坐标和间距
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int) x, (int) baseY);
		// 结束绘制
		g.dispose();

		try {
			ImageIO.write(bi, "jpg", response.getOutputStream());
			// 刷新响应流
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;

	}
	/**
     * 算数表达式验证码
     * 1.产生干扰线
     * 2.范围随机颜色
     * @param response
     * @return
     */
    public static String drawImageVerificate(HttpServletResponse response){
        //定义验证码的宽和高
        int width = 100, height = 30;
        //创建图片缓冲流
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //创建图片的上下文
        Graphics2D g = image.createGraphics();
        //产生随机对象，主要用于算数表达式的数字
        Random random = new Random();
        //设置背景
        g.setColor(getRandomColor(240,250));
        g.setFont(new Font("微软雅黑",Font.PLAIN,22));
        //开始绘制
        g.fillRect(0,0,width,height);
        //绘制干扰线
        g.setColor(getRandomColor(180,230));
        for(int i=0;i<100;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(60);
            int y1 = random.nextInt(60);
            g.drawLine(x,y,x1,y1);
        }
        //开始拼接算数验证码表达式
        int num1 = (int)(Math.random()*10 +1);
        int num2 = (int)(Math.random()*10+1);
        int result = 0;
        //产生一个0-2之间的随机整数,用来随机计算符号
        int fuhao =random.nextInt(3);
        String fuhaostr = "";
        switch(fuhao){
            case 0: fuhaostr ="+";result = num1 + num2;break;
            case 1: fuhaostr ="-";result = num1 - num2;break;
            case 2: fuhaostr ="*";result = num1 * num2;break;
        }
        //显示的计算表达式
        String calc = num1 + " " + fuhaostr +" "+ num2 + " = ?";
        g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
        //绘制表达式
        g.drawString(calc,5,25);
        //结束绘制
        g.dispose();
        try{
            ImageIO.write(image,"jpeg",response.getOutputStream());
            return String.valueOf(result);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * 产生随机颜色的方法
     */
    public static Color getRandomColor(int fc, int bc){
        //利用随机数
        Random random = new Random();
        //随机颜色
        if(fc>255)fc=255;
        if(bc>255)bc=255;
        int r = fc+random.nextInt(bc-fc);
        int g= fc+random.nextInt(bc-fc);
        int b = fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }


	/**
	 * 此方法用于用户产生随机字母和数字
	 * 
	 * @return
	 */
	private static char randomChar() {
		// 1、定义验证需要的字母和数字
		String str = "ZXCVBNMASDFGHJKLQWERTYUIOP0123456789";
		// 2、定义随机对象
		Random random = new Random();
		// 3、返回随机字符
		return str.charAt(random.nextInt(str.length()));

	}

}