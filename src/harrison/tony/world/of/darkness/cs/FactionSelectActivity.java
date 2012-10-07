package harrison.tony.world.of.darkness.cs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FactionSelectActivity extends ListActivity {
	
	public static final String EXTRA_FACTION = FactionSelectActivity.class.getPackage().toString() + ".Faction";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faction_select);
        getActionBar().setDisplayHomeAsUpEnabled(true);
              
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.factions)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_faction_select, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	
    	case android.R.id.home:
    		Intent parent_intent = new Intent(this, CharacterSelectActivity.class);
    		parent_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(parent_intent);
    		finish();
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override
    public void onListItemClick(ListView list_view, View view, int position, long id) {
    	String text = ((TextView)view).getText().toString();
    	
    	Intent intent = new Intent(this, CharacterSheetActivity.class);
    	intent.putExtra(EXTRA_FACTION, text);
    	startActivity(intent);    	
    }
}
