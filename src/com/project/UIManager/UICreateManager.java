/*
 * @author: Shivani Nayar <shivani.nayar@asu.edu>
 * @date: Dec'12
 * 
 */

package com.project.UIManager;


import java.util.ArrayList;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.DatabaseManager.FieldInfo;
import com.project.XMLManager.XMLParser;
import com.project.dcapplicationgenerator.R;

public class UICreateManager {
	public static Hashtable<String,EditText> editTxtFields = new Hashtable<String, EditText>();
	public static ArrayList<LinearLayout>viewArray = new ArrayList<LinearLayout>();
	public UICreateManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static LinearLayout setAvtivityFields(LinearLayout layoutMain,Context context,String btnType){
		int idVal = 10;
		int a = 0,b = 0;
		if(btnType.equals("Next")){
			a = 0;
			b = 4;
		}else {
			viewArray.add(layoutMain);
			layoutMain.removeAllViews();// = new LinearLayout(context);
			a = 4;
			b = XMLParser.fieldsArray.size();
		}
		for(int i = a; i < b; i++){
			FieldInfo fieldInfo = new FieldInfo();
  		    fieldInfo = XMLParser.fieldsArray.get(i);
  		    if(fieldInfo.getFieldType().equals("text"))
  		    	layoutMain.addView(createField(context, context.getResources(), fieldInfo.getFieldName(),fieldInfo.getType(),fieldInfo.getRequired(),fieldInfo.getInputType(),idVal++));
  		    else if(fieldInfo.getFieldType().equals("radioButton"))
  		        layoutMain.addView(createRadioButton(context, fieldInfo.getValues(),fieldInfo.getFieldName(),idVal++));
  		    else if(fieldInfo.getFieldType().equals("spinner"))
  		        layoutMain.addView(createSpinner(context,context.getResources(),fieldInfo.getFieldName(), fieldInfo.getValues(), idVal++)); 
  		  else if(fieldInfo.getFieldType().equals("seekBar"))
		        layoutMain.addView(createSeekBar(context,fieldInfo.getFieldName(), fieldInfo.getValues(), idVal++)); 
		}
		
		return layoutMain;
	}
	
	/**
	 * Method to create fields with labels as hints.
	 */
	@SuppressLint("NewApi")//for .setBackground(bg) 
	public static EditText createField(Context context,Resources res, String fieldName, String fieldType, Boolean fieldRequired,String inputType,int idVal)
	{
		EditText txtField = new EditText(context);//,null,R.style.etextField);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,50); 
		params.setMargins(1, 1, 1, 5);
		txtField.setId(idVal);
    	txtField.setHint(fieldName);
    	txtField.setHintTextColor(Color.parseColor("#000000"));
    	txtField.setLayoutParams(params);
    	txtField.setHeight(50);
    	txtField.setPadding(5, 5, 5, 10);
    	if(inputType.equals("name"))
    		txtField.setRawInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    	else if(inputType.equals("birthDate"))
    		txtField.setRawInputType(InputType.TYPE_CLASS_DATETIME);
    	else if(inputType.equals("password")){
    		txtField.setTransformationMethod(PasswordTransformationMethod.getInstance());
    		txtField.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    	}else if(inputType.equals("email"))
    		txtField.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
    	else if(inputType.equals("text"))
    		txtField.setRawInputType(InputType.TYPE_CLASS_TEXT);
    	
    	Drawable bg = res.getDrawable(R.drawable.edittext_style);
    	txtField.setBackground(bg);
    	editTxtFields.put(fieldName, txtField);
    	return txtField;
    }
	
	private static LinearLayout createRadioButton(Context context,ArrayList<String> rOptions,String fieldName,int idVal) {
	    LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView labelField = new TextView(context);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	params.setMargins(5, 2, 5, 2);
    	labelField.setText(fieldName);
    	labelField.setTextColor(Color.parseColor("#000000"));
    	labelField.setLayoutParams(params);
    	layout.setPadding(5, 5, 5, 5);
    	layout.addView(labelField);
    	RadioGroup rg = new RadioGroup(context); //create the RadioGroup
	    params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT); 
		rg.setLayoutParams(params);
	    rg.setId(idVal);
	    rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
	    rg.setGravity(Gravity.CENTER_HORIZONTAL);
	    
	    for(int i=0; i<rOptions.size(); i++){
	    	RadioButton rb  = new RadioButton(context);
	        rb.setText(rOptions.get(i).toString());
	        rg.addView(rb); //the RadioButtons are added to the radioGroup instead of the layout
	    }
	    layout.addView(rg);
	    return layout;
	}

	@SuppressLint("NewApi")
	public static LinearLayout createSpinner(Context context,Resources res,String fieldName,ArrayList<String> sOptions,int idVal){
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		TextView labelField = new TextView(context);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    	params.setMargins(5, 2, 5, 2);
    	labelField.setText(fieldName);
    	labelField.setTextColor(Color.parseColor("#000000"));
    	labelField.setLayoutParams(params);
    	labelField.setGravity(Gravity.LEFT);
    	layout.setPadding(5, 5, 5, 5);
    	layout.addView(labelField);
    	Spinner spinnerField = new Spinner(context);
    	spinnerField.setLayoutParams(new LayoutParams(300,LayoutParams.WRAP_CONTENT));
    	ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, sOptions);
    	spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
    	spinnerField.setAdapter(spinnerArrayAdapter);
    	spinnerField.setId(idVal);
    	Drawable bg = res.getDrawable(R.drawable.edittext_style);
    	spinnerField.setBackground(bg);
    	spinnerField.setGravity(Gravity.CENTER);
    	layout.addView(spinnerField); 
    	
		return layout;
	}
	
	
	@SuppressLint("NewApi")
	public static LinearLayout createSeekBar(Context context,String fieldName,ArrayList<String> sOptions,int idVal){
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView labelField = new TextView(context);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    	params.setMargins(5, 2, 5, 2);
    	labelField.setText(fieldName);
    	labelField.setTextColor(Color.parseColor("#000000"));
    	labelField.setLayoutParams(params); 
    	layout.setPadding(5, 5, 5, 5);
    	layout.addView(labelField);
    	SeekBar sb = new SeekBar(context);
    	sb.setMax(Integer.parseInt(sOptions.get(1)));
    	sb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	sb.setId(idVal);
    	layout.addView(sb); 
    	
		return layout;
	}
	
	/**
	 * Method to create fields with labels
	 */
	public static LinearLayout createFields(Context context, String fieldName,int position, CharSequence hintVal)
	{
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		TextView labelField = new TextView(context);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
    	labelField.setId(position);
    	labelField.setText(fieldName);
    	labelField.setTextColor(Color.parseColor("#000000"));
    	labelField.setLayoutParams(params); 
    	layout.addView(labelField);
    	EditText txtField = new EditText(context);
    	params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
    	txtField.setId(position);
    	txtField.setTextSize(12);
    	txtField.setHintTextColor(Color.parseColor("#ededed"));
    	txtField.setHint(hintVal);
    	txtField.setTextColor(Color.parseColor("#ededed"));
    	txtField.setLayoutParams(params);
    	txtField.setRawInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
    	layout.addView(txtField);
    	
    	return layout;
    }
}