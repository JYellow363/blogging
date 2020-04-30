package com.acme.blogging.repository;

import com.acme.blogging.model.Post;
import com.acme.blogging.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
