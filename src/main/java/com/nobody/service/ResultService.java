package com.nobody.service;

import com.nobody.model.Result;
import com.nobody.repository.ResultRepository;
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
public class ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository){
        this.resultRepository = resultRepository;
    }

    public ResponseEntity<Page<Result>> getAllExams(int page, int size){
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<Result> results = resultRepository.findAll(pageable);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching results", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<Result>> getResultsByUserId(Integer userId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<Result> results = resultRepository.findByUserId(userId, pageable);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching results for user id: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
