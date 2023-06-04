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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileInputStream;
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
//        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        login.setOnClickListener(view -> {
            CollectionReference usersCollection = db.collection("user");
            String em = email.getText().toString();
            String password = pass.getText().toString();
            if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Fill the required Fields", Toast.LENGTH_SHORT).show();
            } else {
            usersCollection.whereEqualTo("email", em)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                int i = 0;
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    // Perform your checks or operations on each document here
                                    // For example, you can access document fields using document.getString("fieldName")
                                    DocumentSnapshot userSnapshot = querySnapshot.getDocuments().get(i);
                                    String storedPassword = userSnapshot.getString("password");
                                    String uid = userSnapshot.getString("uid");
                                    if (password.equals(storedPassword)) {
                                        // Login successful, proceed to the next screen
                                        db.collection("user").document(uid).get()
                                                .addOnSuccessListener(documentSnapshot -> {
                                                    if (documentSnapshot.exists()) {
                                                        String userRole = documentSnapshot.getString("role");
                                                        // Based on the user's role, navigate to the appropriate screen
                                                        if (userRole.equals("user")) {
                                                            // Navigate to admin panel
                                                            email.setText("");
                                                            pass.setText("");
                                                            Intent adminIntent = new Intent(getActivity(), user_panel.class);
                                                            startActivity(adminIntent);
                                                        } else if (userRole.equals("admin")) {
                                                            // Navigate to user panel
                                                            email.setText("");
                                                            pass.setText("");
                                                            Intent adminIntent = new Intent(getActivity(), admin_panel.class);
                                                            startActivity(adminIntent);
                                                        }
                                                    } else {
                                                        // Handle the case where the user's role document doesn't exist
                                                        Toast.makeText(getActivity(), "Connection Error. Please try again", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Handle any errors that occurred while retrieving the user's role
                                                    Toast.makeText(getActivity(), "Connection Error. Please try again", Toast.LENGTH_SHORT).show();
                                                });
                                        // You can also retrieve other user information from the userSnapshot
                                    } else {
                                        // Invalid password
                                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        email.setText("");
                                        pass.setText("");
                                    }
                                    i++;
                                }
                            } else {
                                // User with the provided email does not exist
                                Toast.makeText(getActivity(), "User Not Registered!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error occurred while fetching user data
                            Toast.makeText(getActivity(), "Connection Error. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
//            if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
//                Toast.makeText(getActivity(), "Fill the required Fields", Toast.LENGTH_SHORT).show();
//            } else {
//                String email1 = email.getText().toString();
//                String password = pass.getText().toString();
//                db.collection("user").document(email1).get()
//                        .addOnSuccessListener(documentSnapshot -> {
//                            if (documentSnapshot.exists()) {
//                                String userRole = documentSnapshot.getString("role");
//                                // Based on the user's role, navigate to the appropriate screen
//                                if (userRole.equals("user")) {
//                                    // Navigate to admin panel
//                                    auth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(task -> {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
//                                            email.setText("");
//                                            pass.setText("");
//                                            Intent adminIntent = new Intent(getActivity(), user_panel.class);
//                                            startActivity(adminIntent);
//                                        } else {
//                                            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                } else if (userRole.equals("admin")) {
//                                    // Navigate to user panel
//                                    auth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(task -> {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
//                                            email.setText("");
//                                            pass.setText("");
//                                            Intent adminIntent = new Intent(getActivity(), admin_panel.class);
//                                            startActivity(adminIntent);
//                                        } else {
//                                            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            } else {
//                                // Handle the case where the user's role document doesn't exist
//                                Toast.makeText(getActivity(), "Failed to register. Please try again", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(getActivity(), SignUpFragment.class);
//                                startActivity(i);
//                            }
//                        })
//                        .addOnFailureListener(e -> {
//                            // Handle any errors that occurred while retrieving the user's role
//                            Toast.makeText(getActivity(), "Failed to register. Please try again", Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getActivity(), SignUpFragment.class);
//                            startActivity(i);
//                        });
////                Login Authentication Code
            }
        });
    }
}