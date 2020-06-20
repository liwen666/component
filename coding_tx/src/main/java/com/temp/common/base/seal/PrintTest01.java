package com.temp.common.base.seal;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintTest01 {

  public static void main(String[] args) {

    PrinterResolution pr = new PrinterResolution(300, 300, PrinterResolution.DPI);

    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
    aset.add(MediaSizeName.ISO_A4);
    aset.add(pr);
    aset.add(OrientationRequested.PORTRAIT);

    PrinterJob pj = PrinterJob.getPrinterJob();
    pj.setPrintable(new Page());
    try {
      pj.print(aset);
    } catch (PrinterException ex) {
      ex.printStackTrace();
    }

  }

  public static class Page implements Printable {

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
      if (pageIndex > 0) {
        return NO_SUCH_PAGE;
      }

      Graphics2D g2d = (Graphics2D) g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

      g.setFont(new Font("Arial", Font.PLAIN, 128));
      FontMetrics fm = g.getFontMetrics();
      int x = (int)(pageFormat.getWidth() - fm.stringWidth("A")) / 2;
      int y = (int)((pageFormat.getHeight() - fm.getHeight()) / 2) + fm.getAscent();

      g2d.drawString("A", x, y);

      return PAGE_EXISTS;
    }
  }
}