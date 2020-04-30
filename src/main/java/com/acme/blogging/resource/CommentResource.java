package com.acme.blogging.resource;


import com.acme.blogging.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResource extends AuditModel {
    private Long id;
    private String text;
}
