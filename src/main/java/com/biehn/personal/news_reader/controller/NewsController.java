package com.biehn.personal.news_reader.controller;

import com.biehn.personal.news_reader.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
  private final NewsService newsService;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/news")
  public String news(Model model) throws Exception{
    model.addAttribute("sectionViewModels",newsService.getNews());
    return "news";
  }
}
