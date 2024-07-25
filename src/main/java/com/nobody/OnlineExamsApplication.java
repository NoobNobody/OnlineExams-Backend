package com.nobody;

import com.nobody.enums.Role;
import com.nobody.model.Exam;
import com.nobody.model.Question;
import com.nobody.model.User;
import com.nobody.repository.ExamRepository;
import com.nobody.repository.QuestionRepository;
import com.nobody.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class OnlineExamsApplication implements CommandLineRunner{

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ExamRepository examRepository;

	@Autowired
	private UserRepository userRepository;

	PasswordEncoder passwordEncoder;

	public OnlineExamsApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineExamsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Question question1 = new Question(null, "What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris", "Easy", "Geography");
		Question question2 = new Question(null, "What is 2 + 2?", "3", "4", "5", "6", "4", "Easy", "Math");
		Question question3 = new Question(null, "What is the largest planet in our solar system?", "Earth", "Mars", "Jupiter", "Saturn", "Jupiter", "Medium", "Science");
		Question question4 = new Question(null, "Who wrote 'To be, or not to be'?", "Shakespeare", "Hemingway", "Frost", "Dickens", "Shakespeare", "Hard", "Literature");
		Question question5 = new Question(null, "What is the chemical symbol for water?", "H2O", "O2", "CO2", "HO", "H2O", "Easy", "Chemistry");
		Question question6 = new Question(null, "What is the square root of 16?", "2", "4", "6", "8", "4", "Easy", "Math");
		Question question7 = new Question(null, "What is the currency of Japan?", "Yuan", "Yen", "Won", "Dollar", "Yen", "Medium", "Economics");
		Question question8 = new Question(null, "What is the speed of light?", "300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s", "300,000 km/s", "Hard", "Physics");
		Question question9 = new Question(null, "What year did World War II end?", "1942", "1945", "1948", "1950", "1945", "Medium", "History");
		Question question10 = new Question(null, "Who painted the Mona Lisa?", "Van Gogh", "Picasso", "Da Vinci", "Rembrandt", "Da Vinci", "Medium", "Art");
		Question question11 = new Question(null, "What is the smallest prime number?", "1", "2", "3", "5", "2", "Easy", "Math");
		Question question12 = new Question(null, "Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", "Mars", "Easy", "Science");
		Question question13 = new Question(null, "What is the largest ocean on Earth?", "Atlantic", "Indian", "Arctic", "Pacific", "Pacific", "Medium", "Geography");
		Question question14 = new Question(null, "Who discovered penicillin?", "Newton", "Einstein", "Fleming", "Curie", "Fleming", "Hard", "Medicine");
		Question question15 = new Question(null, "What is the capital of Australia?", "Sydney", "Melbourne", "Canberra", "Brisbane", "Canberra", "Medium", "Geography");
		Question question16 = new Question(null, "What is the main ingredient in guacamole?", "Tomato", "Onion", "Avocado", "Pepper", "Avocado", "Easy", "Food");
		Question question17 = new Question(null, "Who wrote 'Pride and Prejudice'?", "Austen", "Brontë", "Eliot", "Dickens", "Austen", "Medium", "Literature");
		Question question18 = new Question(null, "What is the freezing point of water?", "0°C", "32°C", "100°C", "212°C", "0°C", "Easy", "Science");
		Question question19 = new Question(null, "What is the capital of Canada?", "Toronto", "Ottawa", "Vancouver", "Montreal", "Ottawa", "Medium", "Geography");
		Question question20 = new Question(null, "What is the tallest mountain in the world?", "K2", "Kangchenjunga", "Lhotse", "Everest", "Everest", "Medium", "Geography");
		Question question21 = new Question(null, "What color is the sky on a clear day?", "Red", "Blue", "Green", "Yellow", "Blue", "Easy", "General Knowledge");
		Question question22 = new Question(null, "How many days are there in a week?", "5", "6", "7", "8", "7", "Easy", "General Knowledge");
		Question question23 = new Question(null, "What do bees make?", "Honey", "Sugar", "Salt", "Butter", "Honey", "Easy", "Nature");
		Question question24 = new Question(null, "What is the opposite of hot?", "Warm", "Cold", "Cool", "Freezing", "Cold", "Easy", "General Knowledge");
		Question question25 = new Question(null, "How many legs does a spider have?", "6", "8", "10", "12", "8", "Easy", "Nature");
		Question question26 = new Question(null, "What is the main ingredient in bread?", "Rice", "Corn", "Wheat", "Oats", "Wheat", "Easy", "Food");
		Question question27 = new Question(null, "How many hours are there in a day?", "12", "24", "36", "48", "24", "Easy", "General Knowledge");
		Question question28 = new Question(null, "What is the color of a ripe banana?", "Red", "Blue", "Green", "Yellow", "Yellow", "Easy", "Nature");
		Question question29 = new Question(null, "What is 3 + 5?", "5", "6", "7", "8", "8", "Easy", "Math");
		Question question30 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question31 = new Question(null, "What is H2O commonly known as?", "Water", "Oxygen", "Hydrogen", "Salt", "Water", "Easy", "Science");
		Question question32 = new Question(null, "What is the shape of the Earth?", "Flat", "Round", "Square", "Triangle", "Round", "Easy", "Geography");
		Question question33 = new Question(null, "How many wheels does a bicycle have?", "1", "2", "3", "4", "2", "Easy", "General Knowledge");
		Question question34 = new Question(null, "What animal is known as man's best friend?", "Cat", "Dog", "Bird", "Fish", "Dog", "Easy", "Nature");
		Question question35 = new Question(null, "How many months are there in a year?", "10", "11", "12", "13", "12", "Easy", "General Knowledge");
		Question question36 = new Question(null, "What color is a stop sign?", "Green", "Yellow", "Red", "Blue", "Red", "Easy", "General Knowledge");
		Question question37 = new Question(null, "What is the capital of Italy?", "Paris", "Berlin", "Rome", "Madrid", "Rome", "Easy", "Geography");
		Question question38 = new Question(null, "How many letters are there in the English alphabet?", "24", "25", "26", "27", "26", "Easy", "General Knowledge");
		Question question39 = new Question(null, "What is the color of grass?", "Red", "Blue", "Green", "Yellow", "Green", "Easy", "Nature");
		Question question40 = new Question(null, "What is the color of sky?", "Red", "Blue", "Green", "Yellow", "Blue", "Easy", "Nature");
		Question question41 = new Question(null, "What do you use to write on paper?", "Chalk", "Pen", "Brush", "Crayon", "Pen", "Easy", "General Knowledge");
		Question question42 = new Question(null, "What is 2 + 2?", "3", "4", "5", "6", "4", "Easy", "Math");
		Question question43 = new Question(null, "What is 2 + 2?", "3", "4", "5", "6", "4", "Easy", "Math");
		Question question44 = new Question(null, "What is 2 + 2?", "3", "4", "5", "6", "4", "Easy", "Math");
		Question question45 = new Question(null, "What is 2 + 2?", "3", "4", "5", "6", "4", "Easy", "Math");
		Question question46 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question47 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question48 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question49 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question50 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");
		Question question51 = new Question(null, "What is the name of a baby cat?", "Puppy", "Kitten", "Cub", "Chick", "Kitten", "Easy", "Nature");


		questionRepository.saveAll(List.of(question1, question2, question3, question4, question5, question6, question7, question8, question9, question10
		, question11, question12, question13, question14, question15, question16, question17, question18, question19, question20
		, question21, question22, question23, question24, question25, question26, question27, question28, question29, question30
		, question31, question32, question33, question34, question35, question36, question37, question38, question39, question40
		, question41, question42, question43, question44, question45, question46, question47, question48, question49, question50
		, question51));

		List<Question> mathQuestions = questionRepository.findRandomQuestionsByCategoryAndDifficulty("Math", "Easy", 10);
		List<Question> natureQuestions = questionRepository.findRandomQuestionsByCategoryAndDifficulty("Nature", "Easy", 10);

		Exam mathExam = new Exam();
		mathExam.setTitle("Math exam");
		mathExam.setQuestions(mathQuestions);
		mathExam.setCategory("Math");
		mathExam.setQNumber(mathQuestions.size());
		mathExam.setDifficultyLevel("Easy");

		Exam natureExam = new Exam();
		natureExam.setTitle("Nature exam");
		natureExam.setQuestions(natureQuestions);
		natureExam.setCategory("Nature");
		natureExam.setQNumber(natureQuestions.size());
		natureExam.setDifficultyLevel("Easy");

		examRepository.save(mathExam);
		examRepository.save(natureExam);

		User user = new User();
		user.setEmail("johndoe@gmail.com");
		user.setPassword(passwordEncoder.encode("karwala1"));
		user.setFirstname("John");
		user.setLastname("Doe");
		user.setRole(Role.ADMIN);

		User user1 = new User();
		user1.setEmail("noobnobody@gmail.com");
		user1.setPassword(passwordEncoder.encode("karwala1"));
		user1.setFirstname("Noob");
		user1.setLastname("Nobody");
		user1.setRole(Role.USER);

		User user2 = new User();
		user2.setEmail("jobworker@gmail.com");
		user2.setPassword(passwordEncoder.encode("karwala1"));
		user2.setFirstname("Job");
		user2.setLastname("Worker");
		user2.setRole(Role.USER);

		User user3 = new User();
		user3.setEmail("enummaster@gmail.com");
		user3.setPassword(passwordEncoder.encode("karwala1"));
		user3.setFirstname("Enum");
		user3.setLastname("Master");
		user3.setRole(Role.USER);

		userRepository.save(user);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
	}

}
