/*
 * Copyright (C) 2018 The LineageOS Project
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

package org.lineageos.settings.camera64;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import java.io.IOException;

import org.lineageos.settings.R;

public class camera64SettingsFragment
        extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.camera64_settings);

        SwitchPreference switchPreference = findPreference("camera64_toggle");
        if (switchPreference != null) {
            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean isCameraEnabled = (Boolean) newValue;
                    if (isCameraEnabled) {
                        enablecam();
                    } else {
                        disablecam();
                    }
                    return true;
                }
            });
        }
    }

    private void enablecam() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] {
                "/system/bin/sh",
                "-c",
                "stop media && " +
                "unlink /vendor/etc/camera/camxoverridesettings.txt && " +
                "unlink /vendor/lib64/hw/com.qti.chi.override.so && " +
                "unlink /vendor/lib64/camera/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin && " +
                "ln /vendor/custom_cam/camxoverridesettings.txt /vendor/etc/camera/camxoverridesettings.txt && " +
                "ln /vendor/custom_cam/com.qti.chi.override.so /vendor/lib64/hw/com.qti.chi.override.so && " +
                "ln /vendor/custom_cam/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin /vendor/lib64/camera/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin && " +
                "sleep 1 && " +
                "start media && " +
                "sleep 1 && " +
                "killall -1 android.hardware.camera.provider@2.4-service_64 && " +
                "pm trim-caches 9999999999999"
            });
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void disablecam() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] {
                "/system/bin/sh",
                "-c",
                "stop media && " +
                "unlink /vendor/etc/camera/camxoverridesettings.txt && " +
                "unlink /vendor/lib64/hw/com.qti.chi.override.so && " +
                "unlink /vendor/lib64/camera/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin && " +
                "ln /vendor/stock_cam/camxoverridesettings.txt /vendor/etc/camera/camxoverridesettings.txt && " +
                "ln /vendor/stock_cam/com.qti.chi.override.so /vendor/lib64/hw/com.qti.chi.override.so && " +
                "ln /vendor/stock_cam/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin /vendor/lib64/camera/com.qti.sensormodule.munch_sunny_ov64b40_wide.bin && " +
                "sleep 1 && " +
                "start media && " +
                "sleep 1 && " +
                "killall -1 android.hardware.camera.provider@2.4-service_64 && " +
                "pm trim-caches 9999999999999"
            });
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

