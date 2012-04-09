package mjava.me.fb;


import java.net.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Seid Muhie Yimam
 * @Date April 08, 2012
 *
 */

public class Extract {
	public static void main(String args[]) throws IOException, JSONException {
		
		System.out.print("Enter your access token, you can get it from this site \n" +
				"https://developers.facebook.com/tools/explorer/:");
	    Scanner scanner = new Scanner(System.in);
	    String accesstoken = scanner.nextLine();
	    System.out.println();
	    System.out.println();
	    System.out.println();
		System.out.print("Enter the FaceBook group ID. Copy and paste the group web address in the following site to know its ID \n" +
				"http://wallflux.com/facebook_id/");
	    String groupid = scanner.nextLine();
	    System.out.println();
	    System.out.println();
	    System.out.println();
		System.out.print("Type the name of the output file you like it to be. It will be saved in the directory you are running this program");
	    String filename = scanner.nextLine();
	    
	    System.out.println("Please wait....");
		URL url = null;
		URLConnection urlConn = null;
		InputStreamReader inStream = null;
		StringBuffer sb = new StringBuffer();
		//FileWriter fstream = new FileWriter("FEZEKIR_CENTER_JSON.txt",true);
		File file = new File(filename.toString());
		 BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF8"));
		try {
			// Create the URL obect that points
			
			String myFBGroupSite = "https://graph.facebook.com/"+groupid.toString()+"/feed?access_token="+accesstoken.toString() ;
			
			boolean more = true;
			while (more) {
				InputStream is = new URL(myFBGroupSite).openStream();
				JSONObject json;
				try {
					BufferedReader rd = new BufferedReader(
							new InputStreamReader(is, Charset.forName("UTF-8")));
					String jsonText = readAll(rd);
					json = new JSONObject(jsonText);
				} finally {
					is.close();
				}
				Set nextPage = new HashSet();
				// Read and print the lines from index.html
				
/*				JSONObject myjson = new JSONObject(json);
				JSONObject topLevelArray = myjson.optJSONObject("data");
				
				for (int i = 0; i<topLevelArray.length(); i++){
					
				//	JSONObject comments = new JSONObject(topLevelArray.getJSONObject(2));
				//	JSONArray comments = myjson.getJSONArray("data");
					//JSONArray from = myjson.getJSONArray("data"); 
					JSONObject from = topLevelArray.getJSONObject("from");
					String author = from.getString("name");
					
					JSONObject comments = topLevelArray.getJSONObject("comments");
					String author = from.getString("name");
					
					String created_time = topLevelArray.getString("created_time");
					String message = topLevelArray.getString("message");
					String created_time = topLevelArray.getString("created_time");
					String created_time = topLevelArray.getString("created_time");
					
				}*/
				
				
				String nextLine = json.toString();
				String next = "";
				out.write(nextLine + "\n");
				if (nextLine.contains("\"next\":\"https:")) {
					next = nextLine.substring(
							nextLine.indexOf("\"next\":\"https:") + 8,
							nextLine.length() - 3).replace("\\", "");
					myFBGroupSite = next;
					nextPage.add(next);
				} else {
					more = false;
				}
			}
			out.close();
		} catch (MalformedURLException e) {
			System.out.println("Please check the URL:" + e.toString());
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Can't read  from the Internet: "
					+ e1.toString());
			System.exit(0);
		}
		System.out.println("Finished Working.... File successfully extracted");
	}

	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
}