package lukas.sivickas.uninote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import lukas.sivickas.uninote.adapters.ModuleArrayAdapter;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.forms.AssignmentForm;
import lukas.sivickas.uninote.forms.ModuleForm;


public class AssignmentsFragment extends Fragment {

    private static final String TAG = "AssignmentsFragment";
    public static DBHelper mDbHelper;

    public AssignmentsFragment() {
        // Required empty public constructor
    }

    public static AssignmentsFragment newInstance(String param1, String param2) {
        AssignmentsFragment fragment = new AssignmentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating new AssignmentsFragment");
        mDbHelper = new DBHelper(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.mToolbar.setTitle(getString(R.string.title_assignments));
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d(TAG, "onOptionsItemSelected: add pressed");
                Intent intent = new Intent(getContext(), AssignmentForm.class);
                startActivity(intent);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
