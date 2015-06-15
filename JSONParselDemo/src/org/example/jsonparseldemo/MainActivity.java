package org.example.jsonparseldemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
	private EditText etLocation;
	private TextView tvOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etLocation = (EditText) findViewById(R.id.etLocation);
		tvOutput = (TextView) findViewById(R.id.tvOutput);
	}
	
	public void getWeather(View v){
		String location = etLocation.getText().toString();
		String urlStr = baseUrl + location;
		
		new JSONParserTask().execute(urlStr);
	}

	private String[] parseJSON(String in){
		String[] dataArr = {null, null, null};
		
		try{
			JSONObject reader = new JSONObject(in);
			
			JSONObject main = reader.getJSONObject("main");
			dataArr[0] = main.getString("temp");
			dataArr[1] = main.getString("pressure");
			dataArr[2] = main.getString("humidity");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataArr;
	}
	
	private String fetchWeatherData(String urlStr){
		String weatherData = null;
		
		try{
			URL url = new URL(urlStr);
			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.connect();
			
			InputStream stream = conn.getInputStream();
			String data = convertStreamToString(stream);
			
			String[] dataArr = parseJSON(data);
			
			if(dataArr[0] != null || dataArr[1] != null || dataArr[2] != null){
			weatherData = "Temperature: " + dataArr[0] 
					+ "\nHumidity: " + dataArr[2] 
					+ "\nPressure: " + dataArr[1];
			}
		
		}catch (MalformedURLException ex){
			ex.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return weatherData;
	} 
	static String convertStreamToString(java.io.InputStream is){
		@SuppressWarnings("resource")
		java.util.Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next():"";
	}
	
	private class JSONParserTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String weatherData = fetchWeatherData(params[0]);
			
			return weatherData;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if(result != null){
				tvOutput.setText(result);
			}
			else{
				tvOutput.setText("Cannot fetch weather data! Sorry." + result);
			}
		}
		
	}
	
	
	
}
