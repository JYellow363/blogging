package com.acme.blogging.controller;

import com.acme.blogging.model.Post;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import com.acme.blogging.resource.converter.PostConverter;
import com.acme.blogging.service.PostService;
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
public class PostController {

    @Autowired
    private PostConverter mapper;

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostResource> resources = postsPage.getContent().stream().map(mapper::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/posts/{id}")
    public PostResource getPostById(@PathVariable(name = "id") Long postId) {
        return mapper.convertToResource(postService.getPostById(postId));
    }

    @PostMapping("/posts")
    public PostResource createPost(@Valid @RequestBody SavePostResource resource)  {
        Post post = mapper.convertToEntity(resource);
        return mapper.convertToResource(postService.createPost(post));
    }

    @PutMapping("/posts/{id}")
    public PostResource updatePost(@PathVariable(name = "id") Long postId, @Valid @RequestBody SavePostResource resource) {
        Post post = mapper.convertToEntity(resource);
        return mapper.convertToResource(postService.updatePost(postId, post));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/tags/{tagId}/posts")
    public Page<PostResource> getAllPostsByTagId(@PathVariable(name = "tagId") Long tagId, Pageable pageable) {
        Page<Post> postsPage = postService.getAllPostsByTagId(tagId, pageable);
        List<PostResource> resources = postsPage.getContent().stream().map(mapper::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/posts/{postId}/tags/{tagId}")
    public PostResource assignPostTag(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "tagId") Long tagId) {
        return mapper.convertToResource(postService.assignPostTag(postId, tagId));
    }

    @DeleteMapping("/posts/{postId}/tags/{tagId}")
    public PostResource unassignPostTag(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "tagId") Long tagId) {

        return mapper.convertToResource(postService.unassignPostTag(postId, tagId));
    }



}
