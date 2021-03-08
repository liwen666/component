package com.temp.common.pdf2word.aspose;

import com.aspose.pdf.License;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;


/**
 * itext  转PDF 工具类
 * @author sunkuang
 *
 */
public class PdfUtilTest {
	public static void main(String[] args) throws InterruptedException {
//		PDFUtil.produceData(Lists.newArrayList("D:\\文档\\技术文档\\阿里巴巴Java开发手册终极版v1.3.0.pdf","D:\\文档\\技术文档\\TEST.pdf"));
//		PDFUtil.addWatermark("D:\\文档\\技术文档\\TEST.pdf","abc");
		try {
			System.out.println(getLicense());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getLicense2());
	}

		//证书获取
		public static synchronized boolean getLicense() {
			boolean result = false;
			try {

				String license2 = "<License>\n" +
						"    <Data>\n" +
						"        <Products>\n" +
						"            <Product>Aspose.Total for Java</Product>\n" +
						"            <Product>Aspose.Words for Java</Product>\n" +
						"        </Products>\n" +
						"        <EditionType>Enterprise</EditionType>\n" +
						"        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
						"        <LicenseExpiry>20991231</LicenseExpiry>\n" +
						"        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
						"    </Data>\n" +
						"    <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
						"</License>";
				ByteArrayInputStream license = new ByteArrayInputStream(license2.getBytes("UTF-8"));
				License aposeLic = new License();
				aposeLic.setLicense(license);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

	//证书获取
	public static synchronized boolean getLicense2() {
		boolean result = false;
		try {

			String license2 = "<License>\n" +
					"    <Data>\n" +
					"        <Products>\n" +
					"            <Product>Aspose.Total for Java</Product>\n" +
					"            <Product>Aspose.Words for Java</Product>\n" +
					"        </Products>\n" +
					"        <EditionType>Enterprise</EditionType>\n" +
					"        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
					"        <LicenseExpiry>20991231</LicenseExpiry>\n" +
					"        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
					"    </Data>\n" +
					"    <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
					"</License>";
			ByteArrayInputStream license = new ByteArrayInputStream(license2.getBytes("UTF-8"));
			com.aspose.words.License aposeLic = new com.aspose.words.License();
			aposeLic.setLicense(license);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}