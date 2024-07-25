package com.nobody.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Exam {

    @Id
    @GeneratedValue
    private Integer id;

    private String category;
    private int qNumber;
    private String title;
    private String difficultyLevel;

    @ManyToMany
    private List<Question> questions;

}
