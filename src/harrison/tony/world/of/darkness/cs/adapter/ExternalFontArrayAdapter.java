package harrison.tony.world.of.darkness.cs.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExternalFontArrayAdapter<T> extends ArrayAdapter<T> {
	
	private int m_resource;
	private int m_text_view_resource_id;
	
	private Typeface m_typeface;

	public ExternalFontArrayAdapter(Context context, int text_view_resource_id, Typeface typeface) {
		super(context, text_view_resource_id);				
		init(0, text_view_resource_id, typeface);
	}

	public ExternalFontArrayAdapter(Context context, int resource, int text_view_resource_id, Typeface typeface) {
		super(context, resource, text_view_resource_id);
		init(resource, text_view_resource_id, typeface);		
	}

	public ExternalFontArrayAdapter(Context context, int text_view_resource_id, T[] objects, Typeface typeface) {
		super(context, text_view_resource_id, objects);
		init(0, text_view_resource_id, typeface);		
	}

	public ExternalFontArrayAdapter(Context context, int text_view_resource_id, List<T> objects, Typeface typeface) {
		super(context, text_view_resource_id, objects);
		init(0, text_view_resource_id, typeface);		
	}

	public ExternalFontArrayAdapter(Context context, int resource, int text_view_resource_id, T[] objects, Typeface typeface) {
		super(context, resource, text_view_resource_id, objects);
		init(resource, text_view_resource_id, typeface);		
	}

	public ExternalFontArrayAdapter(Context context, int resource, int text_view_resource_id, List<T> objects, Typeface typeface) {
		super(context, resource, text_view_resource_id, objects);
		init(resource, text_view_resource_id, typeface);		
	}
	
	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		View view = super.getView(position, convert_view, parent);		
		
		if (m_resource == 0) {			
			((TextView)view).setTypeface(m_typeface);
		} else {
			((TextView)view.findViewById(m_text_view_resource_id)).setTypeface(m_typeface);
		}
					
		return view;
	}
	
	private void init(int resource, int text_view_resource_id, Typeface typeface) {
		m_resource = resource;
		m_text_view_resource_id = text_view_resource_id;
		m_typeface = typeface;
	}
}
