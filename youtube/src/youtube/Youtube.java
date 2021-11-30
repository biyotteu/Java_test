package youtube;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

public class Youtube {
	public static void main(String[] args) {
		javafx.scene.image.ImageView imageview = null;
		Image image = null;
	        try {
	        	Scanner scan = new Scanner(System.in);
	        	String uri = new String();
	        	uri = scan.nextLine();
	    	    String[] videoID = uri.split("v=");
	            URL url = new URL("http://img.youtube.com/vi/" + videoID[1] +"/0.jpg");
	            System.out.println(url);
	            BufferedImage img = ImageIO.read(url);
	            System.out.println(img.toString());
	            File file=new File("C:\\Users\\tjqtj\\abcds.jpg");
	            ImageIO.write(img, "jpg", file);
	            javafx.scene.image.Image card = SwingFXUtils.toFXImage(img, null );
	            imageview.setImage(card);
	        } catch (IOException e) {
	         e.printStackTrace();
	        }
	}
}
