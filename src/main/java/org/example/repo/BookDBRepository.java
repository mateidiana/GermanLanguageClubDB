
package org.example.repo;
import org.example.model.Exceptions.DatabaseException;

import org.example.model.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDBRepository extends DBRepository<Book> {
    public BookDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Book obj) {
        String sql = "INSERT INTO BOOK(id, title, " +
                " author) VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getTitle());
            statement.setString(3, obj.getAuthor());
            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public Book read(int id) {
        String sql = "SELECT * FROM BOOK WHERE id = ?";

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
    public void update(Book obj) {
        String sql = "UPDATE BOOK SET title = ?, "
                + " author = ? WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getTitle());
            statement.setString(2, obj.getAuthor());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }

    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM BOOK WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    @Override
    public List<Book> getAll(){
        String sql = "SELECT * FROM BOOK";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookcourses = new ArrayList<>();
            while(resultSet.next()){
                bookcourses.add(extractFromResultSet(resultSet));
            }
            return bookcourses;
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
    }

    private Book extractFromResultSet(ResultSet resultSet) throws SQLException {
        Book book=new Book(resultSet.getInt("id"),resultSet.getString("title"),resultSet.getString("author"));
        return book;
    }
}
