//package com.temp.common.pdf2word;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.aspose.cells.Workbook;
//import com.aspose.slides.Presentation;
//import com.aspose.words.Document;
//import com.aspose.words.License;
//import com.aspose.words.SaveFormat;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//
//
//
///**
// * itext  转PDF 工具类
// * @author sunkuang
// *
// */
//public class PdfUtil2 {
//	public static File Pdf(ArrayList<String> imageUrllist,
//			String mOutputPdfFileName) {
//		//Document doc = new Document(PageSize.A4, 20, 20, 20, 20); // new一个pdf文档
//		com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
//		try {
//
//			PdfWriter
//					.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); // pdf写入
//			doc.open();// 打开文档
//			for (int i = 0; i < imageUrllist.size(); i++) { // 循环图片List，将图片加入到pdf中
//				doc.newPage(); // 在pdf创建一页
//				Image png1 = Image.getInstance(imageUrllist.get(i)); // 通过文件路径获取image
//				float heigth = png1.getHeight();
//				float width = png1.getWidth();
//				int percent = getPercent2(heigth, width);
//				png1.setAlignment(Image.MIDDLE);
//				png1.scalePercent(percent + 3);// 表示是原来图像的比例;
//				doc.add(png1);
//			}
//			doc.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		File mOutputPdfFile = new File(mOutputPdfFileName); // 输出流
//		if (!mOutputPdfFile.exists()) {
//			mOutputPdfFile.deleteOnExit();
//			return null;
//		}
//		return mOutputPdfFile; // 反回文件输出流
//	}
//
//	public static int getPercent(float h, float w) {
//		int p = 0;
//		float p2 = 0.0f;
//		if (h > w) {
//			p2 = 297 / h * 100;
//		} else {
//			p2 = 210 / w * 100;
//		}
//		p = Math.round(p2);
//		return p;
//	}
//
//	public static int getPercent2(float h, float w) {
//		int p = 0;
//		float p2 = 0.0f;
//		p2 = 530 / w * 100;
//		p = Math.round(p2);
//		return p;
//	}
//
//
//	/**
//	 * 图片文件转PDF
//	 * @param filepath
//	 * @param request
//	 * @return
//	 */
//	public static String imgOfPdf(String filepath, HttpServletRequest request) {
//		boolean result = false;
//		String pdfUrl = "";
//		String fileUrl = "";
//		try {
//			ArrayList<String> imageUrllist = new ArrayList<String>(); // 图片list集合
//			imageUrllist.add(request.getSession().getServletContext()
//					.getRealPath(File.separator + filepath)); // 添加图片文件路径
//			String fles = filepath.substring(0, filepath.lastIndexOf("."));
//			pdfUrl = request.getSession().getServletContext()
//					.getRealPath(File.separator +fles + ".pdf"); // 输出pdf文件路径
//			fileUrl =fles+".pdf";
//			result = true;
//			if (result == true) {
//				File file = PdfUtil.Pdf(imageUrllist, pdfUrl);// 生成pdf
//				file.createNewFile();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return fileUrl;
//	}
//
//
//
//
//	/*public static void doc2pdf(String Address, String outPath) {
//		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
//			return;
//		}
//		try {
//			long old = System.currentTimeMillis();
//			File file = new File(outPath); // 新建一个空白pdf文档
//			FileOutputStream os = new FileOutputStream(file);
//			Document doc = new Document(Address); // Address是将要被转化的word文档
//			doc.save(
//					os,
//					SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
//			// OpenDocument, PDF, EPUB, XPS, SWF
//			// 相互转换
//			long now = System.currentTimeMillis();
//			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
//			os.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}*/
//
//	public static boolean getLicense() {
//		boolean result = false;
//		try {
//
//
//
//			InputStream is = PdfUtil.class.getClassLoader()
//					.getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
//			License aposeLic = new License();
//			aposeLic.setLicense(is);
//			result = true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//
//	public static String docOfPdf(String filePath, HttpServletRequest request) {
//
//		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
//			return "PDF格式转化失败";
//		}
//		try {
//			long old = System.currentTimeMillis();
//			String filePath1 =  request.getSession().getServletContext().getRealPath(File.separator  + filePath);
//			String filePath2 = filePath.substring(0, filePath.lastIndexOf("."));
//			String pdfPathString = filePath2+".pdf";
//			filePath2 = request.getSession().getServletContext()
//					.getRealPath(File.separator  + filePath2 + ".pdf"); // 输出pdf文件路径
//			File file = new File(filePath2); // 新建一个空白pdf文档
//			FileOutputStream os = new FileOutputStream(file);
//			Document doc = new Document(filePath1); // Address是将要被转化的word文档
//
//			doc.save(
//					os,
//					SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
//			// OpenDocument, PDF, EPUB, XPS, SWF
//			// 相互转换
//			long now = System.currentTimeMillis();
//			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
//			os.close();
//
//			return pdfPathString;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "PDF格式转化失败";
//	}
//	public static void pdfToDoc(String filePath,String docPath) {
//
//		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
//			throw new RuntimeException ("PDF格式转化失败");
//		}
//		try {
//			long old = System.currentTimeMillis();
//			File file = new File(docPath);
//			FileOutputStream os = new FileOutputStream(file);
//			Document doc = new Document(filePath);
//
//			doc.save(
//					os,
//					SaveFormat.DOCX);// 全面支持DOC, DOCX, OOXML, RTF HTML,
//			// OpenDocument, PDF, EPUB, XPS, SWF
//			// 相互转换
//			long now = System.currentTimeMillis();
//			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
//			os.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public static boolean getLicense1() {
//		boolean result = false;
//		try {
//
//			InputStream is = PdfUtil.class.getClassLoader()
//					.getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
//			com.aspose.cells.License aposeLic = new com.aspose.cells.License();
//			aposeLic.setLicense(is);
//			result = true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//
//    /**
//     * @param excelPath
//     * @param pdfPath
//     */
//    public static String exceOfPdf(String filePath, HttpServletRequest request) {
//        if (!getLicense1()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
//        	return "PDF格式转化失败";
//        }
//        try {
//            //long old = System.currentTimeMillis();
//        	//获取路径参数
//			String filePath1 =  request.getSession().getServletContext().getRealPath(File.separator + filePath);
//			String filePath2 = filePath.substring(0, filePath.lastIndexOf("."));
//			String pdfSPath = filePath2+".pdf";
//			filePath2 = request.getSession().getServletContext()
//					.getRealPath(File.separator +filePath2 + ".pdf"); // 输出pdf文件路径
//
//			//文件操作
//			File file = new File(filePath2); // 新建一个空白pdf文档
//			FileOutputStream os = new FileOutputStream(file);
//            Workbook wb = new Workbook(filePath1);// 原始excel路径
//            FileOutputStream fileOS = new FileOutputStream(file);
//            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
//            fileOS.close();
//           // long now = System.currentTimeMillis();
//            //System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
//            return pdfSPath;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "PDF格式转化失败";
//    }
//
//
//	public static boolean getLicense2() {
//		boolean result = false;
//		try {
//
//
//
//			InputStream is = PdfUtil.class.getClassLoader()
//					.getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
//			com.aspose.slides.License aposeLic = new com.aspose.slides.License();
//			aposeLic.setLicense(is);
//			result = true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//    /**
//     *
//     * @param args
//     */
//    //public static void ppt2pdf(String Address) {
//    public static String pptOfpdf(String filePath, HttpServletRequest request){
//        // 验证License
//        if (!getLicense2()) {
//        	return "PDF格式转化失败";
//        }
//        try {
//
//
//          long old = System.currentTimeMillis();
//            //File file = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/generic/web/file/pdf1.pdf");// 输出pdf路径
//            //com.aspose.slides.Presentation pres = new  com.aspose.slides.Presentation(Address);//输入pdf路径
//          	String filePath1 =  request.getSession().getServletContext().getRealPath(File.separator  + filePath);
//          	String filePath2 = filePath.substring(0, filePath.lastIndexOf("."));
//          	String pdfPathString  = filePath2 + ".pdf";
//          	filePath2 = request.getSession().getServletContext()
//          			.getRealPath(File.separator  + filePath2 + ".pdf"); // 输出pdf文件路径
//
//          	 //文件操作
//			File file = new File(filePath2); // 新建一个空白pdf文档
//			com.aspose.slides.Presentation pres = new  com.aspose.slides.Presentation(filePath1);//输入pdf路径
//
//            FileOutputStream fileOS = new FileOutputStream(file);
//            pres.save(fileOS, com.aspose.slides.SaveFormat.Pdf);
//            fileOS.close();
//
//            long now = System.currentTimeMillis();
//            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + file.getPath()); //转化过程耗时
//            return pdfPathString;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "PDF格式转化失败";
//    }
//
//  /*
//   * 因为TXT 可以直接用上面的  DOC 方法 转    暂时 不用这个
//   * public static void textOfpdf(String filePath,HttpServletRequest request) throws DocumentException, IOException {
//
//    	String text =  request.getSession().getServletContext().getRealPath("\\" + filePath);
//      	String pdf = filePath.substring(0, filePath.lastIndexOf("."));
//
//    	BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//    	Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
//    	FileOutputStream out = new FileOutputStream(pdf);
//    	Rectangle rect = new Rectangle(PageSize.A4.rotate());
//    	com.itextpdf.text.Document doc = new com.itextpdf.text.Document(rect);
//    	PdfWriter writer = PdfWriter.getInstance(doc, out);
//    	doc.open();
//    	Paragraph p = new Paragraph();
//    	p.setFont(FontChinese);
//    	BufferedReader read = new BufferedReader(new FileReader(text));
//    	String line = read.readLine();
//    	while(line != null){
//    	System.out.println(line);
//    	p.add(line+"\n");
//    	line = read.readLine();
//    	}
//    	read.close();
//    	doc.add(p);
//    	doc.close();
//
//    }*/
//}