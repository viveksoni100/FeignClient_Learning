package com.mightyjava.controller;

import com.mightyjava.config.MessageConfig;
import com.mightyjava.dto.Book;
import com.mightyjava.feign.BookFeignClient;
import com.mightyjava.utils.ConstantUtils;
import com.mightyjava.utils.ErrorUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("book")
public class BookController {
	
	private String BOOK_REST_URL = ConstantUtils.BOOK_REST_URL;

	@Autowired
	private BookFeignClient bookFeignClient;

	@GetMapping("/form")
	public String bookForm(Model model) {
		model.addAttribute("isNew", true);
		model.addAttribute("bookForm", new Book());
		return "/book/form";
	}

	@GetMapping("/list")
	@Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public String bookList(Model model, Pageable pageable) {
		model.addAttribute("books", bookFeignClient.bookList().getBody());
		return "/book/list";
	}
	
	@Recover
	private String recoverBookList(Throwable throwable) {
		System.out.println(throwable.getMessage());
		return "/book/error";
	}

	@GetMapping("/edit/{id}")
	public String bookOne(@PathVariable Long id, Model model) {
		model.addAttribute("isNew", false);
		model.addAttribute("bookForm", bookFeignClient.bookOne(id).getBody());
		return "/book/form";
	}
	
	@Autowired
	private MessageConfig messageConfig;
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String bookAdd(@Valid @RequestBody Book book, BindingResult result) throws JSONException {
		if(result.hasErrors()) {
			return ErrorUtils.customErrors(result.getAllErrors());
		} else {
			String message = null;
			ResponseEntity<Book> response = null;
			JSONObject jsonObject = new JSONObject();
			
			HttpEntity<Book> request = new HttpEntity<Book>(book);
			if(book.getId() == null) {
				message = messageConfig.getMessage("label.added");
				response = bookFeignClient.bookAdd(book);
			} else {
				message = messageConfig.getMessage("label.updated");
				response = bookFeignClient.bookAdd(book);
			}
			jsonObject.put("status", "success");
			
			String[] msg = {message};
			jsonObject.put("title", messageConfig.getMessage("label.confirmation", msg));
			
			Book respBook = response.getBody();
			String[] msg2 = {respBook.getTitle(), message};
			jsonObject.put("message", messageConfig.getMessage("book.save.success", msg2));
			return jsonObject.toString();
		}
	}
	
	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String bookDelete(@PathVariable Long id) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if(bookFeignClient.bookDelete(id).getBody() != null) {
			jsonObject.put("message", messageConfig.getMessage("book.delete.success"));
		}
		return jsonObject.toString();
	}
}
