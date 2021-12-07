package drawTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class DrawTest {
	public static void main(String[] args) {
		DrawTest dt=new DrawTest();
		try {
			dt.drawer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void drawer() throws Exception {
		URL url=new URL("https://api.cdn.visitjeju.net/photomng/imgpath/201804/30/982d17ac-791d-443d-b9a4-12d4cf81f7c9.jpg");
		BufferedImage img1=ImageIO.read(url);
		
		List<String> text=new ArrayList<>();
		text.add("온도:15.6 습도:60.0 날씨:맑거나 구름 많음 풍속:2.6");
		String fileName="테스트.png";
		String fontFamily="나눔고딕";
		int fontSize=55;
		
		if(text ==null || fileName == null || fontFamily == null ) {
			System.out.println("fail");
			
			return;
		}
		
		Graphics2D graphics=null;
		BufferedImage img=null;
		Font font=null;
		FileOutputStream fos=null;
		
		int width = new DrawTest().getMaxLengthTextLine(text)*(fontSize/2);
		int height=(text.size()+3)*fontSize;
		System.out.println(width);
		System.out.println(height);
		
		font=new Font(fontFamily, Font.BOLD, fontSize);
		img =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		fos=new FileOutputStream(new File(fileName));
		graphics=img.createGraphics();
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.lightGray);
		graphics.fillRect(0, 0, width, height);
		
		graphics.setFont(font);
		graphics.setColor(Color.black);
		
		for(int i=0; i<text.size(); i++) {
			String contents=text.get(i);
			int horizonStart=10;
			int vericalStart=(i+2)*fontSize;
			
			graphics.drawString(contents, horizonStart, vericalStart);
			
			ImageIO.write(img, "png", fos);
			System.out.println(fileName+" success");
			
			
			BufferedImage img2=ImageIO.read(new File("./"+fileName));
			
			Image resize=img2.getScaledInstance(img1.getWidth(), height, BufferedImage.TYPE_INT_RGB);
			
			width=Math.max(img1.getWidth(), img2.getWidth());
			height=img1.getHeight()+img2.getHeight();
			System.out.println(img1.getWidth());
			System.out.println(img1.getHeight());
			BufferedImage mergedImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			graphics=(Graphics2D)mergedImage.getGraphics();
			graphics.setBackground(Color.white);
			graphics.drawString(text.get(i), horizonStart, vericalStart);
			graphics.drawImage(img1, 0, 0, null);
			graphics.drawImage(resize, 0, img1.getHeight(), null);
			fileName="테스트_합병.png";
			fos=new FileOutputStream(new File(fileName));
			ImageIO.write(mergedImage, "png", fos);
			System.out.println("complete");
					
			
		}
		
		
	}

	private int getMaxLengthTextLine(List<String> text) {
		int maxSize=0;
		for(int i=0; i<text.size(); i++) {
			int size=0;
			for(int j=0; j<text.get(i).length(); j++) {
				if(Character.getType(text.get(i).charAt(j))==Character.OTHER_LETTER) {
					size=size+2;
				}
				else {
					size=size+1;
				}
				if(maxSize<size) maxSize=size;
				
				
			}
		}
		System.out.println(maxSize);
		return maxSize;
	}
	
}

