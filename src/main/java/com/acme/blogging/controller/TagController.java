package com.acme.blogging.controller;

import com.acme.blogging.model.Tag;
import com.acme.blogging.resource.SaveTagResource;
import com.acme.blogging.resource.TagResource;
import com.acme.blogging.resource.converter.TagConverter;
import com.acme.blogging.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TagController{
    @Autowired
    private TagConverter mapper;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Page<TagResource> getAllTags(Pageable pageable){
        List<TagResource> tags = tagService
                .getAllTags(pageable)
                .getContent()
                .stream()
                .map(mapper::convertToResource)
                .collect(Collectors.toList());
        int tagsCount = tags.size();
        return new PageImpl<>(tags, pageable, tagsCount);
    }

    @GetMapping("/posts/{postId}/tags")
    public Page<TagResource> getAllTagsByPostId(@PathVariable(name="postId") Long postId, Pageable pageable){
        List<TagResource> tags = tagService
                .getAllTagsByPostId(postId, pageable)
                .getContent()
                .stream()
                .map(mapper::convertToResource)
                .collect(Collectors.toList());
        int tagCount = tags.size();
        return new PageImpl<>(tags, pageable, tagCount);
    }

    @GetMapping("/tags/{id}")
    public TagResource getTagById(@PathVariable(name="id") Long tagId)
    {
        return mapper.convertToResource(tagService.getTagById(tagId));
    }

    @PostMapping("/tags")
    public TagResource createTag(@Valid @RequestBody SaveTagResource resource)
    {
        return mapper.convertToResource(tagService.createTag(mapper.convertToEntity(resource)));
    }
}
