package healerkart.com.dosage.Alpha;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import healerkart.com.dosage.Delta.DosageDB;
import healerkart.com.dosage.R;

/**
 * Created by f3n1Xx on 07/09/15.
 */
public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

//	private static final String TAG = "SettingsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        updatePreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(key);
    }

    private void updatePreferences() {
        updatePreference(DosageDB.TIME_OPTION);
        updatePreference(DosageDB.DATE_RANGE);
        updatePreference(DosageDB.DATE_FORMAT);
        updatePreference(DosageDB.RINGTONE_PREF);
    }

    private void updatePreference(String key){
        Preference pref = findPreference(key);

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
            return;
        }

/*		if (pref instanceof EditTextPreference){
			EditTextPreference editTextPreference =  (EditTextPreference) pref;
			if (editTextPreference.getText().trim().length() > 0){
				editTextPreference.setSummary("Entered Name is  " + editTextPreference.getText());
			}else{
				editTextPreference.setSummary("Enter Your Name");
			}
		}*/

        if (DosageDB.RINGTONE_PREF.equals(key)) {
            Uri ringtoneUri = Uri.parse(DosageDB.getRingtone());
            Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
            if (ringtone != null) pref.setSummary(ringtone.getTitle(this));
        }
    }

/*	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

		if ("about_pref".equals(preference.getKey())) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Your Message");
			builder.setPositiveButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// action on dialog close
						}
					});
			builder.show();
		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}*/

}