package com.acme.blogging.service;

import com.acme.blogging.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable);
    Comment getCommentByIdAndPostId(Long postId, Long commentId);
    Comment createComment(Long postId, Comment comment);
    Comment updateComment(Long postId, Long commentId, Comment commentDetails);
    ResponseEntity<?> deleteComment(Long postId, Long commentId);

    }
