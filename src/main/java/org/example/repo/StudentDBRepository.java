package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentDBRepository extends DBRepository<Student> {
    public StudentDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Student obj){
        String sql = "INSERT INTO STUDENT (STUDENT_ID, NAME)" +
                " VALUES(?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getName());

            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }
    @Override
    public Student read(int id){

        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void update(Student obj){
        String sql  = "UPDATE STUDENT SET name = ? WHERE STUDENT_ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, obj.getName());

            statement.setInt(2, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM STUDENT WHERE STUDENT_ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Student> getAll(){
        String sql = "SELECT * FROM STUDENT";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Student> students = new ArrayList<>();
            while(resultSet.next()){
                students.add(extractFromResultSet(resultSet));
            }
            return students;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private static Student extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getString("name"),
                resultSet.getInt("student_id")
        );
    }
}
