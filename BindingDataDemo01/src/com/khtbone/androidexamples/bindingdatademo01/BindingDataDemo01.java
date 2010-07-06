package com.khtbone.androidexamples.bindingdatademo01;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BindingDataDemo01 extends Activity implements Spinner.OnItemSelectedListener{
	
	private static final String[] PROJECTION = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
	private TextView _txtOutput = null;
	private Cursor cur = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _txtOutput = (TextView) findViewById(R.id.txtOutput);
        
	     // Get a Spinner and bind it to an ArrayAdapter that 
	     // references a String array.
	     Spinner s1 = (Spinner) findViewById(R.id.spinner1);
	     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     s1.setAdapter(adapter);
	
	     try{
	     Spinner s2 = (Spinner) findViewById(R.id.spinner2);
	     cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null,null);
	     
	     SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
	             android.R.layout.simple_spinner_item, // Use a template
	                                                   // that displays a
	                                                   // text view
	             cur, // Give the cursor to the list adapter
	             new String[] {ContactsContract.Data.DISPLAY_NAME}, // Map the NAME column in the
	                                                  // people database to...
	             new int[] {android.R.id.text1}); // The "text1" view defined in
	                                              // the XML template
	                                                  
         adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         s2.setAdapter(adapter2);
         
         s2.setOnItemSelectedListener(this);
	     }catch(Exception e){
	    	 _txtOutput.setText(e.toString());
	     }
         
    }

	public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
		//Toast.makeText(this, String.format("Position: %d, ID: %d", position,id), Toast.LENGTH_SHORT).show();
		
		try{
			cur.moveToPosition(position);
			String cId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
			String cName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			//String cPhone = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			Toast.makeText(this, String.format("ID: %s, NAME: %s", cId,cName), Toast.LENGTH_SHORT).show();
			
		}catch(Exception e){
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(this, "not made", Toast.LENGTH_SHORT).show();
		
	}
}