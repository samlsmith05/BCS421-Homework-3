package com.example.bcs421homework3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailFragment extends Fragment {

        private EditText mEmailEditText;
        private EditText mAmountDueEditText;
        private EditText mFirstEditText;
        private EditText mLastEditText;
        private FirebaseFirestore db;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

            View v = inflater.inflate(R.layout.fragment_detail, container, false);

            mEmailEditText = v.findViewById(R.id.editTextEmailDetail);
            mAmountDueEditText = v.findViewById(R.id.editTextAmountDueDetail);
            mFirstEditText = v.findViewById(R.id.editTextFirstDetail);
            mLastEditText = v.findViewById(R.id.editTextLastDetail);
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

                            String first = document.getString("first");
                            mFirstEditText.setText(first);

                            String last = document.getString("last");
                            mLastEditText.setText(last);
                        }
                    }
                    else {
                        Log.d("MYDEBUG", "Can't get document");
                    }
                }
            });

        }

}
