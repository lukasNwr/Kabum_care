package sk.kabum.kabumcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.dbs.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase dbs;
    private final int RC_SIGN_IN = 10;
    private static final String TAG = "LoginActivity";
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile().requestProfile().requestId()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            uiUpdate(account);
        }
    }

    private void uiUpdate(GoogleSignInAccount account){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_login_loginbutton)
    public void onLoginClicked(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            uiUpdate(account);

            Toast toast = Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast toast = Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT);

            //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPref.edit();

            //editor.putBoolean(getString(R.string.saved_is_user_logged), Boolean.FALSE);
            //editor.commit();
            toast.show();

        }
    }




}
