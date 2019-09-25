package com.rtikcirebonkota.thecataloguemovie.utils;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.notification.DailyReceiver;
import com.rtikcirebonkota.thecataloguemovie.notification.ReleaseReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.change_setting));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
        DailyReceiver dailyReceiver = new DailyReceiver();
        ReleaseReceiver releaseReceiver = new ReleaseReceiver();
        List<MovieResult> movieList;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            movieList = new ArrayList<>();
            SwitchPreference switchReminder = findPreference(getString(R.string.key_today_reminder));
            switchReminder.setOnPreferenceChangeListener(this);
            SwitchPreference switchToday = findPreference(getString(R.string.key_release_reminder));
            switchToday.setOnPreferenceChangeListener(this);
            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean b = (boolean) newValue;

            if(key.equals(getString(R.string.key_today_reminder))){
                if(b){
                    dailyReceiver.setAlarm(getActivity());
                    Toast.makeText(getActivity(),"On",Toast.LENGTH_SHORT).show();
                }else{
                
                    dailyReceiver.cancelAlarm(getActivity());
                    Toast.makeText(getActivity(),"Off",Toast.LENGTH_SHORT).show();
                }
            }

            if(key.equals(getString(R.string.key_release_reminder))){
                if(b){
                    releaseReceiver.setAlarm(getActivity());
                    Toast.makeText(getActivity(),"On",Toast.LENGTH_SHORT).show();
                }else{
                    releaseReceiver.cancelAlarm(getActivity());
                    Toast.makeText(getActivity(),"Off",Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }

    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}