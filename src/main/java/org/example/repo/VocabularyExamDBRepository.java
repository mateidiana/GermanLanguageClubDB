
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.VocabularyExam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VocabularyExamDBRepository extends DBRepository<VocabularyExam> {
    public VocabularyExamDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(VocabularyExam obj) {
        String sql = "INSERT INTO vocabexam(id, name, " +
                " teacher_id) VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getExamName());
            statement.setInt(3, obj.getTeacher());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public VocabularyExam read(int id) {
        String sql = "SELECT * FROM vocabexam WHERE id = ?";

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
    public void update(VocabularyExam obj) {
        String sql = "UPDATE vocabexam SET name = ?, "
                + " teacher_id = ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getExamName());
            statement.setInt(2, obj.getTeacher());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM vocabexam WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<VocabularyExam> getAll(){
        String sql = "SELECT * FROM vocabexam";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<VocabularyExam> vocabcourses = new ArrayList<>();
            while(resultSet.next()){
                vocabcourses.add(extractFromResultSet(resultSet));
            }
            return vocabcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private VocabularyExam extractFromResultSet(ResultSet resultSet) throws SQLException {
        VocabularyExam vocabulary=new VocabularyExam(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"));
        return vocabulary;
    }
}
