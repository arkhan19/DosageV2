package healerkart.com.dosage.DialogFrag;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import healerkart.com.dosage.Delta.DBHelper;
import healerkart.com.dosage.R;


public class PickerDialogFrag3 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    TheListener listener;


    public interface TheListener{
        void returnDate(String date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        listener = (TheListener) getView();


// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = sdf.format(c.getTime());
        String txt = DBHelper.getDateStr(year, month + 1, day);
        ((TextView) getActivity().findViewById(R.id.todate_tv)).setText(txt);


        if (listener != null)
        {
            listener.returnDate(formattedDate);

        }
        //return new DatePickerDialog(this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
    }


}