package lukas.sivickas.uninote.adapters;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lukas.sivickas.uninote.ModulesFragment;
import lukas.sivickas.uninote.NotesFragment;
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.database.Module;
import lukas.sivickas.uninote.database.Note;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.forms.NoteForm;

public class NoteArrayAdapter extends ArrayAdapter<Note> {

    private static final String TAG = "ModuleArrayAdapter";

    FragmentManager fragmentManager;

    public NoteArrayAdapter(Context context, ArrayList<Note> list) {
        super(context, 0, list);
    }

    public NoteArrayAdapter(Context context, ArrayList<Note> list, FragmentManager manager) {
        super(context, 0, list);
        fragmentManager = manager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Note item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.note_item, parent, false);
        }

        TextView id = convertView.findViewById(R.id.tv_note_id);
        TextView crd = convertView.findViewById(R.id.tv_note_crd);
        TextView led = convertView.findViewById(R.id.tv_note_led);
        TextView title = convertView.findViewById(R.id.tv_note_title);
        TextView text = convertView.findViewById(R.id.tv_note_text);
        TextView module = convertView.findViewById(R.id.tv_note_module);

        id.setText("ID: " + item.getId());
        crd.setText("Creation date: " + item.getCreationDate().toString());
        led.setText("Last edit: " + item.getCreationDate().toString());
        title.setText("Title: " + item.getTitle());
        text.setText("Text: " + item.getText());
        module.setText("Module: " + item.getModule().getName() + " (" + item.getModule().getCode() + ")");


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Choose an action");
                // set dialog message
                alertDialogBuilder.setItems(R.array.EditDeleteChoices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            // Edit
                            case 0:
                                Intent intent = new Intent(NoteArrayAdapter.super.getContext(), NoteForm.class);
                                intent.putExtra("editing", true);
                                intent.putExtra("id", item.getId());
                                NoteArrayAdapter.super.getContext().startActivity(intent);
                                break;
                            // Delete
                            case 1:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                // set title
                                alertDialogBuilder.setTitle("Are you sure?");
                                // set dialog message
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        NotesFragment.mDbHelper.deleteNote(item.getId());
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });

        return convertView;
    }
}