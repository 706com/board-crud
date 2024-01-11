package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();   // Iterable -> ArrayList 반환값 오버라이딩
}
