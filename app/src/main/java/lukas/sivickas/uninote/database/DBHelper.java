package lukas.sivickas.uninote.database;

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
            " creation_date TEXT NOT NULL,\n" +
            " text TEXT,\n" +
            " module_id INTEGER,\n" +
            " FOREIGN KEY(module_id) REFERENCES modules(id))";
    private static final String ASSIGNMENTS_CREATION_CODE = "CREATE TABLE IF NOT EXISTS assignments (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " due_date BIGINT NOT NULL,\n" +
            " title TEXT,\n" +
            " description TEXT,\n" +
            " module_id INTEGER,\n" +
            " is_done BOOLEAN, \n" +
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
        contentValues.put("code", module.getCode());
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
        contentValues.put("code", module.getCode());
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
        Cursor res = db.rawQuery("select * from " + MODULES_TABLE_NAME + " order by name asc", null);
        res.moveToFirst();

        //TODO: remove this when publishing
        if (res.getCount() == 0) {
            this.insertModule(new Module("Informacinių sistemų pagringai", "P170B114", "R. Butleris"));
            this.insertModule(new Module("Skaitiniai metodai ir algoritmai", "P170B115", "R. Barauskas"));
            this.insertModule(new Module("Lygiagretusis programavimas", "P170B328", "Karolis Ryselis"));
            this.insertModule(new Module("Išmaniųjų įrenginių programavimas", "P175B156", "Tomas Blažauskas"));
            this.insertModule(new Module("Kompiuterių tinklai ir internetinės technologijos", "T120B145", "R. Kavaliūnas"));
            res = db.rawQuery("select * from " + MODULES_TABLE_NAME + " order by name asc", null);
            res.moveToFirst();
        }

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
            Log.d(TAG, "getAllModules: " + id + " " + name + " " + code + " " + lead);
            modules.add(new Module(id, name, code, lead, getNextAssignment(id)));
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
    //endregion*/

    //region Assignment methods
    public boolean insertAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", assignment.getTitle());
        contentValues.put("description", assignment.getDescription());
        contentValues.put("due_date", assignment.getDueDateTimestamp());
        contentValues.put("module_id", assignment.getModuleId());
        contentValues.put("is_done", assignment.isDone());
        db.insert(ASSIGNMENTS_TABLE_NAME, null, contentValues);
        if (listener != null) {
            listener.onDataUpdated(Destination.ASSIGNMENTS);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        return true;
    }

    public boolean updateAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", assignment.getTitle());
        contentValues.put("description", assignment.getDescription());
        contentValues.put("due_date", assignment.getDueDateTimestamp());
        contentValues.put("module_id", assignment.getModuleId());
        contentValues.put("is_done", assignment.isDone());
        db.update(ASSIGNMENTS_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(assignment.getId())});
        if (listener != null) {
            listener.onDataUpdated(Destination.ASSIGNMENTS);
        } else {
            Log.e(TAG, "insertModule: listener is null");
        }
        return true;
    }

    public void deleteAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(assignment.getId())};
        db.delete(ASSIGNMENTS_TABLE_NAME, "id = ?", args);
        if (listener != null) {
            listener.onDataUpdated(Destination.ASSIGNMENTS);
        }
    }

    public void deleteAssignment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{Integer.toString(id)};
        db.delete(ASSIGNMENTS_TABLE_NAME, "id = ?", args);
        if (listener != null) {
            listener.onDataUpdated(Destination.ASSIGNMENTS);
        }
    }

    public Assignment getAssignment(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ASSIGNMENTS_TABLE_NAME +
                " where id=" + id + "", null);

        res.moveToFirst();

        int title_index = res.getColumnIndexOrThrow("title");
        int desc_index = res.getColumnIndexOrThrow("description");
        int due_date_index = res.getColumnIndexOrThrow("due_date");
        int module_id_index = res.getColumnIndexOrThrow("module_id");
        int is_done_index = res.getColumnIndex("is_done");

        String title;
        String desc;
        Long due_date_timestamp;
        int module_id;
        Boolean isDone;

        title = res.getString(title_index);
        desc = res.getString(desc_index);
        due_date_timestamp = res.getLong(due_date_index);
        module_id = res.getInt(module_id_index);
        isDone = res.getInt(is_done_index) > 0;

        Date due_date = new Date(due_date_timestamp);
        Module module = this.getModule(module_id);

        return new Assignment(id, module, due_date, title, desc, isDone);
    }

    public ArrayList<Assignment> getAllAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ASSIGNMENTS_TABLE_NAME + " order by due_date", null);
        res.moveToFirst();

        //TODO: remove this when publishing
        if (res.getCount() == 0) {
            ArrayList<Module> modules = getAllModules();
            // public Assignment(Module moduleId, Date dueDate, String title, String description, boolean isDone) {
            this.insertAssignment(
                    new Assignment(modules.get(0),
                            new Date(Long.parseLong("1540486063000")),
                            "Pirmas atsiskaitymas",
                            "Pirmo atsiskaitymo aprasas",
                            false));
            this.insertAssignment(
                    new Assignment(modules.get(1),
                            new Date(Long.parseLong("1540658863000")),
                            "Pirmas atsiskaitymas",
                            "Pirmo atsiskaitymo aprasas",
                            false));
            this.insertAssignment(
                    new Assignment(modules.get(2),
                            new Date(Long.parseLong("1540918063000")),
                            "Pirmas atsiskaitymas",
                            "Pirmo atsiskaitymo aprasas",
                            false));
            this.insertAssignment(
                    new Assignment(modules.get(3),
                            new Date(Long.parseLong("1543596463000")),
                            "Pirmas atsiskaitymas",
                            "Pirmo atsiskaitymo aprasas",
                            false));
            this.insertAssignment(
                    new Assignment(modules.get(4),
                            new Date(Long.parseLong("1535820463000")),
                            "Pirmas atsiskaitymas",
                            "Pirmo atsiskaitymo aprasas",
                            false));
            res = db.rawQuery("select * from " + ASSIGNMENTS_TABLE_NAME + " order by due_date", null);
            res.moveToFirst();
        }

        int id_index = res.getColumnIndexOrThrow("id");
        int title_index = res.getColumnIndexOrThrow("title");
        int desc_index = res.getColumnIndexOrThrow("description");
        int due_date_index = res.getColumnIndexOrThrow("due_date");
        int module_id_index = res.getColumnIndexOrThrow("module_id");
        int is_done_index = res.getColumnIndex("is_done");

        int id;
        String title;
        String desc;
        Long due_date_timestamp;
        int module_id;
        Boolean isDone;

        while (res.isAfterLast() == false) {
            id = res.getInt(id_index);
            title = res.getString(title_index);
            desc = res.getString(desc_index);
            due_date_timestamp = res.getLong(due_date_index);
            module_id = res.getInt(module_id_index);
            isDone = res.getInt(is_done_index) > 0;
            Log.d(TAG, "getAllAssignments: " + res.getInt(is_done_index));
            Log.d(TAG, "getAllAssignments: " + isDone);

            Date due_date = new Date(due_date_timestamp);
            Module module = this.getModule(module_id);

            assignments.add(new Assignment(id, module, due_date, title, desc, isDone));
            res.moveToNext();
        }
        return assignments;
    }

    public Assignment getNextAssignment(int module_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from assignments where module_id = "+ module_id +" and is_done = 0 order by due_date asc limit 1", null);

        res.moveToFirst();
        if (res.getCount() > 0) {
            int id_index = res.getColumnIndexOrThrow("id");
            int title_index = res.getColumnIndexOrThrow("title");
            int desc_index = res.getColumnIndexOrThrow("description");
            int due_date_index = res.getColumnIndexOrThrow("due_date");
            int is_done_index = res.getColumnIndex("is_done");

            int id;
            String title;
            String desc;
            Long due_date_timestamp;
            Boolean isDone;

            id = res.getInt(id_index);
            title = res.getString(title_index);
            desc = res.getString(desc_index);
            due_date_timestamp = res.getLong(due_date_index);
            isDone = res.getInt(is_done_index) > 0;

            Date due_date = new Date(due_date_timestamp);
            Module module = this.getModule(module_id);

            return new Assignment(id, module, due_date, title, desc, isDone);
        } else {
            return null;
        }
    }

    //endregion
}