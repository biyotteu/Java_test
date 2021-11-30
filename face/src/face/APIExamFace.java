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
        String clientId = "DFa9mTfyDW1lAd6pNwlo";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "FwdSMa4DzB";//애플리케이션 클라이언트 시크릿값";
        String inFolder = "C:\\Users\\tjqtj\\Desktop\\photos";
        //이동후의 폴더
        String outFolder = "C:\\Users\\tjqtj\\Desktop\\";
        List<File> dirList = getDirFileList(inFolder);

        for(int idx=0;idx<dirList.size();idx++) {
	        try {
	            String paramName = "image"; // 파라미터명은 image로 지정
	            String cmp = "C:\\Users\\tjqtj\\Desktop\\";
	            String name = dirList.get(idx).getName();
	            String imgFile = "C:\\Users\\tjqtj\\Desktop\\photos\\"+name
	            		+ "";
	            File uploadFile = new File(imgFile);
	            //String apiURL = "https://openapi.naver.com/v1/vision/celebrity"; // 유명인 얼굴 인식
	            String apiURL = "https://openapi.naver.com/v1/vision/face"; // 얼굴 감지
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
	            // file 추가
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
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
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
    
    //파일 입출을 하기위한 함수들
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
    	   
    	   //복사한뒤 원본파일을 삭제함
    	   fileDelete(inFileName);
    	   
    	  } catch (IOException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  }
    	 }

    public static List<File> getDirFileList(String dirPath)
    {
     // 디렉토리 파일 리스트
     List<File> dirFileList = null;
     
     // 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
     File dir = new File(dirPath);
     
     // 디렉토리가 존재한다면
     if (dir.exists())
     {
      // 파일 목록을 구함
      File[] files = dir.listFiles();
      
      // 파일 배열을 파일 리스트로 변화함 
      dirFileList = Arrays.asList(files);
     }
     
     return dirFileList;
    }
    public static void fileDelete(String deleteFileName) {
    	  File I = new File(deleteFileName);
    	  I.delete();
    	 }
    
}
