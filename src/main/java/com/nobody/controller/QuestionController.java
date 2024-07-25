package com.nobody.controller;

import com.nobody.model.Question;
import com.nobody.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("allQuestions")
    @Operation(summary = "Get a list of questions", description = "Return all questions")
    public ResponseEntity<Page<Question>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.getAllQuestions(page, size);
    }

    @GetMapping("category")
    @Operation(summary = "Get All Categories", description = "Return all categories")
    public ResponseEntity<Page<String>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.getAllCategories(page, size);
    }

    @GetMapping("diffLevel")
    @Operation(summary = "Get DifficultyLevel", description = "Return all difficulty levels")
    public ResponseEntity<Page<String>> getDifficultyLevels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.getDifficultyLevels(page, size);
    }

    @GetMapping("category/{category}")
    @Operation(summary = "Get Questions by Category", description = "Return all questions by exam category")
    public ResponseEntity<Page<Question>> getQuestionByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.getQuestionsByCategory(category, page, size);
    }

    @PostMapping("add")
    @Operation(summary = "Add Question", description = "Add new question")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Update Question", description = "Update question in exam")
    public ResponseEntity<Question> updateQuestionById(
            @PathVariable Integer id,
            @RequestBody Question updatedQuestion) {
        return questionService.updateQuestionById(id, updatedQuestion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Question", description = "Delete a question by ID")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Integer id) {
        return questionService.deleteQuestionById(id);
    }

}
