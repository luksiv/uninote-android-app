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

import lukas.sivickas.uninote.adapters.NoteArrayAdapter;
import lukas.sivickas.uninote.database.DBHelper;
import lukas.sivickas.uninote.database.Note;
import lukas.sivickas.uninote.forms.NoteForm;


public class NotesFragment extends Fragment {
    private static final String TAG = "NotesFragment";

    ListView noteView;

    ArrayList<Note> mNotes;
    NoteArrayAdapter mNoteAdapter;
    public static DBHelper mDbHelper;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating new NotesFragments");
        mDbHelper = new DBHelper(getContext());
        mDbHelper.setDataUpdateEventListener(new DBHelper.DataUpdateEventListener() {
            @Override
            public void onDataUpdated(DBHelper.Destination table) {
                Log.d(TAG, "onDataUpdated: got a callback: " + table);
                if (table == DBHelper.Destination.NOTES) {
                    updateNotesDataSet(mDbHelper.getAllNotes());
                }
            }
        });
        mNotes = mDbHelper.getAllNotes();
        mNoteAdapter = new NoteArrayAdapter(super.getContext(), mNotes, NotesFragment.super.getActivity().getFragmentManager());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.mToolbar.setTitle(getString(R.string.title_notes));


        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        noteView = view.findViewById(R.id.lv_notes);
        noteView.setAdapter(mNoteAdapter);
        updateNotesDataSet(mDbHelper.getAllNotes());
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (mDbHelper.getAllModules().size() != 0) {
                    Intent intent = new Intent(getContext(), NoteForm.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this.getContext(), "Add modules, before adding notes", Toast.LENGTH_SHORT).show();
                }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateNotesDataSet(ArrayList<Note> list) {
        if (mNotes != null) {
            if (!mNotes.isEmpty()) {
                mNotes.clear();
            }
            mNotes.addAll(list);
            mNoteAdapter.notifyDataSetChanged();
        }
    }
}
