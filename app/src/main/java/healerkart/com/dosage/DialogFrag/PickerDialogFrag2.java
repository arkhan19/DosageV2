package healerkart.com.dosage.DialogFrag;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

import healerkart.com.dosage.Delta.DBHelper;
import healerkart.com.dosage.R;


public class PickerDialogFrag2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener{



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(hourOfDay, minute);

        String txt = DBHelper.getTimeStr(hourOfDay, minute);
        ((TextView) getActivity().findViewById(R.id.attime_tv)).setText(txt);


    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);



// Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }
}