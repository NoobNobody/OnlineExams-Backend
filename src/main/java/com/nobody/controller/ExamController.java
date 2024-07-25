package com.nobody.controller;

import com.nobody.model.QuestionWrapper;
import com.nobody.model.Response;
import com.nobody.model.Result;
import com.nobody.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService){
        this.examService = examService;
    }

    @PostMapping("create")
    @Operation(summary = "Create new exam", description = "Create exam")
    public ResponseEntity<String> createExam(
            @RequestParam String category,
            @RequestParam(defaultValue = "10") int qNumber,
            @RequestParam String title,
            @RequestParam String difficultyLevel) {

        if (qNumber != 5 && qNumber != 10 && qNumber != 15 && qNumber != 20) {
            return new ResponseEntity<>("Invalid number of questions. Valid options are 5, 10, 15, or 20.", HttpStatus.BAD_REQUEST);
        }

        return examService.createExam(category, qNumber, title, difficultyLevel);
    }

    @Operation(summary = "Get Exam Questions by Id", description = "Return questions")
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getExamQuestions(@PathVariable Integer id){
        return examService.getExamQuestions(id);
    }

    @Operation(summary = "Submit exam", description = "Submit exam")
    @PostMapping("/submit/{id}")
    public ResponseEntity<Result> submitExam(
            @PathVariable Integer id,
            @RequestBody List<Response> responses,
            @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return examService.calculateResult(id, responses, token);
    }

}
