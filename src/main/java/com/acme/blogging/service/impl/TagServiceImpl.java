package com.acme.blogging.service.impl;

import com.acme.blogging.exception.ResourceNotFoundException;
import com.acme.blogging.model.Tag;
import com.acme.blogging.repository.PostRepository;
import com.acme.blogging.repository.TagRepository;
import com.acme.blogging.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly=true)
    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Tag> getAllTagsByPostId(Long postId, Pageable pageable) {
        return postRepository.findById(postId).map(post->{
            List<Tag> tags=post.getTags();
            int tagsCount=tags.size();
            return new PageImpl<>(tags, pageable,tagsCount);
        }).orElseThrow(()->new ResourceNotFoundException("Post","Id","posId"));
    }

    @Transactional
    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id","posId"));
    }

    @Transactional
    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag updateTag(Long tagId, Tag tagDetails) {
        return tagRepository.findById(tagId).map(tag->{
            tag.setName(tagDetails.getName());
            return tagRepository.save(tag);
        }).orElseThrow(()->new ResourceNotFoundException("Tag","Id","postId"));

    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteTag(Long tagId) {
        return null;
    }
}
