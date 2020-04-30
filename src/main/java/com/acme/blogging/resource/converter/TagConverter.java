package com.acme.blogging.resource.converter;

import com.acme.blogging.model.Tag;
import com.acme.blogging.resource.SaveTagResource;
import com.acme.blogging.resource.TagResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class TagConverter{

    private final ModelMapper modelMapper;

    public Tag convertToEntity(SaveTagResource resource){
        return modelMapper.map(resource, Tag.class);
    }

    public TagResource convertToResource(Tag entity){
        return modelMapper.map(entity, TagResource.class);
    }
}