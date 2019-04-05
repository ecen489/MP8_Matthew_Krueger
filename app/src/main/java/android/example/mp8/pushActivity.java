package android.example.mp8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pushActivity extends AppCompatActivity {

    String whichEd;
    int eddID;

    EditText courseidEdit;
    EditText courseNameEdit;
    EditText gradeEdit;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        Intent intent = getIntent();

        courseidEdit = findViewById(R.id.courseID);
        courseNameEdit = findViewById(R.id.courseName);
        gradeEdit = findViewById(R.id.grade);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void radioClick(View view) {

        if(view.getId() == R.id.edButton)
        {
            RadioButton radio_butt = findViewById(R.id.edButton);
            radio_butt.setChecked(true);
            whichEd = "Ed";
            eddID = 105;
        }
        else if(view.getId() == R.id.eddButton)
        {
            RadioButton radio_butt2 = findViewById(R.id.eddButton);
            radio_butt2.setChecked(true);
            whichEd = "Edd";
            eddID = 106;
        }
        else if(view.getId() == R.id.eddyButton)
        {
            RadioButton radio_butt3 = findViewById(R.id.eddyButton);
            radio_butt3.setChecked(true);
            whichEd ="Eddy";
            eddID = 107;
        }
        else if(view.getId() == R.id.plankButton)
        {
            RadioButton radio_butt4 = findViewById(R.id.plankButton);
            radio_butt4.setChecked(true);
            whichEd = "Plank";
            eddID = 108;
        }
    }

    public void pushClick(View view){

        int courseID = Integer.parseInt(courseidEdit.getText().toString());
        String courseName = courseNameEdit.getText().toString();
        String courseGrade = gradeEdit.getText().toString();

        Grade grade = new Grade(courseID, courseName, courseGrade, eddID, whichEd);

        DatabaseReference gradePush = reference.child("mp8firebaseproject/grades/");
        DatabaseReference randomID = gradePush.push();

        randomID.setValue(grade);

        Toast.makeText(getApplicationContext(), "Grade pushed", Toast.LENGTH_SHORT).show();
    }

    public void backClick(View view){
        finish();
    }
}
