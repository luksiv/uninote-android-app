package lukas.sivickas.uninote.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lukas.sivickas.uninote.NotesFragment;
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.database.Assignment;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.database.Module;
import lukas.sivickas.uninote.database.Note;

public class NoteForm extends AppCompatActivity {

    private static final String TAG = "NoteForm";

    public static Toolbar mToolbar;
    public static ActionBar mSupportActionBar;

    Spinner mModule;
    ArrayList<Module> modules;
    ArrayList<String> moduleStrings;
    ArrayAdapter<String> mModuleAdapter;

    EditText mTitle;
    EditText mText;
    Button mSave;

    DBHelper mDbHelper;

    boolean mEditing;
    Note mNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        mEditing = getIntent().getBooleanExtra("editing", false);

        mDbHelper = NotesFragment.mDbHelper;

        mToolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(mToolbar);
        mSupportActionBar = getSupportActionBar();
        mSupportActionBar.setDisplayHomeAsUpEnabled(true);

        mModule = findViewById(R.id.spn_note_module_selected);
        mTitle = findViewById(R.id.et_note_title);
        mText = findViewById(R.id.et_note_text);
        mSave = findViewById(R.id.btn_note_save);

        modules = mDbHelper.getAllModules();
        moduleStrings = getAllModuleNames(modules);
        mModuleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moduleStrings);
        mModuleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mModule.setAdapter(mModuleAdapter);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Module module = modules.get(mModule.getSelectedItemPosition());
                String title = mTitle.getText().toString();
                String text = mText.getText().toString();
                Date dt = Calendar.getInstance().getTime();
                if (mEditing) {
                    mDbHelper.updateNote(new Note(mNote.getId(), module, mNote.getCreationDate(), dt, title, text));
                } else {
                    mDbHelper.insertNote(new Note(module, dt, dt, title, text));
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        if (mEditing) {
            int id = getIntent().getIntExtra("id", -1);
            if (id >= 0) {
                Log.e(TAG, "onCreate: " + id);
                mNote = mDbHelper.getNote(id);
                mModule.setSelection(getModulePosition(mNote.getModule()));
                mTitle.setText(mNote.getTitle());
                mText.setText(mNote.getText());
            } else {
                finish();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<String> getAllModuleNames(ArrayList<Module> modules) {
        ArrayList<String> list = new ArrayList<>();
        for (Module module : modules) {
            String moduleInfo = "[" + module.getCode() + "] " + module.getName();
            list.add(moduleInfo);
        }

        return list;
    }

    private int getModulePosition(Module module) {
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getCode() == module.getCode())
                return i;
        }
        return -1;
    }
}
