package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Teacher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TeacherDBRepository extends DBRepository<Teacher> {
    public TeacherDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Teacher obj){
        String sql = "INSERT INTO TEACHER (TEACHER_ID, NAME)" +
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
    public Teacher read(int id){

        String sql = "SELECT * FROM TEACHER WHERE TEACHER_ID = ?";
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
    public void update(Teacher obj){
        String sql  = "UPDATE TEACHER SET name = ? WHERE TEACHER_ID = ?";

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
        String sql = "DELETE FROM TEACHER WHERE TEACHER_ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Teacher> getAll(){
        String sql = "SELECT * FROM TEACHER";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Teacher> students = new ArrayList<>();
            while(resultSet.next()){
                students.add(extractFromResultSet(resultSet));
            }
            return students;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private static Teacher extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Teacher(
                resultSet.getString("name"),
                resultSet.getInt("teacher_id")
        );
    }
}
