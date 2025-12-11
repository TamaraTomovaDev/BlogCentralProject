package org.intecbrussel.repository;

import com.example.blogcentral.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    // newest
    Page<BlogPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // oldest
    Page<BlogPost> findAllByOrderByCreatedAtAsc(Pageable pageable);

    // popular
    Page<BlogPost> findAllByOrderByLikesDesc(Pageable pageable);
}
