package com.concierge.apiblog.Repositories;

import com.concierge.apiblog.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByIdIsNotNullOrderByIdAsc(Pageable pageable);
    List<Post> findPostsByIdIsNotNullOrderByIdDesc(Pageable pageable);

    List<Post> findPostsByIdIsNotNullOrderByViewsAsc(Pageable pageable);
    List<Post> findPostsByIdIsNotNullOrderByViewsDesc(Pageable pageable);

}