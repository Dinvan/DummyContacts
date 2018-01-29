package com.info.dummycontacts;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String TAG = "RemindMe";
    LocalData localData;

    SwitchCompat reminderSwitch;
    TextView tvTime, tv_reminder_freq_desc;

    LinearLayout ll_set_time, ll_set_freq, ll_terms;

    int hour, min;

    ClipboardManager myClipboard;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {

        localData = new LocalData(getActivity());

        myClipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);

        ll_set_time = (LinearLayout) getView().findViewById(R.id.ll_set_time);
        ll_set_freq = (LinearLayout) getView().findViewById(R.id.ll_set_freq);
        ll_terms = (LinearLayout) getView().findViewById(R.id.ll_terms);

        tvTime = (TextView) getView().findViewById(R.id.tv_reminder_time_desc);
        tv_reminder_freq_desc = (TextView) getView().findViewById(R.id.tv_reminder_freq_desc);

        reminderSwitch = (SwitchCompat) getView().findViewById(R.id.timerSwitch);

        hour = localData.get_hour();
        min = localData.get_min();

        tvTime.setText(getFormatedTime(hour, min));
        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
        reminderSwitch.setChecked(localData.getReminderStatus());

        if (!localData.getReminderStatus())
            ll_set_time.setAlpha(0.4f);
        ll_set_freq.setAlpha(0.4f);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: true");
                    NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class, localData.get_hour(), localData.get_min(), localData.getInterval());
                    ll_set_time.setAlpha(1f);
                    ll_set_freq.setAlpha(1f);
                } else {
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(getActivity(), AlarmReceiver.class);
                    ll_set_time.setAlpha(0.4f);
                    ll_set_freq.setAlpha(0.4f);
                }

            }
        });

        ll_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localData.getReminderStatus())
                    showTimePickerDialog(localData.get_hour(), localData.get_min());
            }
        });

        ll_set_freq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localData.getReminderStatus())showContactFreqDialog();
            }
        });


    }

    final CharSequence[] OPTION_ITEMS = {"Every Minute", "Every Day", "Every 2 Day", "Weekly", "Monthly","Cancel"};
    public void showContactFreqDialog() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Frequency");

        builder.setItems(OPTION_ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        localData.setInterval(1 * 60 * 1000);
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        break;
                    case 1:
                        localData.setInterval(AlarmManager.INTERVAL_DAY);
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        break;
                    case 2:
                        localData.setInterval(AlarmManager.INTERVAL_DAY * 2);
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        break;
                    case 3:
                        localData.setInterval(AlarmManager.INTERVAL_DAY * 7);
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        break;
                    case 4:
                        localData.setInterval(AlarmManager.INTERVAL_DAY * 30);
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        break;
                    case 5:
                        dialog.dismiss();
                        break;
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }


    private void showTimePickerDialog(int h, int m) {

        LayoutInflater inflater = getLayoutInflater(getArguments());
        View view = inflater.inflate(R.layout.timepicker_header, null);

        TimePickerDialog builder = new TimePickerDialog(getActivity(), R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Log.d(TAG, "onTimeSet: hour " + hour);
                        Log.d(TAG, "onTimeSet: min " + min);
                        localData.set_hour(hour);
                        localData.set_min(min);
                        tvTime.setText(getFormatedTime(hour, min));
                        tv_reminder_freq_desc.setText(getFormattedInterval(localData.getInterval()));
                        NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class, localData.get_hour(), localData.get_min(), localData.getInterval());
                    }
                }, h, m, false);

        builder.setCustomTitle(view);
        builder.show();

    }

    public String getFormatedTime(int h, int m) {
        final String OLD_FORMAT = "HH:mm";
        final String NEW_FORMAT = "hh:mm a";

        String oldDateString = h + ":" + m;
        String newDateString = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, getCurrentLocale());
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDateString;
    }

    public String getFormattedInterval(long interval){

            if(interval==1 * 60 * 1000){
                return OPTION_ITEMS[0].toString();
            }else    if(interval==AlarmManager.INTERVAL_DAY) {
                return OPTION_ITEMS[1].toString();
            }else    if(interval==AlarmManager.INTERVAL_DAY*2) {
                return OPTION_ITEMS[2].toString();
            }else    if(interval==AlarmManager.INTERVAL_DAY*7) {
                return OPTION_ITEMS[3].toString();
            }else    if(interval==AlarmManager.INTERVAL_DAY*30) {
                return OPTION_ITEMS[4].toString();
            }
        return "";
    }


    @TargetApi(Build.VERSION_CODES.N)
    public Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
    }
}
