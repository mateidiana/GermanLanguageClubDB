
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.PastWordMistake;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PastWordMistakesDBRepository extends DBRepository<PastWordMistake> {
    public PastWordMistakesDBRepository(String dbUrl, String dbUser, String dbPassPastWordMistake) {
        super(dbUrl, dbUser, dbPassPastWordMistake);
    }

    @Override
    public void create(PastWordMistake obj) {
        String sql = "INSERT INTO PastWordMistakes(id,student_id, word, meaning, vocabulary_id)" +
                " VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(3, obj.getWord());
            statement.setString(4, obj.getMeaning());
            statement.setInt(5, obj.getVocabId());
            statement.setInt(2, obj.getStudentId());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public PastWordMistake read(int id) {
        String sql = "SELECT * FROM PastWordMistakes WHERE id = ?";

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
    public void update(PastWordMistake obj) {
        String sql = "UPDATE PastWordMistakes SET word = ?, meaning = ?, vocabulary_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getWord());
            statement.setString(2, obj.getMeaning());
            statement.setInt(3, obj.getVocabId());
            statement.setInt(4, obj.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM PastWordMistakes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<PastWordMistake> getAll() {
        String sql = "SELECT * FROM PastWordMistakes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<PastWordMistake> PastWordMistakeList = new ArrayList<>();
            while (resultSet.next()) {
                PastWordMistakeList.add(extractFromResultSet(resultSet));
            }
            return PastWordMistakeList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private PastWordMistake extractFromResultSet(ResultSet resultSet) throws SQLException {
        PastWordMistake PastWordMistake=new PastWordMistake(resultSet.getInt("id"),resultSet.getString("word"),resultSet.getString("meaning"),resultSet.getInt("student_id"));
        PastWordMistake.setVocabId(resultSet.getInt("vocabulary_id"));
        return PastWordMistake;
    }
}
