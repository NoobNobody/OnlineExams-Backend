package com.nobody.repository;

import com.nobody.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT DISTINCT q.category FROM question q", nativeQuery = true)
    Page<String> findAllCategories(Pageable pageable);

    @Query(value = "SELECT DISTINCT q.difficulty_level FROM question q", nativeQuery = true)
    Page<String> findDifficultyLevel(Pageable pageable);

    Page<Question> findByCategory(String category, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM question q WHERE q.category = :category", nativeQuery = true)
    int countByCategory(@Param("category") String category);

    @Query(value = "SELECT COUNT(DISTINCT q.category) FROM question q WHERE q.category = :category", nativeQuery = true)
    int countCategories(@Param("category") String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category AND q.difficulty_level=:difficultyLevel ORDER BY RANDOM() LIMIT :qNumber", nativeQuery = true)
    List<Question> findRandomQuestionsByCategoryAndDifficulty(@Param("category") String category, @Param("difficultyLevel") String difficultyLevel, @Param("qNumber") int qNumber);

    @Query(value = "SELECT COUNT(*) FROM question q WHERE q.category=:category AND q.difficulty_level=:difficultyLevel", nativeQuery = true)
    int countByCategoryAndDifficulty(@Param("category") String category, @Param("difficultyLevel") String difficultyLevel);

}
