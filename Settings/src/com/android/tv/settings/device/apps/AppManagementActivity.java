/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.tv.settings.device.apps;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.tv.settings.BaseSettingsFragment;

/**
 * Activity that manages an app.
 */
public class AppManagementActivity extends Activity {

    private static final String TAG = "AppManagementActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final Uri uri = getIntent().getData();
            if (uri == null) {
                Log.wtf(TAG, "No app to inspect (missing data uri in intent)");
                finish();
                return;
            }
            final String packageName = uri.getSchemeSpecificPart();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, SettingsFragment.newInstance(packageName))
                    .commit();
        }

    }

    public static class SettingsFragment extends BaseSettingsFragment {
        private static final String ARG_PACKAGE_NAME = "packageName";

        public static SettingsFragment newInstance(String packageName) {
            final Bundle b = new Bundle(1);
            b.putString(ARG_PACKAGE_NAME, packageName);
            final SettingsFragment f = new SettingsFragment();
            f.setArguments(b);
            return f;
        }

        @Override
        public void onPreferenceStartInitialScreen() {
            final Bundle b = new Bundle();
            AppManagementFragment.prepareArgs(b, getArguments().getString(ARG_PACKAGE_NAME));
            final Fragment f = new AppManagementFragment();
            f.setArguments(b);
            startPreferenceFragment(f);
        }
    }
}
