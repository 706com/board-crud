package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    // 특정 게시글의 모든 댓글 조회 : nativeQuery 사용
    @Query(value =
            "SELECT * "
            + "FROM comment "
            + "WHERE article_id = :articleId",
            nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회 : 네이티브 쿼리 XML 사용 (orm.xml)
    List<Comment> findByNickname(String nickname);
}
