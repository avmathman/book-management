package com.book.management.infrastructure.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.book.management.domain.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Data repository for users.
 */
@Repository
public class BookRepository implements CustomRepository<BookEntity> {

    private static final String INSERT_SQL = "insert into books (title, author, description) values (?,?,?)";
    private static final String UPDATE_SQL = "update books set title = ?, author = ?, description = ? where id = ?";
    private static final String DELETE_BY_ID_SQL = "delete from books where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BookEntity save(BookEntity book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            return ps;
        }, keyHolder);

        return new BookEntity(
                (Long) keyHolder.getKeys().get("id"),
                keyHolder.getKeys().get("title").toString(),
                keyHolder.getKeys().get("author").toString(),
                keyHolder.getKeys().get("description").toString());

    }

    @Override
    public Optional<BookEntity> update(BookEntity book) {
        int result = jdbcTemplate.update(UPDATE_SQL, book.getTitle(), book.getAuthor(), book.getDescription(), book.getId());

        if (result == 0) {
            return Optional.empty();
        }

        return this.findById(book.getId());
    }


    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }

    @Override
    public List<BookEntity> findAll() {
        return jdbcTemplate.query(
                "select * from books",
                (rs, rowNum) ->
                        new BookEntity(
                                rs.getLong("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("description")
                        )
        );
    }

    @Override
    public Optional<BookEntity> findById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from books where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new BookEntity(
                                rs.getLong("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("description")
                        ))
        );
    }
}
