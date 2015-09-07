package healerkart.com.dosage.Alpha;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.R;

/**
 * Created by f3n1Xx on 21/08/15.
 */

public class settingsFrag extends PreferenceFragment {
    public settingsFrag()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}


/*
//Fragment
public class settingsFrag extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        updatePreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(key);
    }

    private void updatePreferences() {
        updatePreference(DosageDB.TIME_OPTION);
        updatePreference(DosageDB.DATE_RANGE);
        updatePreference(DosageDB.DATE_FORMAT);
        //updatePreference(DosageDB.RINGTONE_PREF);
    }

    private void updatePreference(String key) {
        Preference pref = findPreference(key);

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
            return;
        }
        if (DosageDB.RINGTONE_PREF.equals(key)) {
            DosageDB objDosageDB = (DosageDB) getActivity().getApplicationContext();
            Uri ringtoneUri = Uri.parse(objDosageDB.getRingtone());
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), ringtoneUri);
            if (ringtone != null) pref.setSummary(ringtone.getTitle(getActivity()));
        }
    }


}*/
