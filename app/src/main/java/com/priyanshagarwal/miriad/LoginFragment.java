package com.priyanshagarwal.miriad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
    private String firstName="Guest",lastName="";
    private List<String> best= Collections.emptyList();
    private String username="Guest";
    private String phoneNo="9876543210";
    LinearLayout signuplayout,signuplayout1,signuplayout2,mainLayout;

    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    TextView errorReporter;
    ProgressBar progressBar;
    public MaterialButton jointheclub;
    public RelativeLayout buttonLayout;
    public int loginChoice=1;
    TextInputEditText usernameEditText,passwordReenterEditText,emailEditText,passwordEditText,firstNameEditText,lastNameEditText,phoneNoEditText;
    public Button resetButton,nextButton;
    public TextInputLayout passwordTextInput,passwordReenterTextInput,usernameTextInput,emailTextInput,firstNameTextInput,lastNameTextInput,phoneNoTextInput;

    boolean fieldChecker()
    {
        if(loginChoice==2)
        {
            if(firstNameEditText.getText().toString().isEmpty())
            {
                firstNameTextInput.setError(getString(R.string.mrd_empty_field_error));
                firstNameEditText.requestFocus();
                return false;
            }
            if(lastNameEditText.getText().toString().isEmpty())
            {
                lastNameTextInput.setError(getString(R.string.mrd_empty_field_error));
                lastNameEditText.requestFocus();
                return false;
            }
            if(usernameEditText.getText().toString().isEmpty())
            {
                usernameTextInput.setError(getString(R.string.mrd_empty_field_error));
                usernameEditText.requestFocus();
                return false;
            }

        }
        if(emailEditText.getText().toString().isEmpty())
        {
            emailTextInput.setError(getString(R.string.mrd_empty_field_error));
            emailEditText.requestFocus();
            return false;
        }
        if(loginChoice==2)
            if(phoneNoEditText.getText().toString().isEmpty())
            {
                phoneNoTextInput.setError(getString(R.string.mrd_empty_field_error));
                phoneNoEditText.requestFocus();
                return false;
            }
        else
            {
                try{
                    long n=Long.parseLong(phoneNoEditText.getText().toString());
                    if(phoneNoEditText.getText().toString().length()!=10)
                    {
                        phoneNoTextInput.setError("Phone number must be of 10 digits.");
                        phoneNoEditText.requestFocus();
                        return false;
                    }

                }
                catch (NumberFormatException e)
                {
                    phoneNoTextInput.setError("Phone number must contain only digits.");
                    phoneNoEditText.requestFocus();
                    return false;
                }
            }

        if(passwordEditText.getText().toString().isEmpty())
        {
            passwordTextInput.setError(getString(R.string.mrd_empty_field_error));
            passwordEditText.requestFocus();
            return false;
        }
        if(!isPasswordValid(passwordEditText.getText()))
        {
            passwordTextInput.setError(getString(R.string.mrd_error_password));
            passwordEditText.requestFocus();
            return false;
        }

        if(loginChoice==2)
        {
            if(passwordReenterEditText.getText().toString().isEmpty())
            {
                passwordReenterTextInput.setError(getString(R.string.mrd_empty_field_error));
                passwordReenterEditText.requestFocus();
                return false;
            }

        }
        return true;
    }

    void errorNull()
    {
        firstNameTextInput.setError(null);
        lastNameTextInput.setError(null);
        usernameTextInput.setError(null);
        passwordReenterTextInput.setError(null);
        passwordTextInput.setError(null);
        phoneNoTextInput.setError(null);
        emailTextInput.setError(null);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       //Firebase Authentication

        mAuth = FirebaseAuth.getInstance();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mrd_login_fragment, container, false);
        errorReporter=view.findViewById(R.id.errorReporter);
        buttonLayout=view.findViewById(R.id.buttonLayout);
//       login=view.findViewById(R.id.login);
       jointheclub=view.findViewById(R.id.jointheclub);
        passwordTextInput = view.findViewById(R.id.password_text_input);
        passwordEditText = view.findViewById(R.id.password_edit_text);
