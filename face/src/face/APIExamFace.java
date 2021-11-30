package face;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;



public class APIExamFace {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        StringBuffer reqStr = new StringBuffer();
        String clientId = "DFa9mTfyDW1lAd6pNwlo";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
        String clientSecret = "FwdSMa4DzB";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
        String inFolder = "C:\\Users\\tjqtj\\Desktop\\photos";
        //�̵����� ����
        String outFolder = "C:\\Users\\tjqtj\\Desktop\\";
        List<File> dirList = getDirFileList(inFolder);

        for(int idx=0;idx<dirList.size();idx++) {
	        try {
	            String paramName = "image"; // �Ķ���͸��� image�� ����
	            String cmp = "C:\\Users\\tjqtj\\Desktop\\";
	            String name = dirList.get(idx).getName();
	            String imgFile = "C:\\Users\\tjqtj\\Desktop\\photos\\"+name
	            		+ "";
	            File uploadFile = new File(imgFile);
	            //String apiURL = "https://openapi.naver.com/v1/vision/celebrity"; // ������ �� �ν�
	            String apiURL = "https://openapi.naver.com/v1/vision/face"; // �� ����
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setUseCaches(false);
	            con.setDoOutput(true);
	            con.setDoInput(true);
	            // multipart request
	            String boundary = "---" + System.currentTimeMillis() + "---";
	            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            OutputStream outputStream = con.getOutputStream();
	            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
	            String LINE_FEED = "\r\n";
	            // file �߰�
	            String fileName = uploadFile.getName();
	            writer.append("--" + boundary).append(LINE_FEED);
	            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
	            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
	            writer.append(LINE_FEED);
	            writer.flush();
	            FileInputStream inputStream = new FileInputStream(uploadFile);
	            byte[] buffer = new byte[4096];
	            int bytesRead = -1;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            outputStream.flush();
	            inputStream.close();
	            writer.append(LINE_FEED).flush();
	            writer.append("--" + boundary + "--").append(LINE_FEED);
	            writer.close();
	            BufferedReader br = null;
	            int responseCode = con.getResponseCode();
	            if(responseCode==200) { // ���� ȣ��
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // ���� �߻�
	                System.out.println("error!!!!!!! responseCode= " + responseCode);
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            }
	            String inputLine;
	            if(br != null) {
	                StringBuffer response = new StringBuffer();
	                while ((inputLine = br.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                br.close();
	                System.out.println(response.toString());
	                JSONParser parser = new JSONParser();
	                Object obj = parser.parse( response.toString() );
	                JSONObject json = (JSONObject) obj;
	                JSONArray  jA = (JSONArray) json.get("faces");
	                json = (JSONObject) jA.get(0);
	                json = (JSONObject) json.get("emotion");
	                String em = json.get("value").toString();
	                fileMove(inFolder+"\\"+fileName, outFolder+em+"\\"+fileName);
	            } else {
	                System.out.println("error !!!");
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
        }
    }
    
    //���� ������ �ϱ����� �Լ���
    public static void fileMove(String inFileName, String outFileName) {
    	  try {
    	   FileInputStream fis = new FileInputStream(inFileName);
    	   FileOutputStream fos = new FileOutputStream(outFileName);
    	   
    	   int data = 0;
    	   while((data=fis.read())!=-1) {
    	    fos.write(data);
    	   }
    	   fis.close();
    	   fos.close();
    	   
    	   //�����ѵ� ���������� ������
    	   fileDelete(inFileName);
    	   
    	  } catch (IOException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  }
    	 }

    public static List<File> getDirFileList(String dirPath)
    {
     // ���丮 ���� ����Ʈ
     List<File> dirFileList = null;
     
     // ���� ����� ��û�� ���丮�� ������ ���� ��ü�� ������
     File dir = new File(dirPath);
     
     // ���丮�� �����Ѵٸ�
     if (dir.exists())
     {
      // ���� ����� ����
      File[] files = dir.listFiles();
      
      // ���� �迭�� ���� ����Ʈ�� ��ȭ�� 
      dirFileList = Arrays.asList(files);
     }
     
     return dirFileList;
    }
    public static void fileDelete(String deleteFileName) {
    	  File I = new File(deleteFileName);
    	  I.delete();
    	 }
    
}
