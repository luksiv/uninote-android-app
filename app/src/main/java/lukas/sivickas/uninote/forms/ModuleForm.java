package lukas.sivickas.uninote.forms;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lukas.sivickas.uninote.ModulesFragment;
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.database.Module;

public class ModuleForm extends AppCompatActivity {

    private static final String TAG = "ModuleForm";

    public static Toolbar mToolbar;
    public static ActionBar mSupportActionBar;

    EditText mName;
    EditText mCode;
    EditText mLead;
    Button mSave;

    DBHelper mDbHelper;

    boolean mEditing;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_form);
        mEditing = getIntent().getBooleanExtra("editing", false);

        mToolbar = findViewById(R.id.module_toolbar);
        setSupportActionBar(mToolbar);
        mSupportActionBar = getSupportActionBar();
        mSupportActionBar.setDisplayHomeAsUpEnabled(true);

        mDbHelper = ModulesFragment.mDbHelper;

        mName = findViewById(R.id.et_module_name);
        mCode = findViewById(R.id.et_module_code);
        mLead = findViewById(R.id.et_module_lead);
        mSave = findViewById(R.id.btn_module_save);

        if(mEditing){
            id = getIntent().getIntExtra("id", -1);
            if(id >= 0){
                Log.e(TAG, "onCreate: " + id);
                Module module = mDbHelper.getModule(id);
                mName.setText(module.getName());
                mCode.setText(module.getCode());
                mLead.setText(module.getLead());
            }
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditing){
                    String name = mName.getText().toString();
                    String code = mCode.getText().toString();
                    String lead = mLead.getText().toString();
                    mDbHelper.updateModule(new Module(id, name, code, lead));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    String name = mName.getText().toString();
                    String code = mCode.getText().toString();
                    String lead = mLead.getText().toString();
                    mDbHelper.insertModule(new Module(name, code, lead));
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

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
}