//        passwordEditText.requestFocus();
        nextButton = view.findViewById(R.id.next_button);
        resetButton=view.findViewById(R.id.reset_button);
        usernameTextInput=view.findViewById(R.id.username_text_input);
        usernameEditText = view.findViewById(R.id.username_edit_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        progressBar=view.findViewById(R.id.progressBar);
        firstNameEditText=view.findViewById(R.id.firstname_edit_text);
        lastNameEditText=view.findViewById(R.id.lastname_edit_text);
        phoneNoEditText=view.findViewById(R.id.phoneno_edit_text);
        passwordReenterTextInput=view.findViewById(R.id.password_reenter_text_input);
        passwordReenterEditText=view.findViewById(R.id.password_reenter_edit_text);

        firstNameTextInput=view.findViewById(R.id.firstname_text_input);
        lastNameTextInput=view.findViewById(R.id.lastname_text_input);
        emailTextInput=view.findViewById(R.id.email_text_input);
        phoneNoTextInput=view.findViewById(R.id.phoneno_text_input);

        //backpress
        //loginchoice!=0 restart else exit
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginChoice=1;
//                login.setVisibility(View.GONE);
//                signup.setVisibility(View.GONE);
//                buttonLayout.setVisibility(View.VISIBLE);
//                usernameTextInput.setVisibility(View.VISIBLE);
//                passwordTextInput.setVisibility(View.VISIBLE);
//                resetButton.setText("Reset");
//                nextButton.setText("Login");
//            }
//        });
        signuplayout=view.findViewById(R.id.signupLayout);
        signuplayout1=view.findViewById(R.id.signupLayout1);
        signuplayout2=view.findViewById(R.id.signupLayout2);
        mainLayout=view.findViewById(R.id.mainLayout);
        jointheclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginChoice=2;
//                login.setVisibility(View.GONE);
//                signup.setVisibility(View.GONE);
//                usernameTextInput.setVisibility(View.VISIBLE);
                buttonLayout.setVisibility(View.VISIBLE);
                signuplayout.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        mainLayout.getWidth(),
                        0,
                        0,
                        0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                signuplayout1.setVisibility(View.VISIBLE);
//                TranslateAnimation animate1 = new TranslateAnimation(
//                        0,
//                        0,
//                        0,
//                        signuplayout1.getHeight());
//                animate1.setDuration(500);
//                animate1.setFillAfter(true);
//                TranslateAnimation animate2 = new TranslateAnimation(
//                        0,
//                        0,
//                        0,
//                        signuplayout2.getHeight());
//                animate2.setDuration(500);
//                animate2.setFillAfter(true);
                signuplayout2.setVisibility(View.VISIBLE);
//                signuplayout.startAnimation(animate);
//                signuplayout1.startAnimation(animate1);
//                signuplayout2.startAnimation(animate2);
                    mainLayout.startAnimation(animate);
//                passwordTextInput.setVisibility(View.VISIBLE);
//                passwordReenterTextInput.setVisibility(View.VISIBLE);
//                resetButton.setText("Reset");
                nextButton.setText("SignUp");
            }
        });

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorReporter.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
//                if (!isPasswordValid(passwordEditText.getText())) {
//                    passwordTextInput.setError(getString(R.string.mrd_error_password));
//                    progressBar.setVisibility(View.GONE);
//                    errorReporter.setVisibility(View.INVISIBLE);
                errorNull();
                if(!fieldChecker())
                {
                    progressBar.setVisibility(View.GONE);
                    errorReporter.setVisibility(View.INVISIBLE);
                } else {
                    String email,password;
                    email=emailEditText.getText().toString();
                    password=passwordEditText.getText().toString();
                  if(loginChoice==2) {
                       if(password.equals(passwordReenterEditText.getText().toString())) {
                           passwordReenterTextInput.setError(null);
                           firstName=firstNameEditText.getText().toString();
                           lastName=lastNameEditText.getText().toString();
                           phoneNo=phoneNoEditText.getText().toString();
                           username=usernameEditText.getText().toString();
                           createAccount(email, password);
                       }
                       else
                       {
                           progressBar.setVisibility(View.GONE);
                           errorReporter.setVisibility(View.INVISIBLE);
                           passwordReenterTextInput.setError("Passwords don't match.");
                   }
                   }
                   else if(loginChoice==1)
                   signIn(email,password);

//                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment

                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorNull();
                passwordEditText.setText(null);
                passwordReenterEditText.setText(null);
                usernameEditText.setText(null);
                errorReporter.setVisibility(View.INVISIBLE);
            }
        });
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });
        firstNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    firstNameTextInput.setError(null); //Clear the error

                return false;
            }
        });
        lastNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                lastNameTextInput.setError(null); //Clear the error

                return false;
            }
        });
        usernameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                usernameTextInput.setError(null); //Clear the error

                return false;
            }
        });
        emailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                emailTextInput.setError(null); //Clear the error

                return false;
            }
        });
        phoneNoEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                phoneNoTextInput.setError(null); //Clear the error

                return false;
            }
        });
        passwordReenterEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(passwordEditText.getText().toString().equals(passwordReenterEditText.getText().toString()))
                passwordReenterTextInput.setError(null); //Clear the error

                return false;
            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(getContext(),QuestionsActivity.class);
            startActivity(intent);

        }
        // Snippet from "Navigate to the next Fragment" section goes here.
//        emailEditText.getParent().requestFocus();
        return view;
    }

    private void signIn(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("Yo", "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            errorReporter.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Intent intent=new Intent(getContext(),QuestionsActivity.class);
//                            intent.putExtra("name",firstName);
//                            int i;
//                            for(i=0;i<best.size();i++)
//                            intent.putExtra(Integer.toString(i),best.get(i));
//                            intent.putExtra("size",Integer.toString(i));
                            startActivity(intent);
                        } else {
//                             If sign in fails, display a message to the user.
//                            errorReporter.setText(task.getException().toString());
                            try {
                                errorReporter.setText(task.getException().toString().split(":")[1]);
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                errorReporter.setText(task.getException().toString());
                            }
                            errorReporter.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
//                            Log.w("Yo", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginFragment.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void createAccount(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email,password )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user1 = mAuth.getCurrentUser();
//                            updateUI(user);
                            User user=new User(firstName,lastName,best,username,password,email,phoneNo);
                            errorReporter.setVisibility(View.INVISIBLE);



                            FirebaseDatabase.getInstance().getReference("Users")

                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override

                                public void onComplete(@NonNull Task<Void> task) {

                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        errorNull();
                                        Intent intent=new Intent(getContext(),QuestionsActivity.class);
                                        intent.putExtra("name",firstName);
//                                        int i;
//                                        for(i=0;i<best.size();i++)
//                                            intent.putExtra(Integer.toString(i),best.get(i));
//                                        intent.putExtra("size",Integer.toString(i));
                                        startActivity(intent);

                                    } else {

                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();



                                    }

                                }

                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                errorReporter.setText(task.getException().toString().split(":")[1]);
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                errorReporter.setText(task.getException().toString());
                            }
                            errorReporter.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.w("yo", "createUserWithEmail:failure", task.getException());
//                                        Toast.makeText(LoginFragment.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

//    private void updateUI(FirebaseUser user) {
//
//    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
}
