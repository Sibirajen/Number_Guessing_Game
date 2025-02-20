import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GuessTheNumber {
    private static int highScore = Integer.MAX_VALUE; // Stores the best (lowest) attempt count

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean playAgain;

        do {
            greet();
            printDiff();
            System.out.print("Enter difficulty: ");
            int difficulty = sc.nextInt();

            int maxAttempts = switch (difficulty) {
                case 1 -> 10; // Easy
                case 2 -> 5;  // Medium
                case 3 -> 3;  // Hard
                default -> 5; // Default to Medium if invalid input
            };

            startGame(difficulty);
            playGame(maxAttempts, sc);

            System.out.print("\n🎮 Do you want to play again? (yes/no): ");
            playAgain = sc.next().equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("👋 Thanks for playing! See you next time.");
        sc.close();
    }

    public static void playGame(int maxAttempts, Scanner sc) {
        int randNum = getRandNum();
        int attempts = 0;
        boolean won = false;

        Timer timer = new Timer();
        boolean[] timeUp = {false};

        // Time-based challenge (20 seconds)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp[0] = true;
                System.out.println("\n⏳ Time is up! The correct number was: " + randNum);
                System.exit(0);
            }
        }, 60000); // 60 seconds

        while (attempts < maxAttempts && !timeUp[0]) {
            System.out.print("Enter your guess: ");
            int guess = sc.nextInt();
            attempts++;

            if (guess < randNum) {
                System.out.println("📉 Incorrect! The number is GREATER than " + guess);
            } else if (guess > randNum) {
                System.out.println("📈 Incorrect! The number is LESS than " + guess);
            } else {
                System.out.println("🎉 Congratulations! You guessed the correct number in " + attempts + " attempts.");
                won = true;
                updateHighScore(attempts);
                timer.cancel();
                break;
            }

            // 🎯 Hint Feature
            if (attempts == maxAttempts - 1) {
                System.out.println("🕵️ Hint: The number is " + (randNum % 2 == 0 ? "EVEN" : "ODD"));
            }
        }

        if (!won) {
            System.out.println("❌ Sorry! You've used all your chances. The correct number was: " + randNum);
        }

        timer.cancel();
    }

    public static int getRandNum() {
        Random random = new Random();
        return random.nextInt(100) + 1; // Generates a number from 1 to 100
    }

    public static void updateHighScore(int attempts) {
        if (attempts < highScore) {
            highScore = attempts;
            System.out.println("🏆 New High Score! Best attempts: " + highScore);
        } else {
            System.out.println("💡 Your best attempt so far: " + highScore);
        }
    }

    public static void startGame(int difficulty) {
        String diff = switch (difficulty) {
            case 1 -> "Easy (10 chances)";
            case 2 -> "Medium (5 chances)";
            case 3 -> "Hard (3 chances)";
            default -> "Default Medium (5 chances)";
        };

        System.out.printf("""
                🔥 You have selected %s difficulty.
                Let's start the game!%n""", diff);
    }

    public static void printDiff() {
        System.out.println("""
                📌 Choose your difficulty level:
                1. Easy (10 chances)
                2. Medium (5 chances)
                3. Hard (3 chances)""");
    }

    public static void greet() {
        System.out.println("""
                🎮 Welcome to GuessIt!
                I'm thinking of a number between 1 and 100.
                Try to guess it as fast as possible!""");
    }
}
