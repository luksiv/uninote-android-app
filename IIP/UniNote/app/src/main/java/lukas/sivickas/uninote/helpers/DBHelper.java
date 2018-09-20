package lukas.sivickas.uninote.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public static final String DATABASE_NAME = "uninote.db";

    public static final String MODULES_TABLE_NAME = "modules";
    public static final String NOTES_TABLE_NAME = "notes";
    public static final String ASSIGNMENTS_TABLE_NAME = "assignments";

    //region Table creation codes
    private static final String MODULES_CREATION_CODE = "CREATE TABLE IF NOT EXISTS modules (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " name TEXT NOT NULL,\n" +
            " code TEXT,\n" +
            " lead TEXT)";
    private static final String NOTES_CREATION_CODE = "CREATE TABLE IF NOT EXISTS notes (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " creation_date DATETIME NOT NULL,\n" +
            " text TEXT,\n" +
            " module_id INTEGER,\n" +
            " FOREIGN KEY(module_id) REFERENCES modules(id))";
    private static final String ASSIGNMENTS_CREATION_CODE = "CREATE TABLE IF NOT EXISTS assignments (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " due_date DATETIME NOT NULL,\n" +
            " description TEXT,\n" +
            " module_id INTEGER,\n" +
            " FOREIGN KEY(module_id) REFERENCES modules(id))";
    //endregion

    public enum Destination {
        MODULES(0),
        NOTES(1),
        ASSIGNMENTS(2);

        private int id;
        Destination(int id){
            this.id = id;
        }

        public int id(){
            return id;
        }

    }

    // This interface defines the type of messages I want to communicate to my owner
    public interface DataUpdateEventListener{
        // These methods are different events and need to pass relavant arguments related to the event
        void onDataUpdated(Destination table);
    }

    private DataUpdateEventListener listener;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.listener = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MODULES_CREATION_CODE);
        db.execSQL(NOTES_CREATION_CODE);
        db.execSQL(ASSIGNMENTS_CREATION_CODE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MODULES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE_NAME);
        onCreate(db);
    }

    public void setDataUpdateEventListener(DataUpdateEventListener listener) {
        this.listener = listener;
    }

    //region Module methods
    public boolean insertModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", module.getName());
        contentValues.put("code", module.getLead());
        contentValues.put("lead", module.getLead());
        db.insert(MODULES_TABLE_NAME, null, contentValues);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        }
        return true;
    }

    public boolean updateModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", module.getName());
        contentValues.put("code", module.getLead());
        contentValues.put("lead", module.getLead());
        db.update(MODULES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(module.getId())});
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        }
        return true;
    }

    public Integer deleteModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(module.getId())};
        int notes = db.delete(NOTES_TABLE_NAME, "module_id = ?", args);
        int assignments = db.delete(ASSIGNMENTS_TABLE_NAME, "module_id = ?", args);
        int modules = db.delete(MODULES_TABLE_NAME, "id = ?", args);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        }
        return notes + assignments + modules;
    }

    public Integer deleteModule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(id)};
        int notes = db.delete(NOTES_TABLE_NAME, "module_id = ?", args);
        int assignments = db.delete(ASSIGNMENTS_TABLE_NAME, "module_id = ?", args);
        int modules = db.delete(MODULES_TABLE_NAME, "id = ?", args);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
            Log.d(TAG, "deleteModule: message sent");
        } else {
            Log.e(TAG, "deleteModule: listener is null");
        }
        return notes + assignments + modules;
    }

    public Module getModule(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MODULES_TABLE_NAME +
                " where id=" + id + "", null);

        res.moveToFirst();
        int name_index = res.getColumnIndexOrThrow("name");
        int code_index = res.getColumnIndexOrThrow("code");
        int lead_index = res.getColumnIndexOrThrow("lead");

        String name;
        String code;
        String lead;

        name = res.getString(name_index);

        if (res.isNull(code_index)) {
            code = "";
        } else {
            code = res.getString(code_index);
        }

        if (res.isNull(lead_index)) {
            lead = "";
        } else {
            lead = res.getString(lead_index);
        }

        return new Module(id, name, code, lead);
    }

    public ArrayList<Module> getAllModules() {
        ArrayList<Module> modules = new ArrayList<Module>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MODULES_TABLE_NAME, null);
        res.moveToFirst();

        int id_index = res.getColumnIndexOrThrow("id");
        int name_index = res.getColumnIndexOrThrow("name");
        int code_index = res.getColumnIndexOrThrow("code");
        int lead_index = res.getColumnIndexOrThrow("lead");

        int id;
        String name;
        String code;
        String lead;

        while (res.isAfterLast() == false) {
            id = res.getInt(id_index);
            name = res.getString(name_index);
            if (res.isNull(code_index)) {
                code = "";
            } else {
                code = res.getString(code_index);
            }

            if (res.isNull(lead_index)) {
                lead = "";
            } else {
                lead = res.getString(lead_index);
            }

            modules.add(new Module(id, name, code, lead));
            res.moveToNext();
        }
        return modules;
    }
    //endregion
}