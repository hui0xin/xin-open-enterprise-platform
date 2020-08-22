package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码Utils
 */
@Slf4j
public class CaptchaUtil {

	// 使随机性更好
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	private static final int WIDTH_MAX = 300;
	private static final int HEIGHT_MAX = 300;

	private static int widthFix(int width) {
		return (width < 1 ? 100 : (width > WIDTH_MAX ? WIDTH_MAX : width));
	}

	private static int heightFix(int height) {
		return (height < 1 ? 50 : (height > HEIGHT_MAX ? HEIGHT_MAX : height));
	}

	/**
	 * 生成颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + RANDOM.nextInt(bc - fc);
		int g = fc + RANDOM.nextInt(bc - fc);
		int b = fc + RANDOM.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static BufferedImage createHard(int width, int height, String captchaValue) {
		return createHard(width, height, captchaValue, null, 15, 10);
	}

	/**
	 * 生成图片
	 * @param width
	 * @param height
	 * @param captchaValue
	 * @param font
	 * @param fallHead
	 * @param splitSize
	 * @return
	 */
	public static BufferedImage createHard(int width, int height, String captchaValue, Font font, int fallHead, int splitSize) {
		if (captchaValue == null) {
			throw new NullPointerException("captchaValue=" + captchaValue);
		}
		width = widthFix(width);
		height = heightFix(height);

		Font fontUse = font;
		if (fontUse == null) {
			// Font.BOLD PLAIN ITALIC
			// fontUse = FontUtil.getRondomFont(MathUtil.RANDOM.nextInt(3), 35);
			fontUse = FontUtil.getRondomFont(MathUtil.RANDOM.nextInt(3), 50);
		}
		int fontSize = fontUse.getSize();

		if (log.isDebugEnabled()) {
			log.debug("createHard(int width, int height, String captchaValue, Font font, int fallHead, int splitSize) width=" + width + " height=" + height + " captchaValue=" + captchaValue + " font=" + font + " fallHead=" + fallHead + " splitSize=" + splitSize);
		}

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		// graphics.setColor(getRandColor(200, 250));
		graphics.setColor(getRandColor(230, 250));
		graphics.fillRect(0, 0, width, height);
		for (int i = 0; i < 30; i++) {
			graphics.setColor(getRandColor(60, 200));
			int x = RANDOM.nextInt(width);
			int y = RANDOM.nextInt(height);
			int xl = RANDOM.nextInt(20);
			int yl = RANDOM.nextInt(20);
			graphics.drawLine(x, y, x + xl, y + yl);
			graphics.drawLine(x + 1, y + 1, x + xl + 1, y + yl + 1);
		}
		AffineTransform fontAT = new AffineTransform();
		fontAT.rotate(Math.toRadians(0.0D));
		int captchaValueLength = captchaValue.length();

		int fontLength = captchaValueLength * fontSize + (captchaValueLength - 1) * splitSize;

		int lastSpanHalf = 0;
		if (fontLength > width) {
			fontLength = width;
			splitSize = 2;
			fontSize = (width - (captchaValueLength - 1) * splitSize) / captchaValueLength;
			lastSpanHalf = (width - fontSize * captchaValueLength - (captchaValueLength - 1) * splitSize) / 2;
		}

		int firstX = (width - fontLength) / 2;
		int firstY = (height + fontSize) / 2 - fallHead;
		if (firstX < 1) {
			firstX = lastSpanHalf;
		}
		if (firstY < 0) {
			firstY = 0;
		}

		for (int i = 0; i < captchaValueLength; i++) {
			graphics.setColor(new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110)));
			if (RANDOM.nextInt(7) > 3) {
				fontAT.rotate(Math.toRadians(-RANDOM.nextInt(10)));
			} else {
				fontAT.rotate(Math.toRadians(RANDOM.nextInt(10)));
			}
			// fontUse = new Font(fontUse.getFontName(), fontUse.getStyle(),
			// RANDOM.nextInt(15) + fontSize);
			// MathUtil.RANDOM.nextInt(3)
			fontUse = FontUtil.getRondomFont(0, fontSize);

			fontUse = fontUse.deriveFont(fontAT);
			graphics.setFont(fontUse);
			int x = firstX + (fontSize + splitSize) * i;
			int y = firstY + RANDOM.nextInt(fallHead);
			graphics.drawString(String.valueOf(captchaValue.charAt(i)), x, y);
		}
		return bufferedImage;
	}

	public static BufferedImage createWeak(int width, int height, String captchaValue) {
		if (log.isDebugEnabled()) {
			log.debug("createWeak(int width, int height, String captchaValue) width=" + width + " height=" + height + " captchaValue=" + captchaValue);
		}

		if (captchaValue == null) {
			throw new NullPointerException("captchaValue=" + captchaValue);
		}
		width = widthFix(width);
		height = heightFix(height);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphic = bufferedImage.createGraphics();
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, width, height);
		// graphic.setColor(Color.BLACK);
		// graphic.drawRect(0, 0, width - 1, height - 1);
		// (int) (height * 0.8)
		Font font = new Font("Arial", Font.PLAIN + Font.ITALIC, 30);
		graphic.setFont(font);

		// FontRenderContext fontRenderContext = graphic.getFontRenderContext();
		// Rectangle2D rectangle2D =
		// font.getStringBounds(String.valueOf(captchaValue.charAt(0)),
		// fontRenderContext);
		// rectangle2D.getWidth();

		int captchaValueLength = captchaValue.length();
		int splitSize = 2;
		int xx = 0;
		int widthChar = (width - (captchaValueLength * splitSize)) / captchaValueLength;
		for (int i = 0; i < captchaValueLength; i++) {
			xx = i * widthChar + splitSize;
			graphic.setColor(new Color(MathUtil.RANDOM.nextInt(110), MathUtil.RANDOM.nextInt(110), MathUtil.RANDOM.nextInt(110)));
			graphic.drawString(String.valueOf(captchaValue.charAt(i)), xx, (int) (height * 0.8) + MathUtil.RANDOM.nextInt(6));
		}

		// 加点
		for (int i = 0; i < (width + height); i++) {
			graphic.setColor(new Color(MathUtil.RANDOM.nextInt(255), MathUtil.RANDOM.nextInt(255), MathUtil.RANDOM.nextInt(255)));
			graphic.drawOval(MathUtil.RANDOM.nextInt(width), MathUtil.RANDOM.nextInt(height), 1, 1);
		}

		// 加线
		for (int i = 0; i < 4; i++) {
			graphic.setColor(new Color(MathUtil.RANDOM.nextInt(255), MathUtil.RANDOM.nextInt(255), MathUtil.RANDOM.nextInt(255)));
			graphic.drawLine(0, MathUtil.RANDOM.nextInt(height), width, MathUtil.RANDOM.nextInt(height));
		}
		return bufferedImage;
	}
}
