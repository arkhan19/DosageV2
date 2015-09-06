package healerkart.com.dosage.Alpha;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import healerkart.com.dosage.Delta.Alarm;
import healerkart.com.dosage.Delta.AlarmMsg;
import healerkart.com.dosage.Delta.AlarmService;
import healerkart.com.dosage.Delta.AlarmTime;
import healerkart.com.dosage.Delta.DBHelper;
import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.Delta.Util;
import healerkart.com.dosage.R;


/**
 * Created by f3n1Xx on 29/10/15.
 */
public class dosageFrag extends Fragment{
    private SQLiteDatabase db;
    ///
    private RadioGroup rg;
    ///
    private ViewSwitcher vs;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    ///
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    ///
    private EditText msgEdit;
    private TextView dateT;
    private Button button2;
    private CheckBox soundCb;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView fromdateText;
    private TextView todateText;
    private TextView attimeText;
    ///
    private static final SimpleDateFormat sdf = new SimpleDateFormat();
    ///
    private EditText minsEdit;
    private EditText hoursEdit;
    private EditText daysEdit;
    private EditText monthsEdit;
    private EditText yearsEdit;
    ///
    private static final int DIALOG_FROMDATE = 1;
    private static final int DIALOG_TODATE = 2;
    private static final int DIALOG_ATTIME = 3;
    ///
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    public dosageFrag()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add, container, false);
        //minDateSelector.setCalendarViewShown(false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
    }









//@Normal
    private void findViews() {
        msgEdit = (EditText) getActivity().findViewById(R.id.msg_et);
        soundCb = (CheckBox) getActivity().findViewById(R.id.sound_cb);
        datePicker = (DatePicker) getActivity().findViewById(R.id.datePicker);
        timePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);
        fromdateText = (TextView) getActivity().findViewById(R.id.fromdate_tv);
        todateText = (TextView) getActivity().findViewById(R.id.todate_tv);
        attimeText = (TextView) getActivity().findViewById(R.id.attime_tv);
        vs = (ViewSwitcher) getActivity().findViewById(R.id.view_switcher);
        rg = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        rl3 = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout3);
        rl4 = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout4);
        spinner1 = (Spinner) getActivity().findViewById(R.id.spinner1);
        spinner2 = (Spinner) getActivity().findViewById(R.id.spinner2);
        spinner3 = (Spinner) getActivity().findViewById(R.id.spinner3);

        minsEdit = (EditText) getActivity().findViewById(R.id.mins_et);
        hoursEdit = (EditText) getActivity().findViewById(R.id.hours_et);
        daysEdit = (EditText) getActivity().findViewById(R.id.days_et);
        monthsEdit = (EditText) getActivity().findViewById(R.id.months_et);
        yearsEdit = (EditText) getActivity().findViewById(R.id.years_et);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(msgEdit.getText())) {
            msgEdit.requestFocus();
            Toast.makeText(getActivity(), "Enter a message", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vs.getDisplayedChild() == 1) {
            if (TextUtils.isEmpty(fromdateText.getText())) {
                Toast.makeText(getActivity(), "Specify from date", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(todateText.getText())) {
                Toast.makeText(getActivity(), "Specify to date", Toast.LENGTH_SHORT).show();
                return false;
            }
            try {
                if (sdf.parse(fromdateText.getText().toString()).after(sdf.parse(todateText.getText().toString()))) {
                    Toast.makeText(getActivity(), "From date is after To date!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (ParseException e) {}
            if (TextUtils.isEmpty(attimeText.getText())) {
                Toast.makeText(getActivity(), "Specify time", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}


