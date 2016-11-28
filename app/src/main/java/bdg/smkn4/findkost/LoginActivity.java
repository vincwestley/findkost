package bdg.smkn4.findkost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtSignup = (TextView)findViewById(R.id.txtSignup);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = txtUsername.getText().toString();
                final String password = txtPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                        "Please Wait",
                        "Loading ...", true);

                //AuthProcess
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //DeclareError
                            if(password.length() < 6){
                                txtPassword.setError("Password too short, enter minimum 6 characters!");
                                pDialog.dismiss();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_error), Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            }
                        } else{
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            pDialog.dismiss();
                            finish();
                        }
                    }
                });

            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }
}