package com.acme.blogging.service.impl;

import com.acme.blogging.exception.ResourceNotFoundException;
import com.acme.blogging.model.Comment;
import com.acme.blogging.repository.CommentRepository;
import com.acme.blogging.repository.PostRepository;
import com.acme.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getCommentByIdAndPostId(Long postId, Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with Id " + commentId +
                                " and PostId " + postId));
    }

    @Transactional
    @Override
    public Comment createComment(Long postId, Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "Id", postId));

    }

    @Transactional
    @Override
    public Comment updateComment(Long postId, Long commentId, Comment commentDetails) {
        if(!postRepository.existsById(postId))
            throw new ResourceNotFoundException("Post", "Id", postId);

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentDetails.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Comment not found with Id " + commentId + " and PostId " + postId));

    }
}
