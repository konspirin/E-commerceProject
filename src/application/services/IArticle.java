package application.services;

import java.util.List;

import application.api.ReturnCode;
import application.dto.ArticleDto;
import application.dto.ArticleInfoDto;

public interface IArticle {

	ReturnCode addArticle(ArticleDto articleDto);
	ArticleDto getArticleById(int id);
	List<ArticleInfoDto> getAllArticlesInfo();
	ReturnCode removeArticle(int id);
	ReturnCode updateArticle(ArticleDto updArticle);
}
