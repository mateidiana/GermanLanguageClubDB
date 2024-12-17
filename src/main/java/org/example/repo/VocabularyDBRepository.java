
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Vocabulary;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VocabularyDBRepository extends DBRepository<Vocabulary> {
    public VocabularyDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Vocabulary obj) {
        String sql = "INSERT INTO VOCABULARY(id, name, " +
                "teacher_id, maxStudents) VALUES(?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getCourseName());
            statement.setInt(3, obj.getTeacher());
            statement.setInt(4, obj.getAvailableSlots());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public Vocabulary read(int id) {
        String sql = "SELECT * FROM VOCABULARY WHERE id = ?";

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
    public void update(Vocabulary obj) {
        String sql = "UPDATE VOCABULARY SET name = ?, "
                + " teacher_id = ?, maxStudents= ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getCourseName());
            statement.setInt(2, obj.getTeacher());
            statement.setInt(3, obj.getAvailableSlots());
            statement.setInt(4, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM VOCABULARY WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Vocabulary> getAll(){
        String sql = "SELECT * FROM VOCABULARY";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Vocabulary> vocabcourses = new ArrayList<>();
            while(resultSet.next()){
                vocabcourses.add(extractFromResultSet(resultSet));
            }
            return vocabcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Vocabulary extractFromResultSet(ResultSet resultSet) throws SQLException {
        Vocabulary vocabulary=new Vocabulary(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"),resultSet.getInt("maxStudents"));
        return vocabulary;
    }
}
