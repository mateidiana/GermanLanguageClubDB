
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Grammar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrammarDBRepository extends DBRepository<Grammar> {
    public GrammarDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Grammar obj) {
        String sql = "INSERT INTO GRAMMAR(id, name, " +
                " teacher_id, max_students) VALUES(?, ?, ?, ?)";
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
    public Grammar read(int id) {
        String sql = "SELECT * FROM GRAMMAR WHERE id = ?";

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
    public void update(Grammar obj) {
        String sql = "UPDATE GRAMMAR SET name = ?, "
                + " teacher_id = ?, max_students= ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(2, obj.getCourseName());
            statement.setInt(3, obj.getTeacher());
            statement.setInt(4, obj.getAvailableSlots());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM GRAMMAR WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Grammar> getAll(){
        String sql = "SELECT * FROM GRAMMAR";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Grammar> grammarcourses = new ArrayList<>();
            while(resultSet.next()){
                grammarcourses.add(extractFromResultSet(resultSet));
            }
            return grammarcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Grammar extractFromResultSet(ResultSet resultSet) throws SQLException {
        Grammar grammar=new Grammar(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("teacher_id"),resultSet.getInt("max_students"));
        return grammar;
    }
}
