package com.sjqp.driverexame.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author jiashiran on 2019/1/8.
 */
public class SecurityCodeUtil {

	public static final String SECURITY_CODE = "SECURITY_CODE";
	
	// 验证码图片的宽度。  
	private static int width = 100;

	// 验证码图片的高度。  
	private static int height = 40;
	
	private static int codeCount = 4;
	
	private static int fontWidth = width/(codeCount+1);  
	private static int fontHeight = 24;  
	private static int codeY = 28;  

	public static final char[] codeSequence = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 
												'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
												'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9' };


	/**
	 * 生成验证码。
	 * @param imgBuf 验证码的图像缓存。
	 * @return 验证码的字符串表示。
	 */
	public static String generateSecurityCode(BufferedImage imgBuf) {
		Graphics2D g = imgBuf.createGraphics();

		// 创建一个随机数生成器类  
		Random random = new Random();

		// 将图像填充为白色  
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, imgBuf.getHeight());

		// 创建字体，字体的大小应该根据图片的高度来定。  
		Font font = new Font("Fixedsys",Font.BOLD, fontHeight);
		// 设置字体。  
		g.setFont(font);

		// 画边框。  
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height-1);

		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。  
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。  
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(30)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。  
			red = random.nextInt(70);
			green = random.nextInt(70);
			blue = random.nextInt(70);

			// 用随机产生的颜色将验证码绘制到图像中。  
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i+1)*(fontWidth+2)-12 , codeY);

			// 将产生的四个随机数组合在一起。  
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	/**
	 * 初始化一个图片缓存区。
	 * @return
	 */
	public static BufferedImage initImage() {
		BufferedImage buffImg = new BufferedImage(width, height,  
				                 BufferedImage.TYPE_INT_RGB); 
		
		return buffImg;
	}
}
