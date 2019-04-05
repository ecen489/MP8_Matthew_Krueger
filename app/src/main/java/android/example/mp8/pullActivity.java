package android.example.mp8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class pullActivity extends AppCompatActivity {

    private static final String TAG = "pullActivity";
    String studentsName;
    int studentsID;

    Button studentPull;
    Button classPull;
    EditText classEdit;
    EditText studentEdit;

    RecyclerView recycler;
    private ArrayList<String> mData = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        studentPull = findViewById(R.id.studentPull);
        classPull = findViewById(R.id.classPull);
        classEdit = findViewById(R.id.classEdit);
        studentEdit = findViewById(R.id.studentEdit);
    }

    public void studentClick(View view){

        //Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();

        int student = Integer.parseInt(studentEdit.getText().toString());

        DatabaseReference studentKey = reference.child("mp8firebaseproject/students/"+ student);
        DatabaseReference gradeKey = reference.child("mp8firebaseproject/grades/");

        studentKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                students student = data.getValue(students.class);
                studentsName = student.name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //well
            }
        });

        Query gradeQuery = gradeKey.orderByChild("student_id").equalTo(student);

        gradeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                if(data.exists()) {
                    for (DataSnapshot snapshot : data.getChildren()) {
                        Grade grade = snapshot.getValue(Grade.class);
                        String studentGrade = grade.student_grade;
                        String course = grade.course_name;

                        mData.add(studentsName + ", " + course + ", " + studentGrade);
                    }
                    initRecyclerView();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Doesn't exist", Toast.LENGTH_SHORT).show();
                }
                mData.add("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });
    }

    public void classClick(View view){
        int class_num = Integer.parseInt(classEdit.getText().toString());

        DatabaseReference gradeKey = reference.child("mp8firebaseproject/grades/");

        Query gradeQuery = gradeKey.orderByChild("course_id").equalTo(class_num);

        gradeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                if(data.exists()) {
                    for (DataSnapshot snapshot : data.getChildren()) {
                        Grade grade = snapshot.getValue(Grade.class);
                        String studentGrade = grade.student_grade;
                        String course = grade.course_name;
                        studentsName = grade.student_name;

                        mData.add(studentsName + ", " + course + ", " + studentGrade);
                    }
                    initRecyclerView();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Doesn't exist", Toast.LENGTH_SHORT).show();
                }
                mData.add("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });

    }

    public void pushClick(View view){
        Intent intent = new Intent(this, pushActivity.class);
        startActivity(intent);
    }

    public void logoutClick(View view){
        Intent backIntent = new Intent();
        backIntent.putExtra("string", "ok");
        setResult(RESULT_OK, backIntent);
        finish();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView");
        recycler = findViewById(R.id.recyclerView);
        recyclerViewAdapter adapter = new recyclerViewAdapter(mData, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
