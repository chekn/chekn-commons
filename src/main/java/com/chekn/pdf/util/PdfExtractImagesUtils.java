package com.chekn.pdf.util;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
 
/**
 * Extracts images from a PDF file.
 */
public class PdfExtractImagesUtils {
 
    /** The new document to which we've added a border rectangle. */
    public static final String RESULT = "%s.%s";
 
    private static Logger logger = LoggerFactory.getLogger(PdfExtractImagesUtils.class);
    
    static class WithIndexRenderListener implements RenderListener {
    	
    	private String outdir;
    	private int order=0;
    	
    	public WithIndexRenderListener(String outdir) {
    		this.outdir=outdir;
    	}

		@Override
		public void beginTextBlock() {}

		@Override
		public void renderText(TextRenderInfo renderInfo) {}

		@Override
		public void endTextBlock() {}

		@Override
		public void renderImage(ImageRenderInfo renderInfo) {
			try {
	            String filename;
	            FileOutputStream os;
	            PdfImageObject image = renderInfo.getImage();
	            if (image == null) return;
	            filename = String.format(RESULT,  ++order, image.getFileType());
	            os = new FileOutputStream(outdir+ File.separator +filename);
	            os.write(image.getImageAsBytes());
	            os.flush();
	            os.close();
	        } catch (IOException e) {
	            logger.info("deal occur exception, exception:{}", e.getMessage());
	            throw new RuntimeException(String.format("extract image occur exception, page-no:%s, image-no:%s", order, renderInfo.getRef().getNumber()));
	        }
		}
    	
    };
    
    /**
     * Parses a PDF and extracts all the images.
     * @param src the source PDF
     * @param dest the resulting PDF
     */
    public static void extractImages(String filename, final String outdir)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(filename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
    	WithIndexRenderListener withIndexRenderListener =new WithIndexRenderListener(outdir);
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        	logger.info("current deal No.{} page picture...", i);
            parser.processContent(i,  withIndexRenderListener);
        }
        reader.close();
    }
 
	
	public static void pageCompoundImagesByIcepdf(String pdfPath, String outDir) throws Exception {
		Document document = new Document();  
		
        document.setFile(pdfPath);
        //determine the scale of the image   
        //and the direction of the image   
          
        float scale = 1f;  
        float rotation = 0f;  
  
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage)
            
            document.getPageImage(i,GraphicsRenderingHints.SCREEN,Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;

            File file = new File(outDir + File.separator + i + ".jpg");  
            ImageIO.write(rendImage, "JPEG", file); 

            image.flush();  
        }  
  
        document.dispose();
	}
    
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        new PdfExtractImagesUtils().extractImages("c:/00316427_1.pdf","c:/imgs");
        new PdfExtractImagesUtils().pageCompoundImagesByIcepdf("c:/00316427_1.pdf","c:/imgs-2");
    }
}