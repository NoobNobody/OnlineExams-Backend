package com.nobody.service;

import com.nobody.model.Question;
import com.nobody.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<Page<Question>> getAllQuestions(int page, int size){
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<Question> questions = questionRepository.findAll(pageable);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch(Exception e){
            logger.error("Error fetching users", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<String>> getAllCategories(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("category").ascending());
            Page<String> categories = questionRepository.findAllCategories(pageable);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Error fetching all categories", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<String>> getDifficultyLevels(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("difficulty_level"));
            Page<String> diffLevel = questionRepository.findDifficultyLevel(pageable);
            return new ResponseEntity<>(diffLevel, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Error fetching difficulty levels", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<Question>> getQuestionsByCategory(String category, int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by("category").ascending());
            Page<Question> questions = questionRepository.findByCategory(category, pageable);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid category: {}", category, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error fetching questions by category", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding Question", e);
            return new ResponseEntity<>("Error adding question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Question> updateQuestionById(Integer id, Question updatedQuestion) {
        try {
            return questionRepository.findById(id)
                    .map(existingQuestion -> {
                        if (updatedQuestion.getQuestionTitle() != null) {
                            existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
                        }
                        if (updatedQuestion.getOption1() != null) {
                            existingQuestion.setOption1(updatedQuestion.getOption1());
                        }
                        if (updatedQuestion.getOption2() != null) {
                            existingQuestion.setOption2(updatedQuestion.getOption2());
                        }
                        if (updatedQuestion.getOption3() != null) {
                            existingQuestion.setOption3(updatedQuestion.getOption3());
                        }
                        if (updatedQuestion.getOption4() != null) {
                            existingQuestion.setOption4(updatedQuestion.getOption4());
                        }
                        if (updatedQuestion.getRightAnswer() != null) {
                            existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
                        }
                        if (updatedQuestion.getDifficultyLevel() != null) {
                            existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
                        }
                        if (updatedQuestion.getCategory() != null) {
                            existingQuestion.setCategory(updatedQuestion.getCategory());
                        }
                        Question savedQuestion = questionRepository.save(existingQuestion);
                        return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            logger.error("Error updating question with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteQuestionById(Integer id) {
        try{
            if (questionRepository.existsById(id)){
                questionRepository.deleteById(id);
                return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting question with id: {}", id, e);
            return new ResponseEntity<>("Error deleting question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
