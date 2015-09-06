package healerkart.com.dosage.Alpha;

import android.view.View.OnClickListener;
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


    public dosageFrag()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent firstpage= new Intent(getActivity(),AddDosage.class);
        getActivity().startActivity(firstpage);
        super.onCreate(savedInstanceState);
    }
}


