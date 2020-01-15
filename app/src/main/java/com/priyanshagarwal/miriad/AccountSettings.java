package com.priyanshagarwal.miriad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AccountSettings extends AppCompatActivity {
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
    public void onBackPressed() {
        finish();
    }
    TextInputLayout oldPasswordInputLayout,newPasswordInputLayout,confirmPasswordInputLayout;
    TextInputEditText oldEditText,newEditText,confirmEditText;
    User user;
    void textNull()
    {
     oldEditText.setText("");
     newEditText.setText("");
     confirmEditText.setText("");
    }
    void errorNull()
    {
        oldPasswordInputLayout.setError(null);
        newPasswordInputLayout.setError(null);
        confirmPasswordInputLayout.setError(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        final LinearLayout changePasswordLayout=findViewById(R.id.changePasswordLayout);
        final Button changePasswordButton=findViewById(R.id.changePasswordButton);
        Button updatePasswordButton=findViewById(R.id.update_button);
        Button cancelButton=findViewById(R.id.cancel_button);
       oldPasswordInputLayout=findViewById(R.id.old_password_text_input);
        newPasswordInputLayout=findViewById(R.id.new_password_text_input);
        confirmPasswordInputLayout=findViewById(R.id.confirm_password_text_input);
        oldEditText=findViewById(R.id.old_password_edit_text);
        newEditText=findViewById(R.id.new_password_edit_text);
        confirmEditText=findViewById(R.id.confirm_password_edit_text);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        final ProgressBar progressBar=findViewById(R.id.progressBar1);
        user= (User)bundle.getSerializable("User Data");
        Button userName=findViewById(R.id.username_button);
        userName.setText(user.getUsername());
        TextView emailTextView=findViewById(R.id.email_textView);
        TextView phoneNoTextView=findViewById(R.id.phoneno_textView);
        TextView bestScoreTextView=findViewById(R.id.bestScore_textView);
        TextView helloTextView=findViewById(R.id.hello_textView);
        helloTextView.setText("Hello "+user.getFirstName()+",");
        emailTextView.setText(user.getEmail());
        phoneNoTextView.setText(user.getPhoneNo());

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation animate = new TranslateAnimation(
                        -changePasswordLayout.getWidth(),
                        0,
                        0,
                        0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                changePasswordButton.setVisibility(View.INVISIBLE);
                changePasswordLayout.setVisibility(View.VISIBLE);
                changePasswordLayout.startAnimation(animate);
            }
        });
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("password").setValue(newEditText.getText().toString());
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                List<String> a=Collections.emptyList();
//                User temp;
//                for(DataSnapshot ds: dataSnapshot.getChildren()) {
//                    temp = ds.getValue(User.class);
//                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(ds.getKey()))
//                    {
//                        user=ds.getValue(User.class);
////                        userNameTextView.setText("Hi "+user.getFirstName());
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPasswordValid(oldEditText.getText()))
                {
                    oldPasswordInputLayout.setError(getString(R.string.mrd_error_password));
                    oldEditText.requestFocus();
                }
                else if(!isPasswordValid(newEditText.getText()))
                {
                    errorNull();
                    newPasswordInputLayout.setError(getString(R.string.mrd_error_password));
                    newEditText.requestFocus();
                }
                else if(!isPasswordValid(confirmEditText.getText()))
                {
                    errorNull();
                    confirmPasswordInputLayout.setError(getString(R.string.mrd_error_password));
                    confirmEditText.requestFocus();
                }
                else if(!confirmEditText.getText().toString().equals(newEditText.getText().toString()))
                {
                    errorNull();
                    confirmPasswordInputLayout.setError("Passwords don't match.");
                    confirmEditText.requestFocus();
                }
//                else if(!oldEditText.getText().toString().equals(user.getPassword()))
//                {
//                    errorNull();
//                    oldPasswordInputLayout.setError("Wrong password entered.");
//                    oldEditText.requestFocus();
//                }
                else {
                    errorNull();
                    progressBar.setVisibility(View.VISIBLE);
                    String a=oldEditText.getText().toString();
                    if(a==""||a==null)
                    {
                        return;
                    }
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), a);
                    firebaseUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(newEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(),"Password updated",Toast.LENGTH_SHORT);
//                                                    Log.d(TAG, "Password updated");
                                                    errorNull();
//                                                    oldPasswordInputLayout.setError("Password updated");
                                                    textNull();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                } else {
//                                                    oldPasswordInputLayout.setError("password");
                                                    Toast.makeText(getApplicationContext(),"Error password not updated",Toast.LENGTH_SHORT);
//                                                    Log.d(TAG, "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        oldPasswordInputLayout.setError("Wrong password");
                                        Toast.makeText(getApplicationContext(),"Error auth failed.",Toast.LENGTH_SHORT);
//                                        Log.d(TAG, "Error auth failed");
                                    }
                                }
                            });
                }
            }
            });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePasswordButton.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        -changePasswordLayout.getWidth(),
                        0,
                        0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                changePasswordLayout.startAnimation(animate);
//                onEnterAnimationComplete();
                textNull();
                errorNull();
                changePasswordLayout.setVisibility(View.GONE);
            }
        });
    }
    }
