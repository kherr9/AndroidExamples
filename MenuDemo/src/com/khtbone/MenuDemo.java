package com.khtbone;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MenuDemo extends Activity {
	
	private static final int MENU_NEW_GAME = 0;
	private static final int MENU_QUIT = 1;
	
	private TextView _txtOutput = null;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _txtOutput = (TextView) findViewById(R.id.txtOutput);

    }
    
    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_NEW_GAME, 0, "New Game");
        menu.add(0, MENU_QUIT, 0, "Quit");
        return true;
    }

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_NEW_GAME:
        	_txtOutput.setText("Selected New Game");
            //newGame();
            return true;
        case MENU_QUIT:
        	_txtOutput.setText("Selected Quit");
            //quit();
            return true;
        }
        return false;
    }
}