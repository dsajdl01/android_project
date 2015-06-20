package org.example.suggest;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;
import android.util.Xml;

public class SuggestTask implements Runnable {

	private static final String TAG = "SuggestTask";
	private final Suggest suggest;
	private final String original;
	
	SuggestTask(Suggest context, String original){
		this.suggest = context;
		this.original = original;
	}
	
	@Override
	public void run(){
		// Get suggestion for the original text
		List<String> suggestions = doSuggest(original);
		suggest.setSuggestions(suggestions);
	}
	
	private List<String> doSuggest(String original){
		List<String> message = new LinkedList<String>();
		String error = null;
		HttpURLConnection con = null;
		Log.d(TAG, "doSuggest(" + original + ")");
		
		try{
			//Check if has been interrupted
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
			// Build Restful Query for google API
			String q = URLEncoder.encode(original, "UTF-8");
			URL url = new URL("http://google.com/complete/search?output=toolbar&q="+ q);
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(1000);
			con.setRequestMethod("GET");
			con.addRequestProperty("Referer", "http://www.pragprog.com/titles/eband3/hello-android");
			con.setDoInput(true);
			//Start the query
			con.connect();
			
			//Check if has been interrupted
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
			//Read results from the query
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(con.getInputStream(),null);
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				String name = parser.getName();
				if(eventType == XmlPullParser.START_TAG && name.equalsIgnoreCase("suggestion")){
					for( int i = 0; i < parser.getAttributeCount(); i++){
						if(parser.getAttributeName(i).equalsIgnoreCase("data")){
							message.add(parser.getAttributeValue(i));
						}
					}
				}
				eventType = parser.next();
			}
				
			//check if task has been interrupted
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
			
		}
		catch (IOException e) {
			Log.d(TAG, "IOExeption", e);
			error = suggest.getResources().getString(R.string.error) + " " + e.toString();
		}
		catch (XmlPullParserException e) {
			Log.d(TAG, "XmlPullParserException", e);
			error = suggest.getResources().getString(R.string.error) + " " + e.toString();
		}
		catch (InterruptedException e) {
			Log.d(TAG, "InterruptedExeption", e);
			error = suggest.getResources().getString(R.string.error) + " additional: " + e.toString();
		}
		finally {
			if( con != null){
				con.disconnect();
			}
		}
		//If there was an error, return the error by itself
		if(error != null){
			message.clear();
			message.add(error);
		}
		
		//Print something if we got nothing
		if(message.size() == 0){
			message.add(suggest.getResources().getString(R.string.no_results));
		}
		// all gone
		Log.d(TAG, " -> return " + message);
		return message;
	}
	
}
