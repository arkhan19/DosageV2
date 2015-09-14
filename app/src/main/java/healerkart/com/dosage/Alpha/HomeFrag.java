package healerkart.com.dosage.Alpha;
//Learn another method that StartManagingCursor to manage Cursor to keep it off the UI thread. Else just use it and give the final version by sunday.
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import healerkart.com.dosage.Delta.Alarm;
import healerkart.com.dosage.Delta.AlarmMsg;
import healerkart.com.dosage.Delta.AlarmService;
import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.Delta.Util;
import healerkart.com.dosage.DialogFrag.OptionFragment;
import healerkart.com.dosage.R;
//Use Alarm Manager


public class HomeFrag extends ListFragment {
    //View Attributes
    private TextView range;
    private ImageButton prev;
    private ImageButton next;
    //For Context Attributes
    private Alarm alarm = new Alarm();
    private AlarmMsg alarmMsg = new AlarmMsg();

    // Attributes
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SQLiteDatabase db;


    // Elements
    private ListView mListView;
    private SimpleCursorAdapter mListAdapter;

    //Range Attributes
    public final Calendar cal = Calendar.getInstance();
    public final Date dt = new Date();
    private String[] monthArr;

    public HomeFrag()
    {   //System.out.println("I am in dosageFrag");
        db= DosageDB.db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        int r =DosageDB.getDateRange();

        switch(r) {
            case 3: // Yearly
                cal.set(Calendar.MONTH, 0);

            case 2: // Monthly
                cal.set(Calendar.DATE, 1);

            case 1: // Weekly
                if (r==1) cal.set(Calendar.DATE, cal.getFirstDayOfWeek());

            case 0: // Daily
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set our attributes
        mContext = getActivity();
        //mListView = getListView();
        mLayoutInflater = inflater;
        //For Range
        monthArr = getResources().getStringArray(R.array.spinner3_arr);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        // Init
        init(view);
        findview(view);
        range.setText(getRangeStr());

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(-1);
                range.setText(getRangeStr());
                ((SimpleCursorAdapter)getListAdapter()).changeCursor(createCursor());
                //Toast.makeText(getActivity(), "Previous Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(+1);
                range.setText(getRangeStr());
                ((SimpleCursorAdapter)getListAdapter()).changeCursor(createCursor());
                //Toast.makeText(getActivity(), "Next Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        // Get the database handler
        db =  DosageDB.db;



        return view;


    }

    public void init(View v) {

        // Setup the listAdapter
        mListAdapter = new SimpleCursorAdapter(

                mContext,
                R.layout.row,
                createCursor(),
                new String[]{Alarm.COL_NAME, AlarmMsg.COL_DATETIME, AlarmMsg.COL_DATETIME, AlarmMsg.COL_DATETIME, AlarmMsg.COL_DATETIME},
                new int[] { R.id.msg_tv, R.id.year_tv, R.id.month_tv, R.id.date_tv, R.id.time_tv}
        );
        //mListView.setAdapter(mListAdapter);
        mListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.msg_tv) return false;

                TextView tv = (TextView) view;
                long time = cursor.getLong(columnIndex);
                dt.setTime(time);
                switch (view.getId()) {
                    case R.id.year_tv:
                        tv.setText(String.valueOf(dt.getYear() + 1900));
                        break;
                    case R.id.month_tv:
                        tv.setText(monthArr[dt.getMonth() + 1]);
                        break;
                    case R.id.date_tv:
                        tv.setText(String.valueOf(dt.getDate()));
                        break;
                    case R.id.time_tv:
                        long now = System.currentTimeMillis();
                        String txt = DosageDB.showRemainingTime() ? Util.getRemainingTime(time, now) : Util.getActualTime(dt.getHours(), dt.getMinutes());
                        if (TextUtils.isEmpty(txt))
                            txt = Util.getActualTime(dt.getHours(), dt.getMinutes());
                        tv.setText(txt);

                        RelativeLayout parent = (RelativeLayout) tv.getParent();
                        TextView tv2 = (TextView) parent.findViewById(R.id.msg_tv);
                        if (time < now) tv2.setTextColor(Color.parseColor("#555555"));
                        else tv2.setTextColor(Color.parseColor("#587498"));
                        break;
                }
                return true;
            }
        });
        setListAdapter(mListAdapter);


    }
    public void findview(View v)
    {
        range = (TextView)v.findViewById(R.id.range_tv);
        prev = (ImageButton) v.findViewById(R.id.imageButton);
        next = (ImageButton)v.findViewById(R.id.imageButton2);

    }

    private String getRangeStr() {
        int date = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        dt.setTime(System.currentTimeMillis());

        switch(DosageDB.getDateRange()) {
            case 0: // Daily
                if (date==dt.getDate() && month==dt.getMonth() && year==dt.getYear()+1900) return "Today";
                else return date+" "+monthArr[month+1];

            case 1: // Weekly
                return date+" "+monthArr[month+1] + move(+1) + " - " + cal.get(Calendar.DATE)+" "+monthArr[cal.get(Calendar.MONTH)+1] + move(-1);

            case 2: // Monthly
                return monthArr[month+1]+" "+year;

            case 3: // Yearly
                return year+"";
        }
        return null;
    }
    private Cursor createCursor() {
        Cursor c = DosageDB.dbHelper.listNotifications(db, cal.getTimeInMillis() + move(+1), cal.getTimeInMillis() + move(-1));
        getActivity().startManagingCursor(c);
        return c;
    }
    private String move(int step) {
        switch(DosageDB.getDateRange()) {
            case 0:
                cal.add(Calendar.DATE, 1 * step);
                break;
            case 1:
                cal.add(Calendar.DATE, 7 * step);
                break;
            case 2:
                cal.add(Calendar.MONTH, 1 * step);
                break;
            case 3:
                cal.add(Calendar.YEAR, 1 * step);
                break;
        }
        return "";
    }

    @Override
    public void onListItemClick(final ListView l, View v, final int pos, long id) {

        registerForContextMenu(l);
        //code to get the listView instance using findViewByID etc
        /*v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getActivity().getApplicationContext(v);
                //OptionFragment opt = new OptionFragment();
                //opt.show(getFragmentManager(), "Option");
                //Toast.makeText(getActivity(), "At Time", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == android.R.id.list) {
            getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle("Choose an Option");
            menu.setHeaderIcon(R.drawable.option_icon);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            alarmMsg.setId(info.id);
            alarmMsg.load(db);
            if (alarmMsg.getDateTime() < System.currentTimeMillis())
                menu.removeItem(R.id.menu_edit);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        boolean refresh = false;

        switch (item.getItemId()) {
            case R.id.menu_edit:
                alarmMsg.setId(info.id);
                alarmMsg.load(db);
                alarm.reset();
                alarm.setId(alarmMsg.getAlarmId());
                alarm.load(db);
                OptionFragment opt = new OptionFragment();
                opt.show(getFragmentManager(), "Option");

                //SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
                //adapter.getCursor().requery();
                //adapter.notifyDataSetChanged();
                break;

            case R.id.menu_delete:
                DosageDB.dbHelper.cancelNotification(db, info.id, false);
                refresh = true;

                Intent cancelThis = new Intent(getActivity(), AlarmService.class);
                cancelThis.putExtra(AlarmMsg.COL_ID, String.valueOf(info.id));
                cancelThis.setAction(AlarmService.CANCEL);
                getActivity().startService(cancelThis);
                break;

            case R.id.menu_delete_repeating:
                alarmMsg.setId(info.id);
                alarmMsg.load(db);
                DosageDB.dbHelper.cancelNotification(db, alarmMsg.getAlarmId(), true);
                refresh = true;

                Intent cancelRepeating = new Intent(getActivity(), AlarmService.class);
                cancelRepeating.putExtra(AlarmMsg.COL_ALARMID, String.valueOf(alarmMsg.getAlarmId()));
                cancelRepeating.setAction(AlarmService.CANCEL);
                getActivity().startService(cancelRepeating);
                break;
        }

        if (refresh) {
            SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
            adapter.getCursor().requery();
            adapter.notifyDataSetChanged();
        }

        return true;
    }



}
