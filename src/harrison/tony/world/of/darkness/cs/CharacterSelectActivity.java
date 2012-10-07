package harrison.tony.world.of.darkness.cs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class CharacterSelectActivity extends ListActivity implements OnClickListener {
	private final String TAG = "CharacterSelect";	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);
        
        ListView list_view = getListView();
        list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);        
        list_view.setMultiChoiceModeListener(new SelectListener());        
        
        
        // TODO: The following section of code should be replaced with a database retrieval
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> character = new HashMap<String, String>();
        character.put("name", "Enki");
        character.put("faction", "Vampire");        
        data.add(character);
        HashMap<String, String> character2 = new HashMap<String,String>();
        character2.put("name", "Malice");
        character2.put("faction", "Sin-eater");
        data.add(character2);
        
        ListAdapter adapter = new CharacterSelectListAdapter(this, data, R.layout.list_view_item_character_select,
        			new String[] {"name", "faction"},
        			new int[] {R.id.name, R.id.faction });        
        setListAdapter(adapter);
        
        int child_count = list_view.getChildCount();
        for (int i = 0; i < child_count; ++i) {
        	list_view.getChildAt(i).findViewById(R.id.check_box).setOnClickListener(this);        	
        }               
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_character_select, menu);        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	
    	case R.id.menu_add_character:
    		startActivity(new Intent(this, FactionSelectActivity.class));    		
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);    		
    	}
    	
    }    
    
    @Override
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	// TODO: make the click listener function
    	Log.d(TAG, "Click at position: " + position); 	
    }
    
    
    // @Implements
    public void onClick(View v) {
    	Log.d(TAG, v.toString());
    }
    
    /**
     * This class handles the contextual action bar
     */
	private class SelectListener implements ListView.MultiChoiceModeListener {
		
		private MenuItem m_menu_edit_character;
		
		// @Implements
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {					
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mode_multi_choice, menu);
            mode.setTitle("Select Items");
            
            m_menu_edit_character = menu.findItem(R.id.menu_edit_character);                                  
            
            return true;
        }
				
		// @Implements
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }
		
		// @Implements
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {        	     
        	switch (item.getItemId()) {
        	
        	case R.id.menu_edit_character:
        		Log.d(TAG, "Edit character");
        		return true;
        	
        	case R.id.menu_discard_character:
        		Log.d(TAG, "Discard character");
        		return true;
        		
        	default:
        		return false;
        	}            
        }

        // @Implements
        public void onDestroyActionMode(ActionMode mode) {        	
        }

        // @Implements
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {       	        	
        	ListView parent = getListView();
        	
        	// Set state of child CheckBox
        	RelativeLayout child = (RelativeLayout) parent.getChildAt(position);        	
        	CheckBox checkbox = (CheckBox) child.findViewById(R.id.check_box);        	
        	checkbox.setChecked(checked);
        	
            final int checkedCount = parent.getCheckedItemCount();                       
            switch (checkedCount) {
            case 0:
            	mode.setSubtitle(null);
            	break;
            case 1:
                mode.setSubtitle("One item selected");
                if (!m_menu_edit_character.isVisible()) {
            		m_menu_edit_character.setVisible(true);
            	}
                break;
            default:
            	mode.setSubtitle("" + checkedCount + " items selected");
            	if (m_menu_edit_character.isVisible()) {
            		m_menu_edit_character.setVisible(false);
            	}
            	break;
            }
        }
	}
	
	/**
	 * This class establishes click handlers for the list item checkboxes
	 */
	private class CharacterSelectListAdapter extends SimpleAdapter {				

		public CharacterSelectListAdapter(Context context, List<HashMap<String, String>> data, int resource, String[] from,	int[] to) {
			super(context, data, resource, from, to);
		}
		
		@Override
		public View getView(int position, View convert_view, ViewGroup parent) {		
			RelativeLayout layout = (RelativeLayout) super.getView(position, convert_view, parent);
			
			CheckBox checkbox = (CheckBox) layout.findViewById(R.id.check_box);
			checkbox.setFocusable(false);
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton button_view, boolean checked) {
					ListView list_view = getListView();
					RelativeLayout parent = (RelativeLayout) button_view.getParent();															
					list_view.setItemChecked(list_view.getPositionForView(parent), checked);
				}
			});
					
			return layout;
		}
	}

}
