/*
 * @author: Shivani Nayar <shivani.nayar@asu.edu>
 * @date: Dec'12
 * 
 */

package com.project.dcapplicationgenerator;

import com.project.DatabaseManager.DatabaseManager;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ListViewActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.id.list,DatabaseManager.list_values);
            setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_view, menu);
        return true;
    }
}
