package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 字体Utils
 */
@Slf4j
public class FontUtil {

	private static final List<String> FONT_NAME_USEABLE_LIST = new ArrayList<String>();
	private static int FONT_NAME_USEABLE_LIST_SIZE = 0;

	private static String[] fontNameArr = new String[] { "Arial", "Arial Black", "Arial Narrow", "Verdana", "Georgia", "Times New Roman", "Trebuchet MS", "Courier New", "Impact", "Comic Sans MS", "Tahoma", "Courier", "Lucida Sans Unicode", "Lucida Console", "Garamond", "MS Sans Serif", "MS Serif", "Palatino Linotype", "Symbol", "Bookman Old Style" };
	private static int fontNameArrLength = fontNameArr.length;

	static {
		Font[] fontArr = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		int fontArrLength = (fontArr != null ? fontArr.length : 0);

		StringBuilder fontArrSb = new StringBuilder(",");
		for (int i = 0; i < fontArrLength; i++) {
			fontArrSb.append(fontArr[i].getFamily()).append(",");
		}
		String fontArrStr = fontArrSb.toString();
		FONT_NAME_USEABLE_LIST.clear();
		for (int i = 0; i < fontNameArrLength; i++) {
			if (fontArrStr.indexOf("," + fontNameArr[i] + ",") > -1) {
				FONT_NAME_USEABLE_LIST.add(fontNameArr[i]);
			}
		}
		FONT_NAME_USEABLE_LIST_SIZE = FONT_NAME_USEABLE_LIST.size();
		log.info("FONT_NAME_USEABLE_LIST=" + FONT_NAME_USEABLE_LIST);
		log.info("FONT_NAME_USEABLE_LIST_SIZE=" + FONT_NAME_USEABLE_LIST_SIZE);
	}

	/**
	 * 以下是不能正常显示的字体<br>
	 * Font[family=Marlett,name=Marlett,style=plain,size=45]<br>
	 * Font[family=Bookshelf Symbol 7,name=Bookshelf Symbol
	 * 7,style=italic,size=45]<br>
	 * Font[family=Estrangelo Edessa,name=Estrangelo Edessa,style=plain,size=45]<br>
	 * Font[family=Gautami,name=Gautami,style=italic,size=45]<br>
	 * Font[family=Jokerman,name=Jokerman,style=bold,size=45]
	 * Font[family=Latha,name=Latha,style=plain,size=45]
	 * Font[family=Mangal,name=Mangal,style=bold,size=45] <br>
	 * Font[family=Marlett,name=Marlett,style=italic,size=45]
	 * Font[family=Mistral,name=Mistral,style=bold,size=45] <br>
	 * Font[family=MS Outlook,name=MS Outlook,style=italic,size=45] <br>
	 * Font[family=MS Reference Specialty,name=MS Reference
	 * Specialty,style=italic,size=45] <br>
	 * Font[family=MS UI Gothic,name=MS UI Gothic,style=bold,size=45]
	 * Font[family=Old English Text MT,name=Old English Text
	 * MT,style=italic,size=45]
	 * Font[family=Parchment,name=Parchment,style=bold,size=45]
	 * Font[family=Raavi,name=Raavi,style=plain,size=45]
	 * Font[family=Shruti,name=Shruti,style=bold,size=45]
	 * Font[family=Symbol,name=Symbol,style=bold,size=45]
	 * Font[family=Tunga,name=Tunga,style=plain,size=45]
	 * Font[family=Webdings,name=Webdings,style=bold,size=45]
	 * Font[family=Wingdings,name=Wingdings,style=bold,size=45]
	 * Font[family=Wingdings 2,name=Wingdings 2,style=italic,size=45]
	 * Font[family=Wingdings 3,name=Wingdings 3,style=bold,size=45]
	 * Font[family=宋体-PUA,name=宋体-PUA,style=bold,size=45] <br>
	 * Font[family=Bookshelf Symbol 7,name=Bookshelf Symbol
	 * 7,style=bold,size=45] <br>
	 * Font[family=Estrangelo Edessa,name=Estrangelo
	 * Edessa,style=italic,size=45]
	 * Font[family=Gautami,name=Gautami,style=italic,size=45]
	 * Font[family=Jokerman,name=Jokerman,style=italic,size=45]
	 * Font[family=Latha,name=Latha,style=italic,size=45]
	 * Font[family=Mangal,name=Mangal,style=bold,size=45]
	 * Font[family=Marlett,name=Marlett,style=bold,size=45]
	 * Font[family=Mistral,name=Mistral,style=plain,size=45] <br>
	 * Font[family=MS Outlook,name=MS Outlook,style=italic,size=45] <br>
	 * Font[family=MS Reference Specialty,name=MS Reference
	 * Specialty,style=italic,size=45] <br>
	 * <br>
	 * 以下是极差的字体<br>
	 * Font[family=Freestyle Script,name=Freestyle Script,style=bold,size=45]<br>
	 * Font[family=Kunstler Script,name=Kunstler Script,style=plain,size=45]<br>
	 * Font[family=华文彩云,name=华文彩云,style=plain,size=45]<br>
	 * <br>
	 * 以下是很好的字体 <br>
	 * Font[family=Algerian,name=Algerian,style=plain,size=45]<br>
	 * Font[family=Algerian,name=Algerian,style=italic,size=45]
	 * 
	 * @param style
	 *            PLAIN = 0 BOLD = 1 ITALIC = 2
	 * @param fontSize
	 * @return
	 */
	public static Font getRondomFont(int style, int fontSize) {
		if (style < 0) {
			style = 0;
		} else if (style > 2) {
			style = 2;
		}
		if (fontSize < 0) {
			fontSize = 20;
		}
		return new Font(FONT_NAME_USEABLE_LIST.get(MathUtil.RANDOM.nextInt(FONT_NAME_USEABLE_LIST_SIZE)), style, fontSize);
	}

	public static void main(String[] args) {
		Font[] fontArr = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		int fontArrLength = (fontArr != null ? fontArr.length : 0);
		Font font;
		for (int i = 0; i < fontArrLength; i++) {
			font = fontArr[i];
			// font.getFamily()
			System.out.println("fontArr[" + i + "]=" + font);
		}
		font = getRondomFont(0, 35);
		System.out.println("getRondomFont(0, 35)=" + font);

	}
}