package harrison.tony.world.of.darkness.cs.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CharactersOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_CHARACTER = "character";	

	public CharactersOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public CharactersOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler error_handler) {
		super(context, name, factory, version, error_handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
		// TODO Auto-generated method stub

	}

}
