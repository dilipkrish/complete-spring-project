package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.services.BookService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@RestController
@RequestMapping("/api/book")
@Api(value = "books", description = "Books API")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @Autowired
    private BookService bookService;

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Books", notes = "Returns all books")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one book at least")
    })
    public ResponseEntity<List<BookResource>> getAllBooks() {
        List books = bookService.findAll();

        List<BookResource> resourceList = bookResourceAssembler.toResources(books);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation(value = "Get one Book", notes = "Returns one book")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exists this book")
    })
    public ResponseEntity<BookResource> getBook(@PathVariable long id) {
        Book book = bookService.findOne(id);

        BookResource resource = bookResourceAssembler.toResource(book);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create Book", notes = "Create a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful create of a book")
    })
    public void createBook(@RequestBody Book book) {
        Book bookCreated = bookService.create(book);

        log.info("Book created: " + bookCreated.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ApiOperation(value = "Update Book", notes = "Update a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful update of book")
    })
    public void updateBook(@PathVariable long id, @RequestBody Book book) {
        Book dbBook = bookService.findOne(id);
        dbBook = bookService.save(dbBook.copyFrom(book));

        log.info("Book updated: " + dbBook.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation(value = "Delete Book", notes = "Delete a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful delete of a book")
    })
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);

        log.info("Book deleted: " + id);
    }
}
