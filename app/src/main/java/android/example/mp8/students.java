package android.example.mp8;

public class students {
    int id;
    String name;

    public students(){}
    public students(int student_id, String student_name){
        id = student_id;
        name = student_name;
    }

    public int returnStudentID(){return id;}
    public String returnStudentName(){return name;}
}
