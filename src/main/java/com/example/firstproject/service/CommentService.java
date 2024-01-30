package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CommentService {
    @Autowired  //레포지터리 객체 주입
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    //댓글 조회
    public List<CommentDto> comments(Long articleId) {
//        // 1. 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        // 2. 엔티티 -> dto 변환
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for(int i=0; i<comments.size(); i++){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId).stream()
                .map(comment -> CommentDto.createCommentDto(comment)) // map(a->b) : 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑
                .collect(Collectors.toList());  // 스트림 데이터를 리스트 자료형으로 변환
    }

    //댓글 생성
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto,article);
        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        // 4. DTO 변환 후 반환
        return CommentDto.createCommentDto(created);
    }

    //댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정실패! : 대상 댓글이 없습니다."));
        // 2. 댓글 수정
        target.patch(dto);
        // 3. DB갱신
        Comment updated = commentRepository.save(target);
        // 4. 댓글 엔티티 -> DTO 변환 후 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제실패! : 대상 댓글이 없습니다."));
        // 2. 댓글 삭제
        commentRepository.delete(target);
        // 3. 삭제 댓글 엔티티 -> DTO 변환 후 반환
        return CommentDto.createCommentDto(target);
    }
}
