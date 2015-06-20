package org.example.suggest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class Suggest extends Activity {

	private EditText origTxt;
	private ListView suggLst;
	private Handler guiThread;
	private ExecutorService suggThread;
	private Runnable updateTask;
	private Future<?> suggPending;
	private List<String> items;
	private ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initThreading();
        findView();
        setListeners();
        setAdapters();
    }
    
    @Override
    protected void onDestroy() {
       // Terminate extra threads here
       suggThread.shutdownNow();
       super.onDestroy();
    }
    
    // get a handle to all user interface elements
    private void findView(){
    	origTxt = (EditText) findViewById(R.id.original_text);
    	suggLst = (ListView) findViewById(R.id.result_list);
    }
    
    //set up adapter for list view
    private void setAdapters(){
    	items = new ArrayList<String>();
    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    	suggLst.setAdapter(adapter); 
    	
    }
    
    private void setListeners(){
    	//define listener for next change
    	TextWatcher textWatcher = new TextWatcher() {
    		public void beforeTextChanged(CharSequence s, int start, int count, int after){
    			/** do something */
    		}
    		public void onTextChanged(CharSequence s, int start, int before, int count){
    			queueUpdate(1000); /* millisecond*/
    		}
    		public void afterTextChanged(Editable s){
    			/** do something */
    		}
    	};
    	
    	//set listener on the original text field
    	origTxt.addTextChangedListener(textWatcher);
    	
    	//Define listener for clicking on an item
    	OnItemClickListener clickListener = new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v, int position, long id){
    			String query = (String) parent.getItemAtPosition(position);
    			doSearch(query);
    		}
    	};
    	
    	// Set listener on the suggestion list
        suggLst.setOnItemClickListener(clickListener);
    }
    
    /** Fire off an intent to do a web search */
    private void doSearch(String query){
    	Intent i = new Intent(Intent.ACTION_WEB_SEARCH);
    	i.putExtra(SearchManager.QUERY, query);
    	startActivity(i);
    }
    
    private void initThreading(){
    	guiThread = new Handler();
    	suggThread = Executors.newSingleThreadExecutor();
    	
    	//This task gets suggestions and update the screen.
    	updateTask = new Runnable(){
    		public void run(){
    		
    			//Get text to suggest
    			String original = origTxt.getText().toString().trim();
    			
    			if(suggPending != null){
    				suggPending.cancel(true);
    			}
    			
    			if(original.length() != 0){
    				//Let user know we are doing something
    				setText(R.string.working);
	    			
	    			//begin suggesting now but don't wait for it
	    			try {
	    				SuggestTask suggestTask = new SuggestTask(Suggest.this,original);
	                      suggPending = suggThread.submit(suggestTask);
	    			}
	    			catch (RejectedExecutionException e){
	    				setText(R.string.error);
	    			}
    			}
	    	}
    	};
    }
    
    /** Request an update to start after a short delay*/
    private void queueUpdate(long delayMillis){
    	// cancel previous update if it hasn't started yet
    	guiThread.removeCallbacks(updateTask);
    	
    	//Start an update if nothing happened after a few milliseconds
    	guiThread.postDelayed(updateTask, delayMillis);
    }
    
    /** Display message */
    private void setText(int id){
    	adapter.clear();
    	adapter.add(getResources().getString(id));
    }
    
    /** Display list*/
    private void setList(List<String> list){
    	adapter.clear();
    	for(String i : list){
    		adapter.add(i);
    	}
    	
    }
    
    /**Modify list on the screen (called from another thread*/
    public void setSuggestions(List<String> s){
    	guiSetList(suggLst, s);
    }
    
    /**All changes to the GUI must be done in the GUI thread */
    private void guiSetList(final ListView v, final List<String> l){
    	guiThread.post(new Runnable(){
    		public void run(){
    			setList(l);
    		}
    	});
    }
}
