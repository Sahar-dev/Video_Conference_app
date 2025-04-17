package com.android.avempace.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.avempace.R;
import com.android.avempace.models.UserModel;
import com.android.avempace.callbacks.UserCallBacks;
import com.android.avempace.service.UserService;
import com.android.avempace.utilities.EventNotifier;
import com.android.avempace.utilities.MyUtils;
import com.android.avempace.utilities.SharedPreferenceHelper;
import com.android.avempace.views.fragments.CallFragment;
import com.android.avempace.views.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.BroadcastIntentHelper;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity{
    private UserService userService;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        sharedPreferenceHelper = new SharedPreferenceHelper();
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        changeFragment(homeFragment);
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        if(!(fragment instanceof HomeFragment))
            transaction.addToBackStack(null);
        transaction.commit();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventNotifier event) {
        showDialog(event.getRoomName(), event.getUsername());
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void showDialog(String roomName, String userName){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.primary));
        pDialog.setTitleText("");
        pDialog.setContentText("You received a call from " + userName);
        pDialog.setCustomImage(R.drawable.call);
        pDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
        pDialog.setCancelable(false);
        pDialog.setContentText("Accept");
        pDialog.setCancelText("Decline");
        pDialog.setConfirmClickListener(sDialog -> {
            changeFragment(CallFragment.newInstance(roomName));
            sDialog.dismissWithAnimation();
        });
        pDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            if(homeFragment.getSelectionOn()){
                homeFragment.removeSelection();
            }
            else
                super.onBackPressed();
        }
    }
}