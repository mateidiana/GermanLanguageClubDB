
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Word;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDBRepository extends DBRepository<Word> {
    public WordDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Word obj) {
        String sql = "INSERT INTO WORD(id, word, meaning, vocabulary_id, vocabulary_exam_id)" +
                " VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getWord());
            statement.setString(3, obj.getMeaning());
            statement.setInt(4, obj.getVocabId());
            statement.setInt(5, obj.getVocabExamId());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public Word read(int id) {
        String sql = "SELECT * FROM word WHERE id = ?";

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
    public void update(Word obj) {
        String sql = "UPDATE word SET word = ?, meaning = ?, vocabulary_id = ?, vocabulary_exam_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getWord());
            statement.setString(2, obj.getMeaning());
            statement.setInt(3, obj.getVocabId());
            statement.setInt(4, obj.getVocabExamId());
            statement.setInt(5, obj.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM word WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Word> getAll() {
        String sql = "SELECT * FROM word";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Word> wordList = new ArrayList<>();
            while (resultSet.next()) {
                wordList.add(extractFromResultSet(resultSet));
            }
            return wordList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Word extractFromResultSet(ResultSet resultSet) throws SQLException {
        Word word=new Word(resultSet.getInt("id"),resultSet.getString("word"),resultSet.getString("meaning"));
        word.setVocabId(resultSet.getInt("vocabulary_id"));
        word.setVocabExamId(resultSet.getInt("vocabulary_exam_id"));
        return word;
    }
}
