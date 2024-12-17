
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDBRepository extends DBRepository<Question> {
    public QuestionDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Question obj) {
        String sql = "INSERT INTO question(id, question, answer, reading_id, grammar_id, reading_exam_id, grammar_exam_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getQuestion());
            statement.setString(3, obj.getRightAnswer());
            statement.setInt(4, obj.getReadingId());
            statement.setInt(5, obj.getGrammarId());
            statement.setInt(6, obj.getReadingExamId());
            statement.setInt(7, obj.getGrammarExamId());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question read(int id) {
        String sql = "SELECT * FROM question WHERE id = ?";

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
    public void update(Question obj) {
        String sql = "UPDATE question SET question = ?, answer = ?, reading_id = ?, grammar_id = ?, reading_exam_id = ?, grammar_exam_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getQuestion());
            statement.setString(2, obj.getRightAnswer());
            statement.setInt(3, obj.getReadingId());
            statement.setInt(4, obj.getGrammarId());
            statement.setInt(5, obj.getReadingExamId());
            statement.setInt(6, obj.getGrammarExamId());
            statement.setInt(7, obj.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM question WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Question> getAll(){
        String sql = "SELECT * FROM question";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Question> questioncourses = new ArrayList<>();
            while(resultSet.next()){
                questioncourses.add(extractFromResultSet(resultSet));
            }
            return questioncourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Question extractFromResultSet(ResultSet resultSet) throws SQLException {
        Question question=new Question(resultSet.getInt("id"),resultSet.getString("question"),resultSet.getString("answer"));
        question.setGrammarExamId(resultSet.getInt("grammar_exam_id"));
        question.setGrammarId(resultSet.getInt("grammar_id"));
        question.setReadingId(resultSet.getInt("reading_id"));
        question.setReadingExamId(resultSet.getInt("reading_exam_id"));
        return question;
    }
}
