package healerkart.com.dosage.Delta;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import healerkart.com.dosage.Delta.Util;
import android.util.Log;

abstract class AbstractModel {

	
	static final String COL_ID = "_id";

	static String getSql() {
		return Util.concat(COL_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ");
	}
	
	void update(ContentValues cv) {
		cv.put(COL_ID, id);
	}	
	
	void load(Cursor cursor) {
		id = cursor.getLong(cursor.getColumnIndex(COL_ID));
	}
	
	//--------------------------------------------------------------------------
	
	protected long id;
	
	protected void reset() {
		id = 0;
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	abstract long save(SQLiteDatabase db);
	abstract boolean update(SQLiteDatabase db);
	
	public long persist(SQLiteDatabase db) {
		if (id > 0)
		{
			if (update(db))
				return id;
			else
				return 0;
		}
		else
		{
			return (save(db));
		}


	}

}
