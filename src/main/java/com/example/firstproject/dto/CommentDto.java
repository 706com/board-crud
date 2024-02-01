package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class CommentDto {

    private Long id;

//    @JsonProperty("article_id") //클라이언트가 쓸 요청 변수명
    private Long articleId;

    private String nickname;

    private String body;

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(c.getId(),c.getArticle().getId(),c.getNickname(),c.getBody());
    }
}
