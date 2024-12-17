
package org.example.repo;
import org.example.model.BookBelongsToCourse;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Book;
import org.example.model.Reading;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBelongsToCourseDBRepository extends DBRepository<BookBelongsToCourse> {
    public BookBelongsToCourseDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(BookBelongsToCourse obj) {
        String sql = "INSERT INTO belongstoreading(id, reading_id, book_id) " +
                "  VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setInt(2, obj.getReading());
            statement.setInt(3, obj.getBook());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public BookBelongsToCourse read(int id) {
        String sql = "SELECT * FROM belongstoreading WHERE id = ?";

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
    public void update(BookBelongsToCourse obj) {
        String sql = "UPDATE belongstoreading SET reading_id = ?, "
                + " book_id = ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getReading());
            statement.setInt(2, obj.getBook());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM belongstoreading WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<BookBelongsToCourse> getAll(){
        String sql = "SELECT * FROM belongstoreading";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<BookBelongsToCourse> bookcourses = new ArrayList<>();
            while(resultSet.next()){
                bookcourses.add(extractFromResultSet(resultSet));
            }
            return bookcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private BookBelongsToCourse extractFromResultSet(ResultSet resultSet) throws SQLException {
        BookBelongsToCourse thing=new BookBelongsToCourse(resultSet.getInt("id"),resultSet.getInt("reading_id"),resultSet.getInt("book_id"));
        return thing;
    }
}
