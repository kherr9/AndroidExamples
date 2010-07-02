package com.khtbone;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MenuDemo extends Activity {
	
	private static final int MENU_NEW_GAME = 0;
	private static final int MENU_QUIT = 1;
	private static final int MENU_COUNTRIES = 2;
	private static final int SUBMENU_NEW = 100;
	private static final int SUBMENU_DELETE = 101;
	
	private String[] _countries = null;
	private TextView _txtOutput = null;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //	init list
        _countries = getResources().getStringArray(R.array.Countries);
        Arrays.sort(_countries);
        
        ListView list = (ListView) findViewById(R.id.lvList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem,_countries);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        
        //	get ref to text views
        _txtOutput = (TextView) findViewById(R.id.txtOutput);

        
    }
    
    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_NEW_GAME, 0, "New Game");
        menu.add(0, MENU_QUIT, 0, "Quit");
        
        SubMenu fileMenu = menu.addSubMenu(0,MENU_COUNTRIES,0,"Country");
        fileMenu.add(0,SUBMENU_NEW,0,"New");
        fileMenu.add(0,SUBMENU_DELETE,0,"Delete");
        
        
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
        case MENU_COUNTRIES:
        	_txtOutput.setText("Selected Country");
        	return true;
    	case SUBMENU_NEW:
    		_txtOutput.setText("Selected New Country");
    		return true;
    	case SUBMENU_DELETE:
    		_txtOutput.setText("Selected Delete Country");
    		return true;
    	default:
    		_txtOutput.setText("unknown menu");
        }
        return false;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if(v.getId() ==R.id.lvList){
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(_countries[info.position]);
			String[] menuItem = getResources().getStringArray(R.array.menu);
			for(int i = 0; i < menuItem.length; i++){
				menu.add(Menu.NONE,i,i,menuItem[i]);
			}
		}
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	  int menuItemIndex = item.getItemId();
    	  String[] menuItems = getResources().getStringArray(R.array.menu);
    	  String menuItemName = menuItems[menuItemIndex];
    	  String listItemName = _countries[info.position];
    	  
    	  _txtOutput.setText(String.format("Select %s for item %s",menuItemName,listItemName));
    	  return true;
	}

}