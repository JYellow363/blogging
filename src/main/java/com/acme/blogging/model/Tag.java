package com.acme.blogging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode (callSuper = true)
@Table(name="tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max=100)
    @NaturalId
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy="tags"
    )

    @JsonIgnore
    private List<Post> posts;
}
