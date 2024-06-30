package com.book.management.domain.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The entity of the book.
 */
@Entity
@Table(name = "books")
@Getter
@Setter
public class BookEntity {

    /**
     * The book identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The book title.
     */
    @Column(name = "title")
    @NotNull
    private String title;

    /**
     * The book author.
     */
    @Column(name = "author")
    @NotNull
    private String author;

    /**
     * The book description.
     */
    @Column(name = "description")
    @NotNull
    private String description;
}
