
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author minseokim
 */
public class MultiTableQueries {
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement getCourseDescriptionList;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;

    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> courseDescription = new ArrayList<ClassDescription>();
        try
        {
            getCourseDescriptionList = connection.prepareStatement("select APP.class.coursecode, APP.course.description, APP.class.seats from app.class, APP.course where semester = (?) and APP.class.coursecode = APP.course.coursecode order by APP.class.coursecode");
            getCourseDescriptionList.setString(1, semester);
            resultSet = getCourseDescriptionList.executeQuery();
            
            
            while(resultSet.next())
            {
                courseDescription.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseDescription;
    }

    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntries = new ArrayList<StudentEntry>();
        
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select APP.schedule.studentid, APP.student.firstname, APP.student.lastname from APP.student, APP.schedule where APP.schedule.studentid = APP.student.studentid and APP.schedule.coursecode = ? and APP.schedule.semester = ? and APP.schedule.status = 'scheduled'");
            getScheduledStudentsByClass.setString(1, courseCode);
            getScheduledStudentsByClass.setString(2, semester);
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                studentEntries.add(new StudentEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentEntries;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntries = new ArrayList<StudentEntry>();
        
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select APP.schedule.studentid, APP.student.firstname, APP.student.lastname from APP.student, APP.schedule where APP.schedule.studentid = APP.student.studentid and APP.schedule.coursecode = ? and APP.schedule.semester = ? and APP.schedule.status = 'waitlisted' order by APP.schedule.timestamp");
            getWaitlistedStudentsByClass.setString(1, courseCode);
            getWaitlistedStudentsByClass.setString(2, semester);
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                studentEntries.add(new StudentEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentEntries;
    }
    
    
}
