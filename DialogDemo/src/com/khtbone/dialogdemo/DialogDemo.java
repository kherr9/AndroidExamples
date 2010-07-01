package com.khtbone.dialogdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

public class DialogDemo extends Activity implements Button.OnClickListener{
	
	private static final int DIALOG_ALERTBOX_1 = 0;
	private static final int DIALOG_ALERTBOX_2 = 1;
	private static final int DIALOG_LIST = 2;
	private static final int DIALOG_LIST_RADIO = 3;
	private static final int DIALOG_PROGRESS = 4;
	private static final int DIALOG_PROGRESS_BAR = 5;
	
	private static final CharSequence[] items = {"Red", "Green", "Blue"};
	
	private static TextView _txtOutput = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _txtOutput = (TextView) findViewById(R.id.txtOutput);
        
        ((Button) findViewById(R.id.btnAlertBox1)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertBox2)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnList)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnListRadio)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnProgress)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnProgressBar)).setOnClickListener(this);
    }
    
    @Override
    protected Dialog onCreateDialog(int id){
    	Dialog dialog = null;
    	switch(id){
    	case DIALOG_ALERTBOX_1:
    		dialog = createAlert1();
    		break;
    	case DIALOG_ALERTBOX_2:
    		dialog = createAlert2();
    		break;
    	case DIALOG_LIST:
    		dialog = createList();
    		break;
    	case DIALOG_LIST_RADIO:
    		dialog = createListRadio();
    		break;
    	case DIALOG_PROGRESS:
    		dialog = createProgress();
    		break;
    	case DIALOG_PROGRESS_BAR:
    		dialog = createProgressBar();
    		break;
    	}
    	
    	return dialog;
    }
    
    private Dialog createAlert1(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage("File uploaded");
    	builder.setCancelable(false);
    	builder.setOnKeyListener( new OnKeyListener(){

			public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
				_txtOutput.setText(String.valueOf(keyCode));
				dialog.dismiss();
				return false;
			}
    		
    	});
    	
    	return builder.create();
    }
    
    private Dialog createAlert2(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage("Are you sure?");
    	builder.setCancelable(false);
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int id){
    			_txtOutput.setText(String.valueOf(id));
    			dialog.cancel();
    		}
    	});
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int id){
    			_txtOutput.setText(String.valueOf(id));
    			dialog.cancel();
    		}
    	});
    	
		return builder.create();
    }

    private Dialog createList(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setTitle("Select Color");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				_txtOutput.setText(items[which]);
			}
		});
    	
    	return builder.create();
    }
    
    private Dialog createListRadio(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setTitle("Select Colors");
    	
    	//	multiple items
    	builder.setMultiChoiceItems(items, new boolean[]{false,false,false}, new DialogInterface.OnMultiChoiceClickListener(){

			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				
				_txtOutput.setText(items[which] + " is " + String.valueOf(isChecked));
			}
    		
    	});
    	
    	/*
    	//	single item
    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int item){
    			_txtOutput.setText(items[item]);
    			//dialog.dismiss();
    		}
    	});
    	*/
    	
    	return builder.create();
    }
    
    private Dialog createProgress(){
    	ProgressDialog dialog = new ProgressDialog(this);
    	
    	dialog.setMessage("...uploading");
    	
    	//	create thread to cancel dialog after X milliseconds
    	new Thread(new ProgressHandler(dialog)).start();
    	
    	return dialog;
    }

    private Dialog createProgressBar(){
    	ProgressDialog dialog = new ProgressDialog(this);
    	
    	dialog.setMessage("loading...");
    	dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    	
    	new Thread(new UpdateProgressBar(dialog)).start();
    	
    	return dialog;
    }
    
	public void onClick(View v) {
		
		if(v.equals(findViewById(R.id.btnAlertBox1))){
			showDialog(DIALOG_ALERTBOX_1);
		}else if(v.equals(findViewById(R.id.btnAlertBox2))){
			showDialog(DIALOG_ALERTBOX_2);
		}else if(v.equals(findViewById(R.id.btnList))){
			showDialog(DIALOG_LIST);
		}else if(v.equals(findViewById(R.id.btnListRadio))){
			showDialog(DIALOG_LIST_RADIO);
		}else if(v.equals(findViewById(R.id.btnProgress))){
			showDialog(DIALOG_PROGRESS);
		}else if(v.equals(findViewById(R.id.btnProgressBar))){
			showDialog(DIALOG_PROGRESS_BAR);
		}
		
	}
	
	
	class UpdateProgressBar implements Runnable{

		private final ProgressDialog _dialog;

		public UpdateProgressBar(ProgressDialog dialog){
			_dialog = dialog;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			for(int i = 0; i <= 100; i++){
				try{
					Thread.sleep(70);
					_dialog.setProgress(i);
				}catch(Exception e){}
				
			}
			_dialog.dismiss();
		}
		
	}
	
	class ProgressHandler implements Runnable{

		Dialog _d;
		
		public ProgressHandler(Dialog d){
			_d = d;
		}
		
		public void run() {
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				
			}finally{
				_d.dismiss();
			}
			
			
		}
		
	}
	
	
}

