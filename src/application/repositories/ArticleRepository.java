package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
