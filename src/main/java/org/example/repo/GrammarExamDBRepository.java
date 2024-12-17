
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.GrammarExam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrammarExamDBRepository extends DBRepository<GrammarExam> {
    public GrammarExamDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(GrammarExam obj) {
        String sql = "INSERT INTO GRAMMAREXAM(id, name, " +
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
    public GrammarExam read(int id) {
        String sql = "SELECT * FROM GRAMMAREXAM WHERE id = ?";

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
    public void update(GrammarExam obj) {
        String sql = "UPDATE GRAMMAREXAM SET name = ?, "
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
        String sql = "DELETE FROM GRAMMAREXAM WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<GrammarExam> getAll(){
        String sql = "SELECT * FROM GRAMMAREXAM";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<GrammarExam> grammarcourses = new ArrayList<>();
            while(resultSet.next()){
                grammarcourses.add(extractFromResultSet(resultSet));
            }
            return grammarcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private GrammarExam extractFromResultSet(ResultSet resultSet) throws SQLException {
        GrammarExam grammar=new GrammarExam(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"));
        return grammar;
    }
}
