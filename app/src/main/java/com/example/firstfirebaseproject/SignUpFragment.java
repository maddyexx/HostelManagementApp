package com.example.firstfirebaseproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText name, email, pass, re_pass,phone;
    Button register;
    FirebaseFirestore db;
    private FirebaseAuth auth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
    private void settingUpListeners() {
//        register.setOnClickListener(view -> {
//////INSERT CODE
//            User u = new User();
//            if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()) || TextUtils.isEmpty(re_pass.getText().toString())) {
//                Toast.makeText(getActivity(), "Fill the required field", Toast.LENGTH_SHORT).show();
//            } else {
//                db = FirebaseFirestore.getInstance();
//                u.setEmail(name.getText().toString());
//                u.setPassword(pass.getText().toString());
////                u.setPhone(Integer.parseInt(phone.getText().toString()));
//                u.getFname(name.getText().toString());
//                String id = db.collection("user").document().getId();
////                u.setUid(Integer.parseInt(id));
//                db.collection("user").document(id).set(u).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "Failed to Register", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        register.setOnClickListener(view -> {
            // Get the input values
            String nameValue = name.getText().toString().trim();
            String emailValue = email.getText().toString().trim();
            String phoneValue = phone.getText().toString().trim();
            String passValue = pass.getText().toString().trim();
            String rePassValue = re_pass.getText().toString().trim();
            auth = FirebaseAuth.getInstance();
            if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Fill the required Fields", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(emailValue, passValue).addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Login Register Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Weak Password!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Check if any required fields are empty
            if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(phoneValue) ||
                    TextUtils.isEmpty(passValue) || TextUtils.isEmpty(rePassValue)) {
                Toast.makeText(getActivity(), "Fill the required fields", Toast.LENGTH_SHORT).show();
            } else {
                // Check if passwords match
                if (!passValue.equals(rePassValue)) {
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return; // Exit the method to prevent further execution
                }
                // Create a new User object
                User user = new User();
                user.setEmail(emailValue);
                user.setPassword(passValue);
                user.setFname(nameValue);
                try {
                    String phone = phoneValue;
                    user.setPhone(phone);
                } catch (NumberFormatException e) {
                    // Handle the case when the phone value cannot be parsed
                    Toast.makeText(getActivity(), "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return; // Exit the method to prevent further execution
                }
                // Get Firestore instance and collection reference
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference usersCollection = db.collection("user");

                // Generate a new document ID
                String id;
                try {
                    id = usersCollection.document().getId();
                    user.setUid(id);
                } catch (NumberFormatException e) {
                    // Handle the case when the phone value cannot be parsed
                    Toast.makeText(getActivity(), "Invalid ID", Toast.LENGTH_SHORT).show();
                    return; // Exit the method to prevent further execution
                }

                // Save the user to Firestore
                usersCollection.document(id).set(user)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                email.setText("");
                                phone.setText("");
                                pass.setText("");
                                re_pass.setText("");
                            } else {
                                Toast.makeText(getActivity(), "Failed to Register user", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
//        String emailValue = email.getText().toString().trim();
//        String passValue = pass.getText().toString().trim();
//        auth = FirebaseAuth.getInstance();
//        if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
//            Toast.makeText(getActivity(), "Fill the required Fields", Toast.LENGTH_SHORT).show();
//        } else {
//            auth.createUserWithEmailAndPassword(emailValue, passValue).addOnCompleteListener(getActivity(), task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getActivity(), "Register Successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "Weak Password!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponents();
        settingUpListeners();
        // Inflate the layout for this fragment
        return rootView;
    }
    private void initComponents() {
        name = rootView.findViewById(R.id.fname);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.phone);
        pass = rootView.findViewById(R.id.password);
        re_pass = rootView.findViewById(R.id.confirmpassword);
        register = rootView.findViewById(R.id.signup_register_btn);
    }
}