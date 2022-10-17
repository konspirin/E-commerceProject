package application.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.api.ReturnCode;
import application.dto.ArticleDto;
import application.dto.ArticleInfoDto;
import application.entities.Article;
import application.exceptions.ProductNotFoundException;
import application.repositories.ArticleRepository;

@Service
public class ArticleService implements IArticle{
	
	@Autowired
	ArticleRepository articleRepo;
	
	//*****************MAPPERS**********************
	
	private ArticleDto articleToArticleDtoMapper(Article article) {
		ArticleDto art = new ArticleDto(article.getId(), article.getTitle(), article.getBody(),
				stringToListImages(article.getImages()), article.getThumbImg(),
				article.getTimestampDateMod());
		return art;
	}
	
	private List<String> stringToListImages(String images) {
		String[] temp = images.split(";");
		return Arrays.asList(temp);
	}

	private ArticleInfoDto articleToArticleInfoDtoMapper(Article article) {
		ArticleInfoDto art = new ArticleInfoDto(article.getId(), article.getTitle(),
				article.getThumbImg(), article.getTimestampDateMod());
		return art;
	}
	
	@Transactional
	public ReturnCode addArticle(ArticleDto articleDto) {
		if(articleRepo.existsById(articleDto.getId()))
			return ReturnCode.THIS_ID_ALREADY_EXISTS;
		
		articleRepo.save(articleDtoToArticle(articleDto));
		return ReturnCode.OK;
	}
	
	private Article articleDtoToArticle(ArticleDto articleDto) {
		Article article = new Article(articleDto.getId(), articleDto.getTitle(), articleDto.getBody(),
				listImagesToString(articleDto.getImages()),
				articleDto.getThumbImg(), articleDto.getTimestampDateMod());
		return article;
	}

	private String listImagesToString(List<String> images) {
		StringBuilder str = new StringBuilder();
		images.forEach(image -> str.append(image+";"));
		str.deleteCharAt(str.length()-1);
		return str.toString();
	}

	public ArticleDto getArticleById(long id) {	
		
				Article article = articleRepo.findById(id).orElse(null);
				if(article!=null)
				return articleToArticleDtoMapper(article);
				else throw new ProductNotFoundException("We can't find an article with ID "+id);
	}

	public List<ArticleInfoDto> getAllArticlesInfo(){
		List<Article> list = articleRepo.findAll();
		List<ArticleInfoDto> result = new ArrayList<>();
		list.forEach(a -> result.add(articleToArticleInfoDtoMapper(a)));
		return result;
	}

	public ReturnCode removeArticle(long id) {
		Article article = articleRepo.findById(id).orElse(null);
		if(article!=null) {
			articleRepo.delete(article);
			return ReturnCode.OK;
		}
		else return ReturnCode.ARTICLE_NOT_FOUND;
	}
	
	public ReturnCode updateArticle(ArticleDto updArticle) {
		if(!articleRepo.existsById(updArticle.getId()))
			return ReturnCode.ARTICLE_NOT_FOUND;
		articleRepo.save(articleDtoToArticle(updArticle));
		return ReturnCode.OK;
		
	}

	@Override
	public ReturnCode addRandomArticle() {
		articleRepo.save(articleDtoToArticle(ArticleDto.randomArticleDto()));
		return ReturnCode.OK;
	}


}
