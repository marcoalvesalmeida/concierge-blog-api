package com.concierge.apiblog.Repositories;

import com.concierge.apiblog.Models.Author;
import com.concierge.apiblog.Models.Category;
import com.concierge.apiblog.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByIdIsNotNullOrderByIdAsc();

    List<Post> findPostsByIdIsNotNullOrderByIdAsc(Pageable pageable);
    List<Post> findPostsByIdIsNotNullOrderByIdDesc(Pageable pageable);

    List<Post> findPostsByIdIsNotNullOrderByViewsAsc(Pageable pageable);
    List<Post> findPostsByIdIsNotNullOrderByViewsDesc(Pageable pageable);

    List<Post> findPostsByAuthorIsOrderByViewsDesc(Pageable pageable, Author author);
    List<Post> findPostsByCategoriesIsContainingOrderByViewsDesc(Pageable pageable, Category category);
    List<Post> findPostsByCategoriesIsContainingAndAuthorIsOrderByViewsDesc(Pageable pageable, Category category, Author author);

    List<Post> findPostsByTitleContainsOrResumeContainsOrContentContains(String search, String search2, String search3);

    boolean existsById(Post post);
}