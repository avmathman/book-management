package com.book.management.application.service.book;

import com.book.management.application.mapper.BookMapper;
import com.book.management.application.model.BookModel;
import com.book.management.domain.book.BookEntity;
import com.book.management.infrastructure.repository.BookRepository;
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
    public BookModel updateBook(BookModel bookModel) {
        BookEntity updated = repository.save(mapper.modelToEntity(bookModel));
        return mapper.entityToModel(updated);
    }

    @Override
    public void deleteBook(Long bookId) {
        repository.deleteById(bookId);
    }

    @Override
    public BookModel getBook(Long bookId) {
        return mapper.entityToModel(repository.findById(bookId).orElse(null));
    }

    @Override
    public List<BookModel> getAllBooks() {
        return mapper.entitiesToModels(repository.findAll());
    }
}
