
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.ReadingExam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingExamDBRepository extends DBRepository<ReadingExam> {
    public ReadingExamDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(ReadingExam obj) {
        String sql = "INSERT INTO READINGEXAM(id, name, text, author, title, " +
                " teacher_id) VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getExamName());
            statement.setString(3, obj.getText());
            statement.setString(4, obj.getTextAuthor());
            statement.setString(5,obj. getTextTitle());
            statement.setInt(6, obj.getTeacher());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public ReadingExam read(int id) {
        String sql = "SELECT * FROM READINGEXAM WHERE id = ?";

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
    public void update(ReadingExam obj) {
        String sql = "UPDATE READINGEXAM SET name = ?, text = ?, title = ?, author = ?,"
                + " teacher_id = ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(2, obj.getExamName());
            statement.setString(3, obj.getText());
            statement.setString(4, obj.getTextTitle());
            statement.setString(5, obj.getTextAuthor());
            statement.setInt(6, obj.getTeacher());
            statement.setInt(7, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM READINGEXAM WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<ReadingExam> getAll(){
        String sql = "SELECT * FROM READINGEXAM";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<ReadingExam> readingexams = new ArrayList<>();
            while(resultSet.next()){
                readingexams.add(extractFromResultSet(resultSet));
            }
            return readingexams;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private ReadingExam extractFromResultSet(ResultSet resultSet) throws SQLException {
        ReadingExam reading=new ReadingExam(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"));
        reading.setText(resultSet.getString("text"));
        reading.setTextAuthor(resultSet.getString("author"));
        reading.setTextTitle(resultSet.getString("title"));
        return reading;
    }
}
