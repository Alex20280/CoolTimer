package com.example.cooltimes;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.timer_preferences);

    }
}
