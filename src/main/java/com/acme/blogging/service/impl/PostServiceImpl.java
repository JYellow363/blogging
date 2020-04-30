package com.acme.blogging.service.impl;

import com.acme.blogging.exception.ResourceNotFoundException;
import com.acme.blogging.model.Post;
import com.acme.blogging.model.Tag;
import com.acme.blogging.repository.PostRepository;
import com.acme.blogging.repository.TagRepository;
import com.acme.blogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Post assignPostTag(Long postId, Long tagId){
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",tagId));

        return postRepository.findById(postId).map(post->{
            if(!post.getTags().contains(tag)){
                post.getTags().add(tag);
                return postRepository.save(post);
            }
            return post;
        }).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
    }

    @Override
    public Post unassignPostTag(Long postId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));

        return postRepository.findById(postId).map(post -> {
            post.getTags().remove(tag);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
    }

    @Override
    public Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable) {
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<?> deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    @Override
    public Post updatePost(Long postId, Post postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
