    package com.nobody.service;

    import com.nobody.config.JwtService;
    import com.nobody.model.*;
    import com.nobody.repository.ExamRepository;
    import com.nobody.repository.QuestionRepository;
    import com.nobody.repository.ResultRepository;
    import com.nobody.repository.UserRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class ExamService {

        private final ExamRepository examRepository;
        private final QuestionRepository questionRepository;
        private final ResultRepository resultRepository;
        private final UserRepository userRepository;
        private final JwtService jwtService;

        public ExamService(
                ExamRepository examRepository,
                QuestionRepository questionRepository,
                ResultRepository resultRepository,
                UserRepository userRepository,
                JwtService jwtService
        ){
            this.examRepository = examRepository;
            this.questionRepository = questionRepository;
            this.resultRepository = resultRepository;
            this.userRepository = userRepository;
            this.jwtService = jwtService;
        }

        public ResponseEntity<String> createExam(String category, int qNumber, String title, String difficultyLevel) {

            if (qNumber != 5 && qNumber != 10 && qNumber != 15 && qNumber != 20) {
                return new ResponseEntity<>("Invalid number of questions. Valid options are 5, 10, 15, or 20.", HttpStatus.BAD_REQUEST);
            }

            int categoryCount = questionRepository.countCategories(category);
            if (categoryCount == 0) {
                return new ResponseEntity<>("Category does not exist.", HttpStatus.BAD_REQUEST);
            }

            int totalQuestionsInCategory = questionRepository.countByCategoryAndDifficulty(category, difficultyLevel);
            if (totalQuestionsInCategory < qNumber) {
                return new ResponseEntity<>("Not enough questions in the selected category and difficulty level. Available: " + totalQuestionsInCategory, HttpStatus.BAD_REQUEST);
            }

            List<Question> questions = questionRepository.findRandomQuestionsByCategoryAndDifficulty(category, difficultyLevel, qNumber);

            Exam exam = new Exam();
            exam.setCategory(category);
            exam.setQNumber(qNumber);
            exam.setTitle(title);
            exam.setDifficultyLevel(difficultyLevel);
            exam.setQuestions(questions);
            examRepository.save(exam);

            return new ResponseEntity<>("Exam created successfully", HttpStatus.CREATED);
        }


        public ResponseEntity<List<QuestionWrapper>> getExamQuestions(Integer id) {
            Optional<Exam> exam = examRepository.findById(id);
            if (exam.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Question> questionsFromDb = exam.get().getQuestions();
            List<QuestionWrapper> questionsForUsers = new ArrayList<>();
            for (Question q : questionsFromDb) {
                QuestionWrapper qw = new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                );
                questionsForUsers.add(qw);
            }
            return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
        }

        @Transactional
        public ResponseEntity<Result> calculateResult(Integer examId, List<Response> responses, String token) {
            Optional<Exam> exam = examRepository.findById(examId);
            if (exam.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User user = getUserFromToken(token).orElseThrow(() -> new RuntimeException("User not found"));
            List<Question> questions = exam.get().getQuestions();
            int right = 0;

            for (Response response : responses) {
                Question question = questions.stream()
                        .filter(q -> q.getId().equals(response.getId()))
                        .findFirst()
                        .orElse(null);
                if (question != null && response.getResponse().equals(question.getRightAnswer())) {
                    right++;
                }
            }

            int totalQuestions = questions.size();
            long percentage = Math.round(((double) right / totalQuestions) * 100);
            String grade = calculateGrade(percentage);

            String resultString = right + "/" + totalQuestions;
            Result result = new Result();
            result.setResult(resultString);
            result.setPercentage(percentage);
            result.setGrade(grade);
            result.setUser(user);
            result.setExam(exam.get());
            resultRepository.save(result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        private String calculateGrade(double percentage) {
            if (percentage >= 90) {
                return "5";
            } else if (percentage >= 75) {
                return "4";
            } else if (percentage >= 60) {
                return "3";
            } else if (percentage >= 50) {
                return "2";
            } else {
                return "1";
            }
        }

        private Optional<User> getUserFromToken(String token) {
            String email = jwtService.extractUsername(token);
            return userRepository.findByEmail(email);
        }

    }
