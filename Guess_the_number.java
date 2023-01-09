import java.util.Random;
import java.util.Scanner;

public class Guess_the_number{
    public static void main(String[] args) {
        Random rn = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("Guess The number(0-9) : ");
        int guess = sc.nextInt();
        int num = rn.nextInt(10);
        if(guess == num) {
            System.out.println("Hurray!,your guess is correct ");
        }
        else{
            System.out.println("Better luck next time the number is " + num);
        }
    }
}
