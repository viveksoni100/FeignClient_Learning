package com.mightyjava.feign;

import com.mightyjava.dto.Book;
import com.mightyjava.utils.ConstantUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author viveksoni100
 */
@FeignClient(value = "book-rest-calls", url = ConstantUtils.BOOK_REST_URL)
public interface BookFeignClient {

    @GetMapping
    ResponseEntity<Collection<Book>> bookList();

    @GetMapping("/{id}")
    ResponseEntity<Book> bookOne(@PathVariable("id") Long id);

    @PostMapping
    ResponseEntity<Book> bookAdd(@RequestBody Book book);

    @PutMapping
    ResponseEntity<Book> bookUpdate(@RequestBody Book book);

    @DeleteMapping("/{id}")
    ResponseEntity<Book> bookDelete(@PathVariable("id") Long id);

}
