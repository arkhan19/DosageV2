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
import android.widget.ImageButton;
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


public class HomeFrag extends Fragment {
    public static SQLiteDatabase db;


    private TextView range;
    private ImageButton prev;
    private ImageButton next;

    public HomeFrag()
    {   //System.out.println("I am in dosageFrag");
        //db= DosageDB.db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        findview(view);


        return view;


    }









    public void findview(View v)
    {
        range = (TextView)v.findViewById(R.id.range_tv);
        prev = (ImageButton) v.findViewById(R.id.imageButton);
        next = (ImageButton)v.findViewById(R.id.imageButton2);

    }

}
