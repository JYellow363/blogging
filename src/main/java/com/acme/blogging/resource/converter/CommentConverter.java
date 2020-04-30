package com.acme.blogging.resource.converter;

import com.acme.blogging.model.Comment;
import com.acme.blogging.resource.CommentResource;
import com.acme.blogging.resource.SaveCommentResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final ModelMapper modelMapper;

    public Comment convertToEntity(SaveCommentResource resource) {
        return modelMapper.map(resource, Comment.class);
    }

    public CommentResource convertToResource(Comment entity) {
        return modelMapper.map(entity, CommentResource.class);
    }

}
