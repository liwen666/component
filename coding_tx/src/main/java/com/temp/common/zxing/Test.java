package com.temp.common.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

//引用辅助类


public class Test {

	static public void encode(String content,String imgpath) {
		try {
			BitMatrix byteMatrix;

			byteMatrix = new MultiFormatWriter().encode(new String(content.getBytes("utf8"),"iso-8859-1"),
					BarcodeFormat.QR_CODE, 200, 200);
			File file = new File(imgpath);

			MatrixToImageWriter.writeToFile(byteMatrix, "png", file);
			System.out.println("成功生成二维码");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static    public String decode(String imgPath) {
		String response = "";
		try {
			Reader reader = new MultiFormatReader();

			File file = new File(imgPath);
			BufferedImage image;
			try {
				image = ImageIO.read(file);
				if (image == null) {
					System.out.println("Could not find image");
				}
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				Result result;
				Hashtable hints = new Hashtable();
				hints.put(DecodeHintType.CHARACTER_SET, "utf8");
				result = new MultiFormatReader().decode(bitmap, hints);

				System.out.println("解析出的结果如下：");
				System.out.println("result = "+ result.toString());
				System.out.println("resultFormat = "+ result.getBarcodeFormat());
				System.out.println("resultText = "+ result.getText());

				response = result.toString();

			} catch (IOException ioe) {
				System.out.println(ioe.toString());
			} catch (ReaderException re) {
				System.out.println(re.toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	public static void main(String[] args) {
		args = new String[2];
		System.out.println(System.getProperty("user.dir"));
		args[0] ="老婆别生气了，我还是很爱你的！么么哒……" ;
		args[1] = System.getProperty("user.dir")+"\\coding_tx\\src\\main\\java\\com\\temp\\common\\zxing\\test.png";
		if (args.length != 2) {
			System.out.println("java -cp core.jar;javase.jar;zxing_test.jar Test param1 param2");
			System.out.println("\tparam1: 二维码图片需要包含的文字信息");
			System.out.println("\tparam2: 二维码图片输出路径");
		} else {
			Test.encode(args[0], args[1]);
		}
		String decode = Test.decode(args[1]);
		System.out.println(decode);
	}

}
