
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.text.html.ImageView;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class youtube extends Application{
	static public Image image;
	public static void main(String[] args) {
	        try {
	        	Scanner scan = new Scanner(System.in);
	        	String uri = new String();
	        	uri = scan.nextLine();
	    	    String[] videoID = uri.split("v=");
	            URL url = new URL("http://img.youtube.com/vi/" + videoID[1] +"/0.jpg");
	            System.out.println(url);
	            BufferedImage img = ImageIO.read(url);
	            System.out.println(img.toString());
	            File file=new File("C:\\Users\\tjqtj\\Desktop\\abcds.jpg");
	            ImageIO.write(img, "jpg", file);
	            image = SwingFXUtils.toFXImage(img, null );
	            launch(args);
	            //down("https://youtu.be/MOo9iJ8RYWM");

		        Youtubemp3 you = new Youtubemp3();
		        String[] str = you.getLink(uri);
		        System.out.println(str[0]);
		        System.out.println(str[1]);
		    } catch (IOException e) {
	         e.printStackTrace();
	        }
	}

	@Override
	public void start(Stage stage) throws Exception {
		  //Setting the image view 
		  javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image); 
	      
	      //Setting the position of the image 
	      imageView.setX(50); 
	      imageView.setY(25); 
	      
	      //setting the fit height and width of the image view 
	      imageView.setFitHeight(455); 
	      imageView.setFitWidth(500); 
	      
	      //Setting the preserve ratio of the image view 
	      imageView.setPreserveRatio(true);  
	      
	      //Creating a Group object  
	      Group root = new Group(imageView);  
	      
	      //Creating a scene object 
	      Scene scene = new Scene(root, 600, 500);  
	      
	      //Setting title to the Stage 
	      stage.setTitle("Loading an image");  
	      
	      //Adding scene to the stage 
	      stage.setScene(scene);
	      
	      //Displaying the contents of the stage 
	      stage.show(); 
	}
	static public void down(String fileURL) throws IOException {
		 URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "music";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	            
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            
	            String saveFilePath = "C:\\Users\\tjqtj\\Desktop" + File.separator + fileName + ".mp4";
	            System.out.println(saveFilePath);
	            //File directory = new File(saveFilePath);
	            //directory.mkdirs();
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	            int bytesRead = -1;
	            byte[] buffer = new byte[4096];
	            while(inputStream.available() != 0) {
	                inputStream.read(buffer);
	                outputStream.write(buffer);
	            }
	            /*while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }*/
	 
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded");
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	}
}
