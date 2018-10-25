package lukas.sivickas.uninote.forms;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lukas.sivickas.uninote.AssignmentsFragment;
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.database.Assignment;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.database.Module;

public class AssignmentForm extends AppCompatActivity {

    private static final String TAG = "AssignmentForm";

    Spinner mModule;
    ArrayList<Module> modules;
    ArrayList<String> moduleStrings;
    ArrayAdapter<String> mModuleAdapter;

    EditText mTitle;

    TextView mDate;
    Button mSetDate;
    TextView mTime;
    Button mSetTime;
    EditText mDescription;

    Button mSave;

    DBHelper mDbHelper;

    boolean mEditing;
    int id;

    Assignment assignment;//for editing


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_form);
        mEditing = getIntent().getBooleanExtra("editing", false);

        mDbHelper = AssignmentsFragment.mDbHelper;


        mTitle = findViewById(R.id.et_assig_title);
        mTime = findViewById(R.id.tv_assig_time);
        mSetTime = findViewById(R.id.btn_assig_time);
        mModule = findViewById(R.id.spn_assig_module_selected);
        mDate = findViewById(R.id.tv_assig_date);
        mSetDate = findViewById(R.id.btn_assig_date);
        mDescription = findViewById(R.id.et_assig_description);
        mSave = findViewById(R.id.btn_assig_save);

        modules = mDbHelper.getAllModules();
        moduleStrings = getAllModuleNames(modules);
        mModuleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moduleStrings);
        mModuleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mModule.setAdapter(mModuleAdapter);

        mSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AssignmentForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String h = String.valueOf(selectedHour);
                        String m = String.valueOf(selectedMinute);
                        if (selectedHour < 10) h = "0" + h;
                        if (selectedMinute < 10) m = "0" + m;
                        mTime.setText(h + ":" + m);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        mSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AssignmentForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String m = String.valueOf(month);
                        String d = String.valueOf(day);
                        if (month < 10) m = "0" + m;
                        if (day < 10) d = "0" + d;
                        mDate.setText(year + "/" + m + "/" + d);
                    }
                }, year, month, day);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = mDate.getText().toString();
                String time = mTime.getText().toString();
                Date dateTime = parseDate(time, date);
                Module module = modules.get(mModule.getSelectedItemPosition());
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();

                if (isInputValid(dateTime, title)) {
                    if (mEditing) {
                        mDbHelper.updateAssignment(new Assignment(assignment.getId(), module, dateTime, title, description, assignment.isDone()));
                    } else {
                        mDbHelper.insertAssignment(new Assignment(module, dateTime, title, description, false));
                    }
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Log.d(TAG, "onClickSave: input was not valid");
                }
            }
        });

        if (mEditing) {
            id = getIntent().getIntExtra("id", -1);
            if (id >= 0) {
                Log.e(TAG, "onCreate: " + id);
                assignment = mDbHelper.getAssignment(id);
                mModule.setSelection(getModulePosition(assignment.getModule()));
                mDate.setText(getDateString(assignment));
                mTime.setText(getTimeString(assignment));
                mTitle.setText(assignment.getTitle());
                mDescription.setText(assignment.getDescription());
            } else {
                finish();
            }
        }

    }

    private Date parseDate(String time, String date) {
        Date dateObj;
        String dateTime = date + " " + time;
        Log.d(TAG, "parseDate: " + dateTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            dateObj = format.parse(dateTime);
            Log.d(TAG, "parseDate: parsed: " + dateObj);
            return dateObj;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isInputValid(Date dateTime, String title) {
        String errorMsg = "";
        if (dateTime == null) errorMsg += "Date and time are required\n";
        if (title.length() < 1) errorMsg += "Title is required";
        if (errorMsg.length() > 1) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    private String getTimeString(Assignment assignment) {
        String time = "";
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(assignment.getDueDate());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour < 10) {
            time += "0" + hour;
        } else {
            time += hour;
        }
        time += ":";
        if (minute < 10) {
            time += "0" + minute;
        } else {
            time += minute;
        }
        return time;
    }

    private String getDateString(Assignment assignment) {
        String date = "";
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(assignment.getDueDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date += year + "/";
        if (month < 10) {
            date += "0" + month;
        } else {
            date += month;
        }
        date += "/";
        if (day < 10) {
            date += "0" + day;
        } else {
            date += day;
        }
        return date;
    }
}
