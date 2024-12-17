
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Reading;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingDBRepository extends DBRepository<Reading> {
    public ReadingDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Reading obj) {
        String sql = "INSERT INTO READING(id, name, text, author, title, " +
                "teacher_id, max_students) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getCourseName());
            statement.setString(3, obj.getText());
            statement.setString(4,obj.getTextAuthor());
            statement.setString(5,obj.getTextTitle());
            statement.setInt(6, obj.getTeacher());
            statement.setInt(7, obj.getAvailableSlots());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public Reading read(int id) {
        String sql = "SELECT * FROM READING WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void update(Reading obj) {
        String sql = "UPDATE READING SET name = ?, text = ?, title = ?, author = ?,"
                + " teacher_id = ?, max_students= ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getCourseName());
            statement.setString(2, obj.getText());
            statement.setString(3, obj.getTextTitle());
            statement.setString(4, obj.getTextAuthor());
            statement.setInt(5, obj.getTeacher());
            statement.setInt(6, obj.getAvailableSlots());
            statement.setInt(7, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM READING WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Reading> getAll(){
        String sql = "SELECT * FROM READING";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Reading> readingcourses = new ArrayList<>();
            while(resultSet.next()){
                readingcourses.add(extractFromResultSet(resultSet));
            }
            return readingcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Reading extractFromResultSet(ResultSet resultSet) throws SQLException {
        Reading reading=new Reading(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"),resultSet.getInt("max_students"));
        reading.setText(resultSet.getString("text"));
        reading.setTextAuthor(resultSet.getString("author"));
        reading.setTextTitle(resultSet.getString("title"));
        return reading;
    }
}
