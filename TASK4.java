//Quiz App

import java.util.*;
import java.util.concurrent.*;



public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static final int TIME_LIMIT = 10; 

    public static void main(String[] args) {
        // Add questions to the quiz
        questions.add(new Question("What is the capital of France?",
                new String[]{"1. Berlin", "2. Paris", "3. Madrid", "4. Rome"}, 2));
        questions.add(new Question("What is 5 + 3?",
                new String[]{"1. 6", "2. 8", "3. 9", "4. 10"}, 2));
        questions.add(new Question("Who wrote 'Hamlet'?",
                new String[]{"1. Charles Dickens", "2. William Shakespeare", "3. Jane Austen", "4. Mark Twain"}, 2));

        System.out.println("Welcome to the Quiz Application!");
        System.out.println("You have " + TIME_LIMIT + " seconds to answer each question.");
        System.out.println("Let's begin!\n");

        for (Question question : questions) {
            askQuestion(question);
        }

        displayResult();
    }

    private static void askQuestion(Question question) {
        System.out.println(question.question);
        for (String option : question.options) {
            System.out.println(option);
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            System.out.print("Enter your answer (1-4): ");
            return scanner.nextInt();
        });

        try {
            int answer = future.get(TIME_LIMIT, TimeUnit.SECONDS);
            if (answer == question.correctOption) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + question.correctOption + "\n");
            }
        } catch (TimeoutException e) {
            System.out.println("Time's up! The correct answer was: " + question.correctOption + "\n");
            future.cancel(true);
        } catch (Exception e) {
            System.out.println("Invalid input. The correct answer was: " + question.correctOption + "\n");
        } finally {
            executor.shutdownNow();
        }
    }

    private static void displayResult() {
        System.out.println("Quiz Over!");
        System.out.println("Your final score is: " + score + " / " + questions.size());
        System.out.println("Thank you for playing!");
    }
}

class Question {
    String question;
    String[] options;
    int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}
