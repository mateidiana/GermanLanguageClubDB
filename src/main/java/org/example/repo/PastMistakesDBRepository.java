
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.PastMistake;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PastMistakesDBRepository extends DBRepository<PastMistake> {
    public PastMistakesDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(PastMistake obj) {
        String sql = "INSERT INTO pastmistakes(id, student_id, question, answer, reading_id, grammar_id) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getId());
            statement.setInt(2,obj.getStudentId());
            statement.setString(3, obj.getPastMistake());
            statement.setString(4, obj.getRightAnswer());
            statement.setInt(5, obj.getReadingId());
            statement.setInt(6, obj.getGrammarId());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PastMistake read(int id) {
        String sql = "SELECT * FROM pastmistakes WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void update(PastMistake obj) {
        String sql = "UPDATE pastmistakes SET question = ?, student_id=?, answer = ?, reading_id = ?, grammar_id = ?, reading_exam_id = ?, grammar_exam_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getPastMistake());
            statement.setInt(2, obj.getStudentId());
            statement.setString(3, obj.getRightAnswer());
            statement.setInt(4, obj.getReadingId());
            statement.setInt(5, obj.getGrammarId());
            statement.setInt(6, obj.getReadingExamId());
            statement.setInt(7, obj.getGrammarExamId());
            statement.setInt(8, obj.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM pastmistakes WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<PastMistake> getAll(){
        String sql = "SELECT * FROM pastmistakes";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<PastMistake> questioncourses = new ArrayList<>();
            while(resultSet.next()){
                questioncourses.add(extractFromResultSet(resultSet));
            }
            return questioncourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private PastMistake extractFromResultSet(ResultSet resultSet) throws SQLException {
        PastMistake question=new PastMistake(resultSet.getInt("id"),resultSet.getString("question"),resultSet.getString("answer"),resultSet.getInt("student_id"));
        question.setGrammarId(resultSet.getInt("grammar_id"));
        question.setReadingId(resultSet.getInt("reading_id"));
        return question;
    }

}
