package com.priyanshagarwal.miriad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class MainActivity extends AppCompatActivity implements NavigationHost{
    LoginFragment loginFragment=new LoginFragment();
    public void onBackPressed() {
        if (loginFragment.loginChoice ==2) {
            loginFragment.errorNull();
            loginFragment.resetButton.setSoundEffectsEnabled(false);
            loginFragment.resetButton.performClick();
            loginFragment.resetButton.setSoundEffectsEnabled(true);
            loginFragment.errorReporter.setVisibility(View.INVISIBLE);
            loginFragment.nextButton.setText("Login");
            loginFragment.progressBar.setVisibility(View.GONE);
//            loginFragment.login.setVisibility(View.VISIBLE);
//            loginFragment.signup.setVisibility(View.VISIBLE);
            loginFragment.signuplayout.setVisibility(View.GONE);
            TranslateAnimation animate = new TranslateAnimation(
                    -loginFragment.mainLayout.getWidth(),
                    0,
                    0,
                    0);
            animate.setDuration(500);
            animate.setFillAfter(true);
//            loginFragment.signuplayout.startAnimation(animate);
            loginFragment.signuplayout1.setVisibility(View.GONE);
//            TranslateAnimation animate1 = new TranslateAnimation(
//                    0,
//                    0,
//                    0,
//                    loginFragment.signuplayout1.getHeight());
//            animate1.setDuration(500);
//            animate1.setFillAfter(true);
//            loginFragment.signuplayout1.startAnimation(animate1);
            loginFragment.signuplayout2.setVisibility(View.GONE);
            loginFragment.mainLayout.startAnimation(animate);
//            TranslateAnimation animate2 = new TranslateAnimation(
//                    0,
//                    0,
//                    0,
//                    loginFragment.signuplayout2.getHeight());
//            animate2.setDuration(500);
//            animate2.setFillAfter(true);
//            loginFragment.signuplayout2.startAnimation(animate2);
//            loginFragment.buttonLayout.setVisibility(View.GONE);
//            loginFragment.passwordReenterTextInput.setVisibility(View.GONE);
//            loginFragment.passwordTextInput.setVisibility(View.GONE);
//            loginFragment.usernameTextInput.setVisibility(View.GONE);
            loginFragment.loginChoice=1;

        } else {
            new AlertDialog.Builder(this).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, loginFragment)
                    .commit();
        }
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();

    }
}
