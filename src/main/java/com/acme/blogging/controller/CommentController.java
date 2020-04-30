package com.acme.blogging.controller;

import com.acme.blogging.model.Comment;
import com.acme.blogging.resource.CommentResource;
import com.acme.blogging.resource.SaveCommentResource;
import com.acme.blogging.resource.converter.CommentConverter;
import com.acme.blogging.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentConverter mapper;
    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResource> getAllCommentsByPostId(
            @PathVariable(name = "postId") Long postId,
            Pageable pageable) {
        Page<Comment> commentPage = commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent().stream().map(mapper::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource getCommentByIdAndPostId(@PathVariable(name = "postId") Long postId,
                                           @PathVariable(name = "commentId") Long commentId) {
        return mapper.convertToResource(commentService.getCommentByIdAndPostId(postId, commentId));
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResource createComment(@PathVariable(name = "postId") Long postId,
                                 @Valid @RequestBody SaveCommentResource resource) {
        return mapper.convertToResource(commentService.createComment(postId, mapper.convertToEntity(resource)));

    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource updateComment(@PathVariable(name = "postId") Long postId,
                                 @PathVariable(name = "commentId") Long commentId,
                                 @Valid @RequestBody SaveCommentResource resource) {
        return mapper.convertToResource(commentService.updateComment(postId, commentId, mapper.convertToEntity(resource)));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "postId") Long postId,
                                           @PathVariable(name = "commentId") Long commentId) {
        return commentService.deleteComment(postId, commentId);
    }



}
