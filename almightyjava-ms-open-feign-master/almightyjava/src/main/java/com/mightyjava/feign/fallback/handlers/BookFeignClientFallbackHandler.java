package com.mightyjava.feign.fallback.handlers;

import com.mightyjava.feign.BookFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

/**
 * @author viveksoni100
 */
public class BookFeignClientFallbackHandler {

    @Autowired
    private BookFeignClient bookFeignClient;

    public String bookListFallBack(Model model, Pageable pageable) {
        model.addAttribute("message", "Service not available!");
        return "/book/error";
    }
}
