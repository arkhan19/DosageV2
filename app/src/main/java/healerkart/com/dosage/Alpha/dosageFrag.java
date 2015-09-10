package healerkart.com.dosage.Alpha;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import healerkart.com.dosage.Delta.Alarm;
import healerkart.com.dosage.Delta.AlarmMsg;
import healerkart.com.dosage.Delta.AlarmService;
import healerkart.com.dosage.Delta.AlarmTime;
import healerkart.com.dosage.Delta.DBHelper;
import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.Delta.Util;
import healerkart.com.dosage.DialogFrag.PickerDialogFrag;
import healerkart.com.dosage.DialogFrag.PickerDialogFrag2;
import healerkart.com.dosage.DialogFrag.PickerDialogFrag3;
import healerkart.com.dosage.R;
//Use Alarm Manager


public class dosageFrag extends Fragment implements AdapterView.OnItemClickListener, PickerDialogFrag.TheListener{
    public static SQLiteDatabase db;


    Button tb;
    ViewSwitcher vs;
    EditText msgEdit;
    private TextView fromdateText;
    private TextView todateText;
    private TextView attimeText;
    private TextView fromdatelb;
    private TextView todatelb;
    private TextView attimelb;
    private CheckBox soundCb;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private RadioGroup rg;
    private EditText minsEdit;
    private EditText hoursEdit;
    private EditText daysEdit;
    private EditText monthsEdit;
    private EditText yearsEdit;

    private RelativeLayout rl3;
    private RelativeLayout rl4;

    private static final int DIALOG_FROMDATE = 1;
    private static final int DIALOG_TODATE = 2;
    private static final int DIALOG_ATTIME = 3;

    static final SimpleDateFormat sdf = new SimpleDateFormat();

    public dosageFrag()
    {   //System.out.println("I am in dosageFrag");
        //db= DosageDB.db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= DosageDB.db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add, container, false);
        findview(view);

        //For Once and Repeat
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vs.showNext();
            }
        });
        //Add Button
        final Button add = (Button) view.findViewById(R.id.button);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (!validate()) return;
                Alarm alarm = new Alarm();
                alarm.setName(msgEdit.getText().toString());
                alarm.setSound(soundCb.isChecked());
                AlarmTime alarmTime = new AlarmTime();
                long alarmId = 0;
                //int x = vs.getDisplayedChild();


                switch(vs.getDisplayedChild()) {
                    case 0: //one time
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        int day = datePicker.getDayOfMonth();
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();

                        //Toast.makeText(getActivity(), "Date : " + year + month + day, Toast.LENGTH_SHORT).show();
                        alarm.setFromDate(DBHelper.getDateStr(year, month, day));
                        alarmTime.setAt(DBHelper.getTimeStr(hour, minute));


                        alarmId = alarm.persist(DosageDB.db);
                        alarmTime.setAlarmId(alarmId);
                        alarmTime.persist(db);
                        Toast.makeText(getActivity(), "Single Dosage Added", Toast.LENGTH_SHORT).show();
                        break;

                    case 1: //repeating
                        alarm.setFromDate(Util.toPersistentDate(fromdateText.getText().toString(), sdf));
                        alarm.setToDate(Util.toPersistentDate(todateText.getText().toString(), sdf));
                        switch(rg.getCheckedRadioButtonId()) {
                            case R.id.radio0: //rule
                                alarm.setRule(Util.concat(spinner1.getSelectedItemPosition(), " ",
                                        spinner2.getSelectedItemPosition(), " ",
                                        spinner3.getSelectedItemPosition()));
                                break;
                            case R.id.radio1: //interval
                                alarm.setInterval(Util.concat(minsEdit.getText(), " ",
                                        hoursEdit.getText(), " ",
                                        daysEdit.getText(), " ",
                                        monthsEdit.getText(), " ",
                                        yearsEdit.getText()));
                                break;
                        }
                        alarmId = alarm.persist(db);

                        alarmTime.setAt(Util.toPersistentTime(attimeText.getText().toString()));
                        alarmTime.setAlarmId(alarmId);
                        alarmTime.persist(db);
                        Toast.makeText(getActivity(), "A Repeating Dosage Has been Added", Toast.LENGTH_SHORT).show();
                        break;
                }
                Intent service = new Intent(getActivity(), AlarmService.class);
                service.putExtra(AlarmMsg.COL_ALARMID, String.valueOf(alarmId));
                service.setAction(AlarmService.POPULATE);
                getActivity().startService(service);
                getActivity().finish();


            }
        });

        fromdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogFrag picker = new PickerDialogFrag();
                picker.show(getFragmentManager(), "From Date");
            }
        });

        todateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogFrag3 picker = new PickerDialogFrag3();
                picker.show(getFragmentManager(), "To Date");
            }

        });

        attimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogFrag2 picker = new PickerDialogFrag2();
                picker.show(getFragmentManager(), "At Time");
                //Toast.makeText(getActivity(), "At Time", Toast.LENGTH_SHORT).show();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        rl3.setVisibility(View.VISIBLE);
                        rl4.setVisibility(View.GONE);
                        break;
                    case R.id.radio1:
                        rl4.setVisibility(View.VISIBLE);
                        rl3.setVisibility(View.GONE);
                        break;
                }
            }
        });

        return view;


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }






    public void findview(View v)
    {
        tb = (Button)v.findViewById(R.id.toggleButton1);
        vs = (ViewSwitcher) v.findViewById(R.id.view_switcher);
        msgEdit = (EditText)v.findViewById(R.id.msg_et);
        fromdateText = (TextView)v.findViewById(R.id.fromdate_tv);
        todateText = (TextView)v.findViewById(R.id.todate_tv);
        attimeText = (TextView)v.findViewById(R.id.attime_tv);
        fromdatelb = (TextView)v.findViewById(R.id.fromdate_lb);
        todatelb = (TextView)v.findViewById(R.id.todate_lb);
        attimelb = (TextView)v.findViewById(R.id.attime_lb);
        soundCb = (CheckBox) v.findViewById(R.id.sound_cb);
        datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        rg = (RadioGroup) v.findViewById(R.id.radioGroup);
        spinner1 = (Spinner)v.findViewById(R.id.spinner1);
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        spinner3 = (Spinner) v.findViewById(R.id.spinner3);
        minsEdit = (EditText) v.findViewById(R.id.mins_et);
        hoursEdit = (EditText) v.findViewById(R.id.hours_et);
        daysEdit = (EditText) v.findViewById(R.id.days_et);
        monthsEdit = (EditText) v.findViewById(R.id.months_et);
        yearsEdit = (EditText) v.findViewById(R.id.years_et);
        rl3 = (RelativeLayout) v.findViewById(R.id.relativeLayout3);
        rl4 = (RelativeLayout) v.findViewById(R.id.relativeLayout4);

        //final EditText msgEdit = (EditText) getActivity().findViewById(R.id.msg_et);
    }

    public boolean validate() {
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

    @Override
    public void returnDate(String date) {
        Toast.makeText(getActivity(), "Specify time" + date, Toast.LENGTH_SHORT).show();
        fromdatelb.setText(date);
    }
}
