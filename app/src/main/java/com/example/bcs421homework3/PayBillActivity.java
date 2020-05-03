package com.example.bcs421homework3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PayBillActivity extends AppCompatActivity {

    private EditText mAmountDueEditText;
    private EditText mPayAmountEditText;
    private Button mPayButton;
    private FirebaseFirestore db;
    private String mDocumentId;
    private double mAmountDue;
    private double mRemaining;
    private String msPayAmount;


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);

        mAmountDueEditText = findViewById(R.id.editTextAmountDuePayBill);
        mPayAmountEditText = findViewById(R.id.editTextPayAmountPayBill);
        mPayButton = findViewById(R.id.buttonPay);
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String userEmail = i.getStringExtra("userInputEmail");

        db.collection("Hwk3Accounts").whereEqualTo("email", userEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        mAmountDue = document.getDouble("amountDue");
                        String sAmount = Double.toString(mAmountDue);
                        mAmountDueEditText.setText(sAmount);

                        mDocumentId = document.getId();

                    }
                }
                else {
                    Log.d("MYDEBUG", "Can't get document");
                }
            }
        });

        this.mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msPayAmount = mPayAmountEditText.getText().toString();
                double payAmount = Double.parseDouble(msPayAmount);
                mRemaining = mAmountDue - payAmount;

                db.collection("Hwk3Accounts").document(mDocumentId).update("amountDue", mRemaining).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("MYDEBUG", "Update successful");
                            finish();
                        }
                        else {
                            Log.d("MYDEBUG", "Update FAILED!!!");
                            finish();
                        }
                    }
                });
            }
        });


    }

}
