package android.example.mp8;

import java.io.Serializable;

public class Grade implements Serializable {
    int course_id;
    int student_id;
    String student_grade;
    String course_name;
    String student_name;

    public Grade() {}
    public Grade(int course, String name, String grade, int id, String stuName){
        course_id = course;
        course_name = name;
        student_grade = grade;
        student_id = id;
        student_name = stuName;
    }

    public String returnCourseName(){return course_name;}
    public int returnStudentID(){return student_id;}
    public String returnStudentGrade(){return student_grade;}
    public int returnCourseID (){return course_id;}
}
