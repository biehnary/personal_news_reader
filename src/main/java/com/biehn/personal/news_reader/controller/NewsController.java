package com.biehn.personal.news_reader.controller;

import com.biehn.personal.news_reader.service.NewsService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {
  private final NewsService newsService;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/news")
  public String news(Model model,
      @RequestParam(value = "date", required = false) LocalDate localDate) throws Exception{
    newsService.syncNews();
    model.addAttribute("pageViewModel",newsService.getNewsPage(localDate));
    return "news";
  }
}
