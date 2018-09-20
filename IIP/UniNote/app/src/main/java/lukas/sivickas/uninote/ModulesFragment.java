package lukas.sivickas.uninote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import lukas.sivickas.uninote.adapters.ModuleArrayAdapter;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.helpers.DBHelper;
import lukas.sivickas.uninote.helpers.Module;

import static android.app.Activity.RESULT_OK;


public class ModulesFragment extends Fragment {

    private static final String TAG = "ModulesFragment";

    private static final int INSERT_REQUEST_CODE = 1;
    private static final int EDIT_REQUEST_CODE = 2;

    ListView mModulesView;
    FloatingActionButton mAdd;
    FloatingActionButton mRefresh;

    ArrayList<Module> mModules;
    ModuleArrayAdapter mModuleAdapter;
    DBHelper mDbHelper;

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
        Log.d(TAG, "onCreate: kuriamas naujas fragmentas");
        mDbHelper = new DBHelper(getContext());
        mDbHelper.setDataUpdateEventListener(new DBHelper.DataUpdateEventListener() {
            @Override
            public void onDataUpdated(DBHelper.Destination table) {
                Log.d(TAG, "onDataUpdated: got a callback: " + table);
                if (table == DBHelper.Destination.MODULES) {
                    updateModuleDataSet(mDbHelper.getAllModules());
                }
            }
        });
        mModules = mDbHelper.getAllModules();
        mModuleAdapter = new ModuleArrayAdapter(super.getContext(), mModules, ModulesFragment.super.getActivity().getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: kuriamas naujas view");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modules, container, false);
        mModulesView = view.findViewById(R.id.lw_modules);
        mModulesView.setAdapter(mModuleAdapter);
        mAdd = view.findViewById(R.id.fab_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModulesFragment.super.getContext(), ModuleForm.class);
                startActivityForResult(intent, 1);
            }
        });


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Insert request returned
            if (requestCode == 1) {
                updateModuleDataSet(mDbHelper.getAllModules());
            }
            // Edit request returned
            else if (requestCode == 2) {
                updateModuleDataSet(mDbHelper.getAllModules());
            }
        }
    }

    public void updateModuleDataSet(ArrayList<Module> list) {
        mModules.clear();
        mModules.addAll(list);
        mModuleAdapter.notifyDataSetChanged();
    }
}
