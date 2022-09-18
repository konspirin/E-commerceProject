package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.api.ReturnCode;
import application.dto.ArticleDto;
import application.dto.ArticleInfoDto;
import application.services.IArticle;

@RestController
@CrossOrigin
public class ArticleController {

	@Autowired
	IArticle blog;
	
	@PostMapping(value = "/article_add")
	ReturnCode addArticle(@RequestBody ArticleDto articleDto) {
		return blog.addArticle(articleDto);
	}
	@GetMapping(value = "/article_get")
	ArticleDto getArticleById(@RequestParam int id) {
		return blog.getArticleById(id);
	}
	@GetMapping(value = "/all_articles_info_get")
	List<ArticleInfoDto> getAllArticlesInfo(){
		return blog.getAllArticlesInfo();
	}
	@DeleteMapping(value = "/article_remove")
	ReturnCode removeArticle(@RequestParam int id) {
		return blog.removeArticle(id);
	}
	@PostMapping(value = "/article_update")
	ReturnCode updateArticle(@RequestBody ArticleDto updArticle) {
		return blog.updateArticle(updArticle);
	}
	@PostMapping("/all_articles_add")
	ReturnCode addAllArticles(@RequestParam int number) {
		for(int i=0; i<number; i++)
		blog.addRandomArticle();
		return ReturnCode.OK;
	}
}
