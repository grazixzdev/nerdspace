package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaBookDetailed;
import br.com.lucena.nerdspace.dto.DataMediaBookList;
import br.com.lucena.nerdspace.model.Book;
import br.com.lucena.nerdspace.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/livro")
public class BookController {
    public final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(new DataMediaBookDetailed(book.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaBookList>> bookList(@PageableDefault Pageable pageable) {
        var page = bookRepository.findAllByActiveTrue(pageable)
                .map(DataMediaBookList::new);
        return ResponseEntity.ok(page);
    }
}
