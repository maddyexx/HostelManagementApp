package com.example.firstfirebaseproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    EditText email, pass;
    Button login;
    private FirebaseAuth auth;
    SharedPreferences pref;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
//        Boolean check = pref.getBoolean("flag", false);
//        Intent iNext;
//        if (check) {//already logged in
//            iNext = new Intent(getActivity(), HomeActivity.class);
//            startActivity(iNext);
//        }
    }
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents();
        settingUpListeners();
        // Inflate the layout for this fragment
        return rootView;
    }
    private void initComponents() {
        email = rootView.findViewById(R.id.email);
        pass = rootView.findViewById(R.id.password);
        login = rootView.findViewById(R.id.login_btn);
    }
    private void settingUpListeners() {
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(view -> {
            if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Fill the required Fields", Toast.LENGTH_SHORT).show();
            } else {
                String email1 = email.getText().toString();
                String password = pass.getText().toString();
//                Login Authentication Code
                auth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        pass.setText("");
                        Intent i= new Intent(getActivity(), admin_panel.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}