
package org.example.repo;
import org.example.model.Enrolled;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Book;
import org.example.model.Reading;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolledDBRepository extends DBRepository<Enrolled> {
    public EnrolledDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Enrolled obj) {
        String sql = "INSERT INTO enrolled(id, student_id, course_id) " +
                "  VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setInt(2, obj.getStudent());
            statement.setInt(3, obj.getCourse());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public Enrolled read(int id) {
        String sql = "SELECT * FROM enrolled WHERE id = ?";

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
    public void update(Enrolled obj) {
        String sql = "UPDATE enrolled SET student_id = ?, "
                + " course_id = ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getStudent());
            statement.setInt(2, obj.getCourse());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM enrolled WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Enrolled> getAll(){
        String sql = "SELECT * FROM enrolled";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Enrolled> idkanymore = new ArrayList<>();
            while(resultSet.next()){
                idkanymore.add(extractFromResultSet(resultSet));
            }
            return idkanymore;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Enrolled extractFromResultSet(ResultSet resultSet) throws SQLException {
        Enrolled thing=new Enrolled(resultSet.getInt("id"),resultSet.getInt("student_id"),resultSet.getInt("course_id"));
        return thing;
    }
}
