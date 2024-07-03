package com.book.management.application.service.book;

import com.book.management.application.exception.ItemNotFoundException;
import com.book.management.application.mapper.BookMapper;
import com.book.management.application.model.BookModel;
import com.book.management.domain.book.BookEntity;
import com.book.management.infrastructure.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookServiceImpl(
            BookRepository repository,
            BookMapper bookMapper
    ) {
        this.repository = repository;
        this.mapper = bookMapper;
    }

    @Override
    public BookModel createBook(BookModel model) {
        BookEntity created = repository.save(mapper.modelToEntity(model));
        return mapper.entityToModel(created);
    }

    @Override
    @Transactional
    public BookModel updateBook(BookModel bookModel) {
        BookEntity updated = repository
                .update(mapper.modelToEntity(bookModel))
                .orElseThrow(() -> new ItemNotFoundException("Book with given id=" + bookModel.getId() + " does not exist!"));

        return mapper.entityToModel(updated);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        int result = repository.deleteById(bookId);

        if (result == 0) {
            throw new ItemNotFoundException("Book with given id=" + bookId + " does not exist!");
        }
    }

    @Override
    @Transactional
    public void deleteAllBooks() {
        repository.deleteAllBooks();
    }

    @Override
    public BookModel getBook(Long bookId) {
        BookEntity current = repository.findById(bookId)
                .orElseThrow(() -> new ItemNotFoundException("Book with given id=" + bookId + " does not exist!"));

        return mapper.entityToModel(current);
    }

    @Override
    public List<BookModel> getAllBooks() {
        return mapper.entitiesToModels(repository.findAll());
    }

    @Override
    public List<BookModel> filterByTitle(String author) {
        return mapper.entitiesToModels(repository.filterByTitle(author));
    }
}
