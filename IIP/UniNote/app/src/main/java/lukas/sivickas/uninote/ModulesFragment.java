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

import java.util.ArrayList;

import lukas.sivickas.uninote.adapters.ModuleArrayAdapter;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.database.Module;


public class ModulesFragment extends Fragment {

    private static final String TAG = "ModulesFragment";

    private static final int INSERT_REQUEST_CODE = 1;
    private static final int EDIT_REQUEST_CODE = 2;

    ListView mModulesView;

    ArrayList<Module> mModules;
    ModuleArrayAdapter mModuleAdapter;
    public static DBHelper mDbHelper;

    public ModulesFragment() {
        // Required empty public constructor
    }

    public static ModulesFragment newInstance(String param1, String param2) {
        ModulesFragment fragment = new ModulesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating new ModulesFragment");
        mDbHelper = new DBHelper(getContext());
        mDbHelper.setDataUpdateEventListener(new DBHelper.DataUpdateEventListener() {
            @Override
            public void onDataUpdated(DBHelper.Destination table) {
                Log.d(TAG, "onDataUpdated: got a callback: " + table);
                updateModuleDataSet(mDbHelper.getAllModules());

            }
        });
        mModules = mDbHelper.getAllModules();
        mModuleAdapter = new ModuleArrayAdapter(super.getContext(), mModules, ModulesFragment.super.getActivity().getFragmentManager());
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: kuriamas naujas view");
        MainActivity.mToolbar.setTitle(getString(R.string.title_modules));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modules, container, false);
        mModulesView = view.findViewById(R.id.lv_modules);
        mModulesView.setAdapter(mModuleAdapter);

        return view;

    }

    public void updateModuleDataSet(ArrayList<Module> list) {
        if (mModules != null) {
            if (!mModules.isEmpty()) {
                mModules.clear();
            }
            mModules.addAll(list);
            mModuleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d(TAG, "onOptionsItemSelected: add pressed");
                Intent intent = new Intent(getContext(), ModuleForm.class);
                startActivity(intent);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


}
