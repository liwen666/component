package com.temp.common.pdf2word.test1;


import java.io.File;

public class Main {
	public static void main(String[] args) {
		
		String res = new PdfToWord().pdftoword("D:\\文档\\技术文档\\阿里巴巴Java开发手册终极版v1.3.0.pdf");
		System.out.println(res);
	}
	
}