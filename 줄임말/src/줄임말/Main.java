package ???Ӹ?;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		try {
			String fileName = "C:\\Users\\tjqtj\\Desktop" ;
			String out = "\\res.txt";
			String in = "\\infile.txt";
			File file = new File(fileName+out) ;
			File infile = new File(fileName+in);
			FileWriter fw = new FileWriter(file, false) ;
			
			BufferedReader br = null;
	        try {
	        	FileInputStream fileInputStream = new FileInputStream(fileName+in);
	        	InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
	        	br = new BufferedReader(inputStreamReader);

	            String str;
	            br.readLine();
	            while ((str = br.readLine()) != null) {
	            	String[] arr = str.split(" ");
					for(int j=0;j<arr.length;j++) {
						fw.write(arr[j].charAt(0));
			            fw.flush();
					}
					fw.write("\n");
		            fw.flush();
	            }
	            fw.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            if(br != null) try {br.close(); } catch (IOException e) {}
	        }
		}catch(Exception e){
            e.printStackTrace();
        }
	         
	}
}
