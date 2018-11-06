package lukas.sivickas.uninote.adapters;

import android.widget.ArrayAdapter;
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

import lukas.sivickas.uninote.AssignmentsFragment;
import lukas.sivickas.uninote.ModulesFragment;
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.Tools;
import lukas.sivickas.uninote.forms.AssignmentForm;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.database.Module;
import lukas.sivickas.uninote.database.Assignment;

public class AssignmentArrayAdapter extends ArrayAdapter<Assignment> {
    private static final String TAG = "ModuleArrayAdapter";

    FragmentManager fragmentManager;

    public AssignmentArrayAdapter(Context context, ArrayList<Assignment> list) {
        super(context, 0, list);
    }

    public AssignmentArrayAdapter(Context context, ArrayList<Assignment> list, FragmentManager manager) {
        super(context, 0, list);
        fragmentManager = manager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Assignment item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.assignment_item, parent, false);
        }

        TextView module = convertView.findViewById(R.id.tv_assig_item_module);
        TextView dueDate = convertView.findViewById(R.id.tv_assig_item_due_date);
        TextView title = convertView.findViewById(R.id.tv_assig_item_title);
        TextView desc = convertView.findViewById(R.id.tv_assig_item_desc);

        module.setText(item.getModuleDesc());
        dueDate.setText(Tools.convertToString(item.getDueDate()));
        title.setText(item.getTitle());
        desc.setText(item.getDescription());


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
                                Intent intent = new Intent(AssignmentArrayAdapter.super.getContext(), AssignmentForm.class);
                                intent.putExtra("editing", true);
                                intent.putExtra("id", item.getId());
                                AssignmentArrayAdapter.super.getContext().startActivity(intent);
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
                                        AssignmentsFragment.mDbHelper.deleteAssignment(item.getId());
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
