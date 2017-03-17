package com.lshb.crawler.code;
//package com.taogu.crawler.code;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.net.URI;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import javax.imageio.ImageIO;
//
//import org.apache.commons.io.FileUtils;
//
///**
// * 
// * @ClassName:CheckCodeProcess
// * @Description:TODO 验证码识别程序
// * @author:fuhuaguo
// * @email:674451971@qq.com
// * @date 2016年5月26日
// */
//public class CheckCodeProcess {
//
//	/**
//	 * 是否为白色转换
//	 */
//	public static boolean isWhite(int colorInt) {
//		Color c = new Color(colorInt);
//		if ((c.getRed() + c.getGreen() + c.getBlue()) > 500) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * 移除背景色 转换为黑白模式
//	 */
//	public static BufferedImage removeBackground(BufferedImage img) throws Exception {
//		int width = img.getWidth();
//		int height = img.getHeight();
//		// 移除背景色
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				if (isWhite(img.getRGB(i, j))) {
//					img.setRGB(i, j, Color.WHITE.getRGB());
//				} else {
//					img.setRGB(i, j, Color.BLACK.getRGB());
//				}
//			}
//		}
//		return img;
//	}
//
//	/**
//	 * 移除边框 四周的
//	 */
//	public static BufferedImage removeBlank(BufferedImage img) throws Exception {
//		int width = img.getWidth();
//		int height = img.getHeight();
//		int x1 = 0;
//		int x2 = 0;
//		int y1 = 0;
//		int y2 = 0;
//
//		Label1: for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				if (!isWhite(img.getRGB(i, j))) {
//					x1 = i;
//					break Label1;
//				}
//			}
//		}
//		Label2: for (int i = width - 1; i >= 0; i--) {
//			for (int j = 0; j < height; j++) {
//				if (!isWhite(img.getRGB(i, j))) {
//					x2 = i;
//					break Label2;
//				}
//			}
//		}
//		Label3: for (int j = 0; j < height; j++) {
//			for (int i = 0; i < width; i++) {
//				if (!isWhite(img.getRGB(i, j))) {
//					y1 = j;
//					break Label3;
//				}
//			}
//		}
//		Label4: for (int j = height - 1; j >= 0; j--) {
//			for (int i = 0; i < width; i++) {
//				if (!isWhite(img.getRGB(i, j))) {
//					y2 = j;
//					break Label4;
//				}
//			}
//		}
//
//		return img.getSubimage(x1, y1, x2 - x1, y2 - y1);
//	}
//
//	/**
//	 * 对验证码中的数字进行旋转为正 为了分割字符
//	 * 
//	 * @param img
//	 * @return
//	 */
//	public static BufferedImage rotateChar(BufferedImage img) {
//		int width = img.getWidth();
//		int height = img.getHeight();
//
//		for (int j = 0; j < height; j++) {
//			int s = (int) ((height - j) * Math.tan(10 * Math.PI / 180));
//			for (int i = width - 1; i >= 0; i--) {
//				if ((i - s) >= 0) {
//					img.setRGB(i, j, img.getRGB(i - s, j));
//				} else {
//					img.setRGB(i, j, Color.WHITE.getRGB());
//				}
//			}
//		}
//		return img;
//	}
//
//	/**
//	 * 对验证码进行切割成字母图片
//	 */
//	public static List<BufferedImage> splitImage(BufferedImage img) {
//		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
//		int width = img.getWidth();
//		int height = img.getHeight();
//
//		int x1 = 0;
//		int space = width / 5 - 25; // 用于每个字母的平均间隔
//
//		Label1: for (int i = 0; i < width; i++) {
//			int count = 0;
//			for (int j = 0; j < height; j++) {
//				if (isWhite(img.getRGB(i, j))) {
//					count++;
//				}
//				if (i > (x1 + space) && (height - count) <= 0) {
//					subImgs.add(img.getSubimage(x1, 0, i - x1, height));
//					x1 = i;
//					continue Label1;
//				}
//			}
//		}
//		subImgs.add(img.getSubimage(x1, 0, width - x1, height));
//
//		return subImgs;
//	}
//
//	/**
//	 * 预加载识别库
//	 */
//	public static Map<String, Integer> loadTrainData() throws Exception {
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		File dir = new File("trainData");
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			int count = 0;
//			BufferedImage img = ImageIO.read(file);
//			for (int i = 0; i < img.getWidth(); i++) {
//				for (int j = 0; j < img.getHeight(); j++) {
//					if (!isWhite(img.getRGB(i, j))) {
//						count++;
//					}
//				}
//			}
//			map.put(file.getName().charAt(0) + "", count);
//		}
//		return map;
//	}
//
//	/**
//	 * 解析验证码图片 使用本机的tesseract
//	 */
//	public static String parseCheckCode(File codeFile) throws Exception {
//		BufferedImage img = ImageIO.read(codeFile);
//
//		String path = codeFile.getAbsolutePath();
//		String basePath = codeFile.getAbsolutePath().replace(codeFile.getName(), "");
//
//		new ImgUtil(path).resize(200, (int) (img.getHeight() * 200.0 / img.getWidth())).save(path);
//
//		// 重新读取
//		img = ImageIO.read(codeFile);
//		// 移除背景
//		img = removeBackground(img);
//		// 移除边框
//		img = removeBlank(img);
//		// 旋转字符
//		img = rotateChar(img);
//
//		ImageIO.write(img, "bmp", codeFile);
//		Runtime run = Runtime.getRuntime();
//		if (CommonUtil.getOSType().equals("mac")) {
//			run.exec("/usr/local/bin/tesseract " + path + " " + basePath + "/CheckCode" + " -psm 7");
//		} else if (CommonUtil.getOSType().equals("win")) {
//			String[] cmds = new String[] { "cmd.exe", "/C",
//					"tesseract " + path + " " + basePath + "/CheckCode" + " -psm 7" };
//			run.exec(cmds);
//		}
//		Thread.sleep(1000);
//		String code = new String(Files.readAllBytes(Paths.get(new URI(basePath + "/CheckCode.txt")))).trim();
//		code = code.replaceAll("Q", "9").replaceAll("Z", "2").replaceAll("\\W", "");
//
//		return code;
//	}
//
//	public static void main(String[] args) {
//		System.out.println("asdf13243123123!@@#';''".replaceAll("\\W", ""));
//	}
//}
