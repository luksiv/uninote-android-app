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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.helpers.DBHelper;
import lukas.sivickas.uninote.helpers.Module;

public class ModuleArrayAdapter extends ArrayAdapter<Module> {

    private static final String TAG = "ModuleArrayAdapter";

    FragmentManager fragmentManager;

    public ModuleArrayAdapter(Context context, ArrayList<Module> list) {
        super(context, 0, list);
    }

    public ModuleArrayAdapter(Context context, ArrayList<Module> list, FragmentManager manager) {
        super(context, 0, list);
        fragmentManager = manager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Module item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.modules_item, parent, false);
        }

        TextView id = convertView.findViewById(R.id.tv_id);
        TextView name = convertView.findViewById(R.id.tv_name);
        TextView code = convertView.findViewById(R.id.tv_code);
        TextView lead = convertView.findViewById(R.id.tv_lead);

        id.setText("ID: " + item.getId());
        name.setText("Name: " + item.getName());
        code.setText("Code: " + item.getCode());
        lead.setText("Lead: " + item.getLead());

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
                                Intent intent = new Intent(ModuleArrayAdapter.super.getContext(), ModuleForm.class);
                                intent.putExtra("editing", true);
                                intent.putExtra("id", item.getId());
                                ModuleArrayAdapter.super.getContext().startActivity(intent);
                                break;
                            // Delete
                            case 1:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                // set title
                                alertDialogBuilder.setTitle("Are you sure?");
                                alertDialogBuilder.setMessage("It will delete all notes and assignments of this module");
                                // set dialog message
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        (new DBHelper(ModuleArrayAdapter.super.getContext())).deleteModule(item.getId());
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
