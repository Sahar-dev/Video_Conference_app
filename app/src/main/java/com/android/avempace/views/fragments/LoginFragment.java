package com.android.avempace.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.avempace.R;
import com.android.avempace.models.UserModel;
import com.android.avempace.models.LoginRequest;
import com.android.avempace.callbacks.AuthResponseCallBacks;
import com.android.avempace.service.AuthService;
import com.android.avempace.views.LoginActivity;
import com.android.avempace.views.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import timber.log.Timber;

public class LoginFragment extends Fragment implements AuthResponseCallBacks {
    private TextInputEditText usernameText, passwordText;
    private AuthService authService;
    ProgressBar progressBar;
    TextView loginText;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authService = new AuthService(this);
        usernameText = view.findViewById(R.id.username_text);
        passwordText = view.findViewById(R.id.password_text);
        RelativeLayout loginButton = view.findViewById(R.id.login_button);
        LinearLayout signUpButton = view.findViewById(R.id.signup_layout);
        progressBar = view.findViewById(R.id.progress);
        loginText = view.findViewById(R.id.login_text);

        loginButton.setOnClickListener(v -> {
            if( usernameText.getText() != null && passwordText.getText() != null) {
                progressBar.setVisibility(View.VISIBLE);
                loginText.setVisibility(View.GONE);
                LoginRequest loginRequest = new LoginRequest(usernameText.getText().toString(), passwordText.getText().toString());
                authService.login(loginRequest);
            }
        });
        signUpButton.setOnClickListener(v -> {
            LoginActivity activity = (LoginActivity) requireActivity();
            activity.changeFragment(new RegisterFragment());
        });

    }


    @Override
        public void onLoginSuccess(UserModel userModel) {
            progressBar.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
        loginText.setText("Success");
        Timber.d("LoginModel");
        Intent intent = new Intent(requireContext(), MainActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onLoginFailure(String error) {
        progressBar.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.GONE);
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onRegisterFailure(String error) {

    }
}