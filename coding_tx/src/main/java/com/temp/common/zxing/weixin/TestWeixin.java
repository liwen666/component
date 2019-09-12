package com.temp.common.zxing.weixin;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

//引用辅助类


public class TestWeixin {

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
	    //解析
        String s = System.getProperty("user.dir") + "\\coding_tx\\src\\main\\java/com/temp/common/zxing/weixin/272bc7446f0a07d024a7ef3570ee6b3.jpg";
        String decode = TestWeixin.decode(s);
		System.out.println(decode);
	}


}
class EncodWeixin{
    public static void main(String[] args) {
//        String encod ="result = wxp://f2f0K-YQ_ttarURFjH4Z1NfFyxqcpPBycGZ2\n" +
//                "&resultFormat = QR_CODE\n" +
//                "&resultText = wxp://f2f0K-YQ_ttarURFjH4Z1NfFyxqcpPBycGZ2\n" +
//                "&wxp://f2f0K-YQ_ttarURFjH4Z1NfFyxqcpPBycGZ2" ;
		        String encod =" wxp://f2f0K-YQ_ttarURFjH4Z1NfFyxqcpPBycGZ2";
        TestWeixin.encode(encod,"E:\\workspace\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\zxing\\weixin\\encode.jpg");


    }
}
