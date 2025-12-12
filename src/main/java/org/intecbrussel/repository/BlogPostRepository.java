package org.intecbrussel.repository;

import org.intecbrussel.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Page<BlogPost> findAllByOrderByCreatedAtAsc(Pageable pageable);
    Page<BlogPost> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<BlogPost> findAllByOrderByLikesDesc(Pageable pageable);

}
