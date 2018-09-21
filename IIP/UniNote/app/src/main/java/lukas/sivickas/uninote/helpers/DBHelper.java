package lukas.sivickas.uninote.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


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

        Destination(int id) {
            this.id = id;
        }

        public int id() {
            return id;
        }

    }

    // This interface defines the type of messages I want to communicate to my owner
    public interface DataUpdateEventListener {
        // These methods are different events and need to pass relavant arguments related to the event
        void onDataUpdated(Destination table);
    }

    private DataUpdateEventListener listener;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.listener = null;
    }

    public DBHelper(Context context, DataUpdateEventListener listener) {
        super(context, DATABASE_NAME, null, 1);
        this.listener = listener;
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
        if (listener != null) {
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
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
        if (listener != null) {
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        Log.d(TAG, "updateModule: " + MODULES_CREATION_CODE);
        Log.d(TAG, "updateModule: " + ASSIGNMENTS_CREATION_CODE);
        Log.d(TAG, "updateModule: " + NOTES_CREATION_CODE);
        return true;
    }

    public Integer deleteModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(module.getId())};
        int notes = db.delete(NOTES_TABLE_NAME, "module_id = ?", args);
        int assignments = db.delete(ASSIGNMENTS_TABLE_NAME, "module_id = ?", args);
        int modules = db.delete(MODULES_TABLE_NAME, "id = ?", args);
        if (listener != null) {
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
        if (listener != null) {
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

    /*
    //region Note methods
    public boolean insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", note.getName());
        contentValues.put("code", note.getLead());
        contentValues.put("lead", note.getLead());
        db.insert(MODULES_TABLE_NAME, null, contentValues);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        return true;
    }

    public boolean updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", note.getName());
        contentValues.put("code", note.getLead());
        contentValues.put("lead", note.getLead());
        db.update(MODULES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(note.getId())});
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        Log.d(TAG, "updateModule: " + MODULES_CREATION_CODE);
        Log.d(TAG, "updateModule: " + ASSIGNMENTS_CREATION_CODE);
        Log.d(TAG, "updateModule: " + NOTES_CREATION_CODE);
        return true;
    }

    public Integer deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(note.getId())};
        int notes = db.delete(NOTES_TABLE_NAME, "module_id = ?", args);
        int assignments = db.delete(ASSIGNMENTS_TABLE_NAME, "module_id = ?", args);
        int modules = db.delete(MODULES_TABLE_NAME, "id = ?", args);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        }
        return notes + assignments + modules;
    }

    public Integer deleteNote(int id) {
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

    public Note getNote(int id) {

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

    public ArrayList<Note> getAllNotes() {
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

    //region Assignment methods
    public boolean insertAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", assignment.getName());
        contentValues.put("code", assignment.getLead());
        contentValues.put("lead", assignment.getLead());
        db.insert(MODULES_TABLE_NAME, null, contentValues);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        return true;
    }

    public boolean updateAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", assignment.getName());
        contentValues.put("code", assignment.getLead());
        contentValues.put("lead", assignment.getLead());
        db.update(MODULES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(assignment.getId())});
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        Log.d(TAG, "updateModule: " + MODULES_CREATION_CODE);
        Log.d(TAG, "updateModule: " + ASSIGNMENTS_CREATION_CODE);
        Log.d(TAG, "updateModule: " + NOTES_CREATION_CODE);
        return true;
    }

    public Integer deleteAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(assignment.getId())};
        int notes = db.delete(NOTES_TABLE_NAME, "module_id = ?", args);
        int assignments = db.delete(ASSIGNMENTS_TABLE_NAME, "module_id = ?", args);
        int modules = db.delete(MODULES_TABLE_NAME, "id = ?", args);
        if(listener != null){
            listener.onDataUpdated(Destination.MODULES);
        }
        return notes + assignments + modules;
    }

    public Integer deleteAssignment(int id) {
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

    public Assignment getAssignment(int id) {

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

    public ArrayList<Assignment> getAllAssignments() {
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
    */
    public Assignment getNextAssignment(int module_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select id, cast(due_date as text) as due_date, description) " +
                "from " + ASSIGNMENTS_TABLE_NAME + " " +
                "where module_id = " + module_id  + " " +
                "and due_date >= date('now') " +
                "order by due_date asc limit 1", null);

        res.moveToFirst();
        if(res.getCount()>0) {
            int idIndex = res.getColumnIndexOrThrow("id");
            int dueDateIndex = res.getColumnIndexOrThrow("due_date");
            int descriptionIndex = res.getColumnIndexOrThrow("description");

            int id;
            Date dueDate;
            String description;

            id = res.getInt(dueDateIndex);
            String dueDateString = res.getString(dueDateIndex);
            description = res.getString(descriptionIndex);

            dueDate = new Date(dueDateString);

            return new Assignment(id, module_id, dueDate, description);
        } else {
            return null;
        }
    }

    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }

    //endregion
}