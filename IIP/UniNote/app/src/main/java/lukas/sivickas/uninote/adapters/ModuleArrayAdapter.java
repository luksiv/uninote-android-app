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
import lukas.sivickas.uninote.R;
import lukas.sivickas.uninote.forms.ModuleForm;
import lukas.sivickas.uninote.database.Module;

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

        TextView id = convertView.findViewById(R.id.tv_module_item_id);
        TextView name = convertView.findViewById(R.id.tv_module_item_name);
        TextView code = convertView.findViewById(R.id.tv_module_lv_item_code);
        TextView lead = convertView.findViewById(R.id.tv_module_lv_item_lead);
        final ImageView mExpandButton = convertView.findViewById(R.id.btn_module_detail_expand);

        id.setText("ID: " + item.getId());
        name.setText(item.getName());
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
                                        ModulesFragment.mDbHelper.deleteModule(item.getId());
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

        final View finalConvertView = convertView;
        mExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout toolbar = finalConvertView.findViewById(R.id.ll_module_lv_item_details);
                if(toolbar.getVisibility() == View.GONE){
                    mExpandButton.setImageResource(R.drawable.ic_expand_less);
                    expand(toolbar);
                } else {
                    mExpandButton.setImageResource(R.drawable.ic_expand_more);
                    collapse(toolbar);
                }
            }
        });

        return convertView;
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density)*2);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)*2);
        v.startAnimation(a);
    }

}
