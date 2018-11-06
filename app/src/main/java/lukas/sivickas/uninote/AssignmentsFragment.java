package lukas.sivickas.uninote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lukas.sivickas.uninote.adapters.AssignmentArrayAdapter;
import lukas.sivickas.uninote.database.Assignment;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.forms.AssignmentForm;


public class AssignmentsFragment extends Fragment {

    private static final String TAG = "AssignmentsFragment";

    ListView assignmentView;

    ArrayList<Assignment> mAssignments;
    AssignmentArrayAdapter mAssignmentAdapter;
    public static DBHelper mDbHelper;

    public AssignmentsFragment() {
        // Required empty public constructor
    }

    public static AssignmentsFragment newInstance() {
        AssignmentsFragment fragment = new AssignmentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating new AssignmentsFragment");
        mDbHelper = new DBHelper(getContext());
        mDbHelper.setDataUpdateEventListener(new DBHelper.DataUpdateEventListener() {
            @Override
            public void onDataUpdated(DBHelper.Destination table) {
                Log.d(TAG, "onDataUpdated: got a callback: " + table);
                if (table == DBHelper.Destination.ASSIGNMENTS) {
                    updateAssignmentDataSet(mDbHelper.getAllAssignments());
                }
            }
        });

        mAssignments = mDbHelper.getAllAssignments();
        mAssignmentAdapter = new AssignmentArrayAdapter(super.getContext(), mAssignments, AssignmentsFragment.super.getActivity().getFragmentManager());

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.mToolbar.setTitle(getString(R.string.title_assignments));
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        assignmentView = view.findViewById(R.id.lv_assignments);
        assignmentView.setAdapter(mAssignmentAdapter);
        updateAssignmentDataSet(mDbHelper.getAllAssignments());

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d(TAG, "onOptionsItemSelected: add pressed");
                if (mDbHelper.getAllModules().size() != 0) {
                    Intent intent = new Intent(getContext(), AssignmentForm.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this.getContext(), "Add modules, before adding assignments", Toast.LENGTH_SHORT).show();
                }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateAssignmentDataSet(ArrayList<Assignment> list) {
        if (mAssignments != null) {
            if (!mAssignments.isEmpty()) {
                mAssignments.clear();
            }
            mAssignments.addAll(list);
            mAssignmentAdapter.notifyDataSetChanged();
        }
    }

}
