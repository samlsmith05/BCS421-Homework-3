package com.example.bcs421homework3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class OverviewFragment extends Fragment {

    private Button mShowDetailsButton;
    private Button mPayBillButton;
    private EditText mEmailEditText;
    private EditText mAmountDueEditText;
    private EditText mUsernameEditText;
    private FirebaseFirestore db;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_overview, container, false);

        mShowDetailsButton = v.findViewById(R.id.buttonShowDetails);
        mPayBillButton = v.findViewById(R.id.buttonPayBill);
        mEmailEditText = v.findViewById(R.id.editTextEmailOverview);
        mAmountDueEditText = v.findViewById(R.id.editTextAmountDueOverview);
        db = FirebaseFirestore.getInstance();


        return v;
    }

    public void onStart(){
        super.onStart();

        Intent i = getActivity().getIntent();
        final String userEmail = i.getStringExtra("userInputEmail");

        db.collection("Hwk3Accounts").whereEqualTo("email", userEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String email = document.getString("email");
                        mEmailEditText.setText(email);

                        double amount = document.getDouble("amountDue");
                        String sAmount = Double.toString(amount);
                        mAmountDueEditText.setText(sAmount);

                    }
                }
                else {
                    Log.w("MYDEBUG", "Can't get document", task.getException());
                }

            }
        });

        mShowDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("userInputEmail", userEmail);
                startActivity(intent);
            }
        });

        mPayBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayBillActivity.class);
                intent.putExtra("userInputEmail", userEmail);
                startActivity(intent);
            }
        });
    }
}
