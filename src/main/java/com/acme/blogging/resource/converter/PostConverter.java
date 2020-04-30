package com.acme.blogging.resource.converter;

import com.acme.blogging.model.Post;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostConverter {

    private final ModelMapper modelMapper;

    public Post convertToEntity(SavePostResource resource) {
        return modelMapper.map(resource, Post.class);
    }

    public PostResource convertToResource(Post entity) {
        return modelMapper.map(entity, PostResource.class);
    }
}
