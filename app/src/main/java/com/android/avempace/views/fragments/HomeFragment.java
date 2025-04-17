package com.android.avempace.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.android.avempace.R;
import com.android.avempace.adapters.UsersAdapter;
import com.android.avempace.callbacks.CallCallBacks;
import com.android.avempace.callbacks.RecyclerCallBack;
import com.android.avempace.models.UserModel;
import com.android.avempace.callbacks.UserCallBacks;
import com.android.avempace.service.CallService;
import com.android.avempace.service.UserService;
import com.android.avempace.utilities.MyUtils;
import com.android.avempace.utilities.SharedPreferenceHelper;
import com.android.avempace.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import timber.log.Timber;

public class HomeFragment extends Fragment implements UserCallBacks, RecyclerCallBack, CallCallBacks{
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private UserService service;
    private CallService callService;
    private ArrayList<UserModel> selectedUsers;
    private Button startCallButton;
    private boolean isGroupCall = false;
    private UserModel clickedUserModel;
    SharedPreferenceHelper sharedPreferenceHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        service = new UserService(this);
        callService = new CallService(this);
        sharedPreferenceHelper = new SharedPreferenceHelper();
        selectedUsers = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.users_recycler);
        startCallButton = view.findViewById(R.id.btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        service.getAllUsers();

        startCallButton.setOnClickListener(v -> {
            isGroupCall = true;
            service.getRandomRoomName();
        });

    }

    @Override
    public void getAllUsersSuccess(List<UserModel> list) {
        UserModel currentUser = new UserModel();
        for(UserModel u: list){
            if(u.getUsername().equals(sharedPreferenceHelper.getString(MyUtils.USERNAME_KEY)));
            {
                currentUser = u;
                break;
            }
        }
        list.remove(currentUser);
        adapter = new UsersAdapter(list);
        adapter.setRecyclerCallBack(this);
        recyclerView.setAdapter(adapter);
        Log.d("usersList", list.toString());
    }


    private void showDialog(String roomName){
        MainActivity activity = (MainActivity) requireActivity() ;
        String message =isGroupCall ? "Start a group call ?" : "Start a call with "+clickedUserModel.getUsername()+"?";
        SweetAlertDialog pDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(requireContext(), R.color.primary));
        pDialog.setTitleText("");
        pDialog.setContentText(message);
        pDialog.setCustomImage(R.drawable.call);
        pDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
        pDialog.setCancelable(false);
        pDialog.setConfirmText("Yes");
        pDialog.setCancelText("No");
        pDialog.setConfirmClickListener(sDialog -> {
            if(isGroupCall){
                List<String> tokensList = new ArrayList<>();
                for(UserModel user: selectedUsers){
                    tokensList.add(user.getFireBaseToken());
                }
                callService.sendGroupCallRequest(roomName, tokensList);
            }
            else{
                callService.sendSingleCallRequest(clickedUserModel.getFireBaseToken(), roomName);
            }
            activity.changeFragment(CallFragment.newInstance(roomName));
            sDialog.dismissWithAnimation();
        });
        pDialog.show();
    }
    @Override
    public void getAllUsersFailure(String error) {

    }

    @Override
    public void getRoomNameSuccess(String roomName) {
        showDialog(roomName);
    }
    @Override
    public void getRoomNameFailure(String error) {

    }
    @Override
    public void onUserClicked(UserModel user, int position) {
        Timber.d("User is : %s", user);
        if(user.isMultipleSelection()){
            if (user.isSelected()) {
                addSelectedUser(user);
            } else
                selectedUsers.remove(user);
        }
        else {
            animateButton(false);
        }
    }

    @Override
    public void onUserLongClick(UserModel user, int position) {
        Timber.d("User is : %s", user);
        if(user.isMultipleSelection()){
            if (user.isSelected()) {
                addSelectedUser(user);
            } else
                selectedUsers.remove(user);
        }
        animateButton(true);
    }

    @Override
    public void onSingleCallClicked(UserModel user, int position) {
        isGroupCall = false;
        clickedUserModel = user;
        service.getRandomRoomName();

    }

    private void addSelectedUser(UserModel user){
        boolean userExist = false;
       for(UserModel userFromList: selectedUsers){
           if(userFromList.getUsername().equals(user.getUsername())) {
               userExist = true;
               break;
           }
       }
       if(!userExist){
           selectedUsers.add(user);
       }
    }
    void animateButton(boolean appear){
        if(appear){
            Animation slideIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left);
            startCallButton.startAnimation(slideIn);
            startCallButton.setVisibility(View.VISIBLE);
        }
        else{
            Animation slideOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left);
            startCallButton.startAnimation(slideOut);
            startCallButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void sendSingleCallSuccess() {

    }

    @Override
    public void sendSingleCallFailure(String error) {

    }

    @Override
    public void sendGroupCallSuccess() {

    }

    @Override
    public void sendGroupCallFailure(String error) {

    }

    public void removeSelection(){
        adapter.multipleSelection(false, 0);
        animateButton(false);
        adapter.notifyDataSetChanged();
    }

    public boolean getSelectionOn(){
        return adapter.selectionOn;
    }

}