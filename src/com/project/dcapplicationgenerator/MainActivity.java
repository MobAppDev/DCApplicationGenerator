/*
 * 
 * @author Shivani Nayar (shivani.nayar@asu.edu)
 * @Version December,2012
 * 
 */
package com.project.dcapplicationgenerator;
import org.xml.sax.InputSource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;

import com.project.DatabaseManager.DatabaseManager;
import com.project.UIManager.UICreateManager;
import com.project.XMLManager.XMLParser;

public class MainActivity extends Activity {
	private Button bCompute;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        try{
        	InputSource inputSrc = new InputSource(getResources().openRawResource(R.raw.xml_input_file));
        	setTitle(XMLParser.parseXML(inputSrc));
        	createLayout("Next");
        	
         }catch(Exception e){
        	e.printStackTrace();
        }
    }
	
	private void createLayout(String btnName){
    	LinearLayout layoutMain = (LinearLayout)findViewById(R.id.formWindow);
    	layoutMain = UICreateManager.setAvtivityFields(layoutMain, this,btnName);
    	bCompute = new Button(this);
    	bCompute.setText(btnName);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        bCompute.setLayoutParams(params);
        bCompute.setOnClickListener(buttonMethod());
        layoutMain.addView(bCompute);
    }
	
	private OnClickListener buttonMethod(){
		OnClickListener oc = new OnClickListener() {
			
			public void onClick(View v) {
				saveData();
			}
		};
		return oc;
	}
	
	private void saveData(){
		int idVal = 10;
		int a = 0,b = 0;
		if(bCompute.getText().equals("Next")){
			a = 0;
			b = 4;
		}else {
			a = 4;
			b = XMLParser.fieldsArray.size();
		}
		for(int i = a; i < b; i++){//XMLParser.fieldsArray.size(); i++){
			String getVal = "";
			if(XMLParser.fieldsArray.get(i).getFieldType().equals("text")){
				EditText et = (EditText)findViewById(idVal++);
				getVal = et.getText().toString();
			}
			else if(XMLParser.fieldsArray.get(i).getFieldType().equals("radioButton")){
				RadioGroup rg = (RadioGroup)findViewById(idVal++);
				RadioButton rb =(RadioButton)findViewById(rg.getCheckedRadioButtonId());
				getVal = rb.getText().toString();
			}
			else if(XMLParser.fieldsArray.get(i).getFieldType().equals("spinner")){
				Spinner sp = (Spinner)findViewById(idVal++);
				//RadioButton rb =(RadioButton)findViewById(rg.getCheckedRadioButtonId());
				getVal = sp.getItemAtPosition(sp.getSelectedItemPosition()).toString();
			}
			else if(XMLParser.fieldsArray.get(i).getFieldType().equals("seekBar")){
				SeekBar sb = (SeekBar)findViewById(idVal++);
				//RadioButton rb =(RadioButton)findViewById(rg.getCheckedRadioButtonId());
				getVal = String.valueOf(sb.getProgress());
			}
			XMLParser.fieldsArray.get(i).setData(getVal);
			System.out.println("data is: " +getVal);
		}
		
		if(bCompute.getText().equals("Next")){
			createLayout("Save");
		}else 
			DatabaseManager.addRowData();
	}
	

    @Override
	protected void onPause() {
		super.onPause();
		DatabaseManager.stopThread();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DatabaseManager.startThread();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		DatabaseManager.stopThread();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//createMenu(menu);
       // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return selectedbutton(item);
	}
	
	@SuppressLint("NewApi")
	private void createMenu(Menu menu) {
		MenuItem mnu1 = menu.add(0, 0, 0, "View Users");
		{
			mnu1.setIcon(R.drawable.viewuser_icon);
			mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
	}

	private boolean selectedbutton(MenuItem item) {
		DatabaseManager.getData();
		startActivity(new Intent("com.project.ListViewActivity"));
		
		return true;
	}
}