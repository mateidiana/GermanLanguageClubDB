
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.ExamResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamResultDBRepository extends DBRepository<ExamResult> {
    public ExamResultDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(ExamResult obj) {
        String sql = "INSERT INTO EXAMRESULT(id, exam_id, " +
                " student_id, result) VALUES(?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setInt(2, obj.getExam());
            statement.setInt(3, obj.getStudent());
            statement.setFloat(4, obj.getResult());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public ExamResult read(int id) {
        String sql = "SELECT * FROM EXAMRESULT WHERE id = ?";

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
    public void update(ExamResult obj) {
        String sql = "UPDATE EXAMRESULT SET     exam_id= ?, "
                + " student_id = ?, result= ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getExam());
            statement.setInt(2, obj.getStudent());
            statement.setFloat(3, obj.getResult());
            statement.setInt(4, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM EXAMRESULT WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<ExamResult> getAll(){
        String sql = "SELECT * FROM EXAMRESULT";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<ExamResult> grammarcourses = new ArrayList<>();
            while(resultSet.next()){
                grammarcourses.add(extractFromResultSet(resultSet));
            }
            return grammarcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private ExamResult extractFromResultSet(ResultSet resultSet) throws SQLException {
        ExamResult grammar=new ExamResult(resultSet.getInt("id"),resultSet.getInt("exam_id"),resultSet.getFloat("result"),resultSet.getInt("student_id"));
        return grammar;
    }
}
