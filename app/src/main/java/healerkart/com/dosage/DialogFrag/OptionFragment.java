package healerkart.com.dosage.DialogFrag;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import healerkart.com.dosage.Alpha.HomeFrag;
import healerkart.com.dosage.Delta.Alarm;
import healerkart.com.dosage.Delta.AlarmMsg;
import healerkart.com.dosage.Delta.AlarmService;
import healerkart.com.dosage.Delta.DBHelper;
import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.R;


public class OptionFragment extends DialogFragment
    {   private Alarm alarm = new Alarm();
        //private AlarmMsg alarmMsg = new AlarmMsg();
        private SQLiteDatabase db;
        //private static final String TAG = "Option Dialog";


        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState)
        {
            db = DosageDB.db;
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Edit Dosage")
                    .setView(getActivity().getLayoutInflater().inflate(R.layout.edit, null))
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Dialog d = (Dialog) dialog;
                            EditText msgEdit = (EditText) d.findViewById(R.id.msg_et);
                            CheckBox soundCb = (CheckBox) d.findViewById(R.id.sound_cb);

                            alarm.setSound(soundCb.isChecked());

                            if (!TextUtils.isEmpty(msgEdit.getText())) {
                                alarm.setName(msgEdit.getText().toString());
                                alarm.persist(db);

                                Toast.makeText(getActivity(), "Dosage Edited", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Enter a message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setIcon(R.drawable.option_icon)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .create();

        }

    }