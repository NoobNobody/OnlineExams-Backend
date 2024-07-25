package com.nobody.repository;

import com.nobody.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    Page<Result> findByUserId(Integer userId, Pageable pageable);

}
