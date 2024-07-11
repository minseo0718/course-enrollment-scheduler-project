
import java.security.Timestamp;
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
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            String semester = entry.getSemester();
            String courseCode = entry.getCourseCode();
            String studentID = entry.getStudentID();
            String status = entry.getStatus();
            java.sql.Timestamp timestamp = entry.getTimestamp();
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, semester);
            addScheduleEntry.setString(2, courseCode);
            addScheduleEntry.setString(3, studentID);
            addScheduleEntry.setString(4, status);
            addScheduleEntry.setTimestamp(5, timestamp);
            addScheduleEntry.executeUpdate();
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule  where semester = (?) and studentid = (?)");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);

        
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                scheduleEntries.add(new ScheduleEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntries;
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int studentCount = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select studentid from app.schedule where coursecode = ? and semester = ? and status = 'scheduled'");
            getScheduledStudentCount.setString(1, courseCode);
            getScheduledStudentCount.setString(2, semester);
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                studentCount++;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<ScheduleEntry>();
        
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where coursecode = ? and semester = ? and status = 'scheduled'");
            getScheduledStudentsByClass.setString(1, courseCode);
            getScheduledStudentsByClass.setString(2, semester);
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                scheduleEntries.add(new ScheduleEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntries;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<ScheduleEntry>();
        
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where coursecode = ? and semester = ? and status = 'waitlisted'");
            getWaitlistedStudentsByClass.setString(1, courseCode);
            getWaitlistedStudentsByClass.setString(2, semester);
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                scheduleEntries.add(new ScheduleEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntries;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where coursecode = ? and semester = ? and studentid = ?");
            dropStudentScheduleByCourse.setString(1, courseCode);
            dropStudentScheduleByCourse.setString(2, semester);
            dropStudentScheduleByCourse.setString(3, studentID);
            dropStudentScheduleByCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where coursecode = ? and semester = ?");
            dropScheduleByCourse.setString(1, courseCode);
            dropScheduleByCourse.setString(2, semester);
            dropScheduleByCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public static void updateScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            String semester = entry.getSemester();
            String courseCode = entry.getCourseCode();
            String studentID = entry.getStudentID();
            String status = entry.getStatus();
            java.sql.Timestamp timestamp = entry.getTimestamp();
            updateScheduleEntry = connection.prepareStatement("update app.schedule set semester = ?, coursecode = ?, studentid = ?, status = ?, timestamp = ? where semester = ? and coursecode = ? and studentid = ?");
            updateScheduleEntry.setString(1, semester);
            updateScheduleEntry.setString(2, courseCode);
            updateScheduleEntry.setString(3, studentID);
            updateScheduleEntry.setString(4, status);
            updateScheduleEntry.setTimestamp(5, timestamp);
            updateScheduleEntry.setString(6, semester);
            updateScheduleEntry.setString(7, courseCode);
            updateScheduleEntry.setString(8, studentID);
            updateScheduleEntry.executeUpdate();
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
