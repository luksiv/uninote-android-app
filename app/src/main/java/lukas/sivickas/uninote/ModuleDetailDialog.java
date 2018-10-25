package lukas.sivickas.uninote;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class ModuleDetailDialog {
    public void showDialog(Context context, String msg) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();

        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.modules_item_detailed);

//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        text.setText(msg);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });


        dialog.show();

    }
}
