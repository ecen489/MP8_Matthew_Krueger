package android.example.mp8;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth authorize;
    FirebaseUser user = null;

    Button loginButton;
    Button newButton;
    Button gobutton;
    EditText passwordView;
    EditText usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        authorize = FirebaseAuth.getInstance();

        //database = FirebaseDatabase.getInstance();
        //reference = database.getReference();

        loginButton = findViewById(R.id.login);
        newButton = findViewById(R.id.create);
        passwordView = findViewById(R.id.password);
        usernameView = findViewById(R.id.username);
        gobutton = findViewById(R.id.goButton);
    }

    public void createClick(View view){

        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();

        authorize.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Created Firebase account", Toast.LENGTH_SHORT).show();
                            user = authorize.getCurrentUser();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Couldn't make a new account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void loginClick (View view){

        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();

        authorize.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Logged in to Firebase", Toast.LENGTH_SHORT).show();
                            user = authorize.getCurrentUser();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Couldn't login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void go (View view){
        if(user!=null) {
            Intent intent = new Intent(this, pullActivity.class);
            startActivityForResult(intent, 1);
        }
        else{
            Toast.makeText(MainActivity.this, "Please log in.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode==1) {
            authorize.signOut();
            user = null;
            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //user = authorize.getCurrentUser();
    }
}
