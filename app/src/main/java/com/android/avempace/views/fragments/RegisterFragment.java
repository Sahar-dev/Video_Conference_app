package com.android.avempace.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.avempace.R;
import com.android.avempace.models.UserModel;
import com.android.avempace.models.RegisterRequest;
import com.android.avempace.callbacks.AuthResponseCallBacks;
import com.android.avempace.service.AuthService;
import com.android.avempace.utilities.SharedPreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment implements AuthResponseCallBacks {

    private boolean passwordMatch = false;
    private TextInputEditText passwordText;
    private TextInputEditText confirmPassWordText;
    private TextInputEditText emailText;
    private TextInputEditText usernameText;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ProgressBar progressBar;
    private TextView registerText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferenceHelper = new SharedPreferenceHelper();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthService authService = new AuthService(this);
        usernameText = view.findViewById(R.id.username_text);
        emailText = view.findViewById(R.id.email_text);
        TextInputLayout emailLayout = view.findViewById(R.id.email_layout);
        TextInputLayout usernameLayout = view.findViewById(R.id.username_layout);
        passwordText = view.findViewById(R.id.password_text);
        confirmPassWordText = view.findViewById(R.id.confirm_password_text);
        registerText = view.findViewById(R.id.register_text);
        progressBar = view.findViewById(R.id.progress);
        TextInputLayout passWordLayout = view.findViewById(R.id.password_layout);
        TextInputLayout confirmPassWordLayout = view.findViewById(R.id.confirm_password_layout);
        LinearLayout signInButton = view.findViewById(R.id.signup_layout);
        RelativeLayout registerButton = view.findViewById(R.id.register_button);

        usernameText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    usernameLayout.setErrorEnabled(true);
                    usernameText.setError("Should not be empty !");
                } else {
                    usernameLayout.setErrorEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        confirmPassWordText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String password = passwordText.getText().toString();
                if (!s.toString().equals(password)) {
                    confirmPassWordLayout.setErrorEnabled(true);
                    confirmPassWordText.setError("Password doesn't match");
                } else {
                    confirmPassWordLayout.setErrorEnabled(false);
                    passwordMatch = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String confirmPassword = confirmPassWordText.getText().toString();
                boolean confirmEmpty = confirmPassword.isEmpty();
                if (!validPassword() && !confirmEmpty) {
                    confirmPassWordLayout.setErrorEnabled(true);
                    confirmPassWordText.setError("Password doesn't match");
                } else {
                    confirmPassWordLayout.setErrorEnabled(false);
                    passwordMatch = true;
                }
                if (s.length() < 8) {
                    passWordLayout.setErrorEnabled(true);
                    passwordText.setError("Password should be more than 8 characters ");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (validEmail()) {
                    emailLayout.setErrorEnabled(false);
                } else {
                    emailLayout.setErrorEnabled(true);
                    emailText.setError("Email is not valid");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        signInButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        registerButton.setOnClickListener(v -> {
            boolean usernameEmpty = usernameText.getText().toString().isEmpty();

            if (usernameEmpty) {
                usernameLayout.setErrorEnabled(true);
                usernameText.setError("Should not be empty !");
            }

            if (validPassword() && validEmail() && !usernameEmpty) {
                progressBar.setVisibility(View.VISIBLE);
                registerText.setVisibility(View.GONE);
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                RegisterRequest registerRequest = new RegisterRequest(username, email, password, sharedPreferenceHelper.getFireBaseToken());
                authService.register(registerRequest);
            } else {
                Toast.makeText(requireContext(), "Fix the errors please", Toast.LENGTH_LONG).show();
            }
            if(emptyFields()){
                Toast.makeText(requireContext(), "Fields should not be empty !", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean emptyFields() {
        return usernameText.getText().toString().isEmpty() ||
                emailText.getText().toString().isEmpty() ||
                passwordText.getText().toString().isEmpty() ||
                confirmPassWordText.getText().toString().isEmpty();
    }

    private boolean validEmail() {

        String email = emailText.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else return false;
    }

    private boolean validPassword() {
        boolean passWordValid = false;
        boolean passMatch = false;
        String passWord = passwordText.getText().toString();
        String confirmPassWord = confirmPassWordText.getText().toString();
        if (passWord.equals(confirmPassWord)) {
            passMatch = true;
        }
        if (passMatch && passWord.length() >= 8) {
            passWordValid = true;
        }
        return passWordValid;
    }

    @Override
    public void onLoginSuccess(UserModel userModel) {

    }

    @Override
    public void onLoginFailure(String error) {

    }

    @Override
    public void onRegisterSuccess() {
        progressBar.setVisibility(View.GONE);
        registerText.setVisibility(View.VISIBLE);
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRegisterFailure(String error) {

        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
    }
}