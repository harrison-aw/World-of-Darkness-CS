package harrison.tony.world.of.darkness.cs;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CharacterSheetActivity extends FragmentActivity implements ActionBar.TabListener {
	
	//static final private String TAG = "CharacterSheetActivity";

    private SectionsPagerAdapter m_sections_pager_adapter;
    private ViewPager m_view_pager;
    
    private Typeface m_cezanne;
    private Typeface m_cas_antn;
    public Typeface getHeadingTypeface() { return m_cezanne; }
    public Typeface getBodyTypeface() { return m_cas_antn; }
    

    @Override
    public void onCreate(Bundle saved_instance_state) {
        super.onCreate(saved_instance_state);
        setContentView(R.layout.activity_character_sheet);
        
        final ActionBar action_bar = getActionBar();
        action_bar.setDisplayHomeAsUpEnabled(true);
        action_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
        
        m_sections_pager_adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        m_view_pager = (ViewPager) findViewById(R.id.pager);
        m_view_pager.setAdapter(m_sections_pager_adapter);
        m_view_pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                action_bar.setSelectedNavigationItem(position);
            }
        });
                      
        for (int i = 0; i < m_sections_pager_adapter.getCount(); i++) {
            action_bar.addTab(
                    action_bar.newTab()
                            .setText(m_sections_pager_adapter.getPageTitle(i))
                            .setTabListener(this));                          
        }
        
        AssetManager mgr = getAssets();
        m_cezanne = Typeface.createFromAsset(mgr, "fonts/Cezanne.ttf");
        m_cas_antn = Typeface.createFromAsset(mgr, "fonts/cas_antn.ttf");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_character_sheet, menu);
        return true;
    }

    // @Implements
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // @Implements
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        m_view_pager.setCurrentItem(tab.getPosition());
    }

    // @Implements
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }    

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	    	
    	@SuppressWarnings("rawtypes")
		private Class[] m_sections = {BasicInfoFragment.class, AttributesFragment.class, SkillsFragment.class}; 
    			
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	try {
				return (Fragment) m_sections[i].newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        	return null;  	
        }

        @Override
        public int getCount() {
            return m_sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	try {
				return (String) m_sections[position].getField("TITLE").get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
        	return "";                    
        }
    }
    
    public static class BasicInfoFragment extends Fragment {
    	static final public String TITLE = "Basic Info";
    			
    	public BasicInfoFragment() {    		
    	}        	
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved_instance_state) {
    		return inflater.inflate(R.layout.fragment_basic_info, container, false);    		
    	}
    }
    
    public static class AttributesFragment extends Fragment {
    	static final public String TITLE = "Attributes";    	
    	
    	public AttributesFragment() {
    	}
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved_instance_state) {
    		ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_skills, container, false);  // TODO: This should be made fragment agnostic
    		LinearLayout layout_container = (LinearLayout) layout.findViewById(R.id.container);
    		
    		Resources res = getResources();
    		CharacterSheetActivity activity = (CharacterSheetActivity) getActivity();
    		
    		activity.addPointItemSections(res.obtainTypedArray(R.array.attributes), layout_container);
    		
    		return layout;
    		
    	}
    }
    
    public static class SkillsFragment extends Fragment {
    	static final public String TITLE = "Skills";    
    	
    	public SkillsFragment() {
    	}
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved_instance_state) {    		
    		ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_skills, container, false);
    		LinearLayout layout_container = (LinearLayout) layout.findViewById(R.id.container);
    		    		    	
    		Resources res = getResources();
    		CharacterSheetActivity activity = (CharacterSheetActivity) getActivity();
    		
    		activity.addPointItemSections(res.obtainTypedArray(R.array.attributes), layout_container);
    		
    		return layout;
    	}
   
    }
    
    static private int getResourceId(String name, Class<?> r_type) {    		
		try {
			return r_type.getField(name).getInt(null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new Resources.NotFoundException("Unable to find resource named: " + name + "(No such field)");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Resources.NotFoundException("Unable to retrieve resource named: " + name + "(Illegal Argument)");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Resources.NotFoundException("Unable to access resource named: " + name + "(Illegal Access)");
		}
	}
    
    private String getSectionName(int section_array_id) {
    	Resources res = getResources();    	
		return res.getString(getResourceId(res.getResourceEntryName(section_array_id), R.string.class));
	}
    
    private TextView createHeading(String text) {
		TextView heading = new TextView(this);
		heading.setText(text);
		heading.setGravity(Gravity.CENTER_HORIZONTAL);
		heading.setTypeface(getHeadingTypeface());
		heading.setTextColor(0xffbd2e2e);
		heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36.0f);
		
		return heading;
	}
    
    private LinearLayout createTextItem(String label_text) {
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.text_item, null, false);    		
		
		TextView label = (TextView) layout.findViewById(R.id.label);    	
		label.setText(label_text);
		label.setTypeface(getBodyTypeface());
		label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22.0f);
		
		return layout;    	
    }
    
    private LinearLayout createSpinnerItem(String label_text, String[] choices) {
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.text_item, null, false);
		
		TextView label = (TextView) layout.findViewById(R.id.label);
		label.setText(label_text);
		
		Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
		//ArrayAdapter adapter = ArrayAdapter<CharSequence>.createFromResource(this, R.array.)
		
		return layout;
    }
    
    private LinearLayout createPointItem(String label_text) {   	    	
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.points_item, null, false);    		
		
		TextView label = (TextView) layout.findViewById(R.id.label);    	
		label.setText(label_text);
		label.setTypeface(getBodyTypeface());
		label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22.0f);
		
		return layout;
	}
    
    private void addPointItemSections(TypedArray items, LinearLayout parent) {    			
		Resources res = getResources();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		for (int i = 0; i < items.length(); ++i ) {
			int id = items.getResourceId(i, 0);
			
			parent.addView(createHeading(getSectionName(id)), params);
		
			String[] skill_group = res.getStringArray(id);
			for (int j = 0; j < skill_group.length; ++j) {    				
				parent.addView(createPointItem(skill_group[j]));
			}
		}
    }
}
