package com.nobody.controller;

import com.nobody.model.Result;
import com.nobody.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("result")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }

    @GetMapping()
    @Operation(summary = "Get a list all Exam results", description = "Return all results")
    public ResponseEntity<Page<Result>> getExamResults(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return resultService.getAllExams(page, size);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get a list of Exam results by user id", description = "Return exams by user id")
    public ResponseEntity<Page<Result>> getResultsByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return resultService.getResultsByUserId(userId, page, size);
    }
}
