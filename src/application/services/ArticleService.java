package application.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.api.ReturnCode;
import application.dto.ArticleDto;
import application.dto.ArticleInfoDto;
import application.entities.Article;
import application.repositories.ArticleRepository;

@Service
public class ArticleService implements IArticle{
	
	@Autowired
	ArticleRepository articleRepo;
	
	//*****************MAPPERS**********************
	
	private ArticleDto articleToArticleDtoMapper(Article article) {
		ArticleDto art = new ArticleDto(article.getId(), article.getTitle(), article.getBody(),
				article.getImgUrl(), article.getThumbImgUrl()
//				, article.getDateModified()
				);
		return art;
	}
	
	private ArticleInfoDto articleToArticleInfoDtoMapper(Article article) {
		ArticleInfoDto art = new ArticleInfoDto(article.getId(), article.getTitle(),
				article.getThumbImgUrl(), article.getDateModified());
		return art;
	}
	
	@Transactional
	public ReturnCode addArticle(ArticleDto articleDto) {
		if(articleRepo.existsById(articleDto.getId()))
			return ReturnCode.THIS_ID_ALREADY_EXISTS;
		Article article = new Article(articleDto.getId(), articleDto.getTitle(), articleDto.getBody(),
				articleDto.getImgUrl(), articleDto.getThumbImgUrl(), LocalDate.now());
		articleRepo.save(article);
		return ReturnCode.OK;
	}
	
	public ArticleDto getArticleById(int id) {	
		
				Article article = articleRepo.findById(id).orElse(null);
				if(article!=null)
				return articleToArticleDtoMapper(article);
				else return null;
	}

	public List<ArticleInfoDto> getAllArticlesInfo(){
		List<Article> list = articleRepo.findAll();
		List<ArticleInfoDto> result = new ArrayList<>();
		list.forEach(a -> result.add(articleToArticleInfoDtoMapper(a)));
		return result;
	}

	public ReturnCode removeArticle(int id) {
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
		Article article = new Article(updArticle.getId(), updArticle.getTitle(), updArticle.getBody(),
				updArticle.getImgUrl(), updArticle.getThumbImgUrl(), LocalDate.now());
		articleRepo.save(article);
		return ReturnCode.OK;
		
	}
}
