package org.example.preferences;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SetPreference extends PreferenceFragment {


	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.setting);
	//        getListView().setBackgroundColor(Color.rgb(4, 26, 55);
	        
	        Preference button = (Preference)getPreferenceManager().findPreference("button");      
	        if (button != null) {
	            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	                @Override
	                public boolean onPreferenceClick(Preference arg0) {
	                	Intent intent = new Intent(getActivity(), MainActivity.class);
	                	startActivity(intent);
	                    return false;
	                }
	            });     
	       }
	}
}


