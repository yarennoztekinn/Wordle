import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Wordle {
    public static void main(String[] args) throws IOException {

        String path = "dict.txt";
        FileInputStream fis = new FileInputStream(path);

        int control = 0;
        String text = "";

        while((control = fis.read()) != -1){
            text += (char)control;
        }

        //text = CIGAR\nREBUT\nSISSY\nHUMPH\n....
        String arr[] = text.split("\n");

        // arr = [ CIGAR, REBUT, SISSY, HUMPH,....]
        String target = chooseWord(arr);

        boolean win = false;
        int countOfRights = 0;

        while(countOfRights <= 5 ) {

            String guess = args[countOfRights].toUpperCase();

            if (guess.equals(target)) { // if guess is equal to target make win true and break the loop
                win = true;
                break;
            }
            if (CheckLength(guess)) {// if its length is five
                if (IsInDict(guess, arr)) { // if it is in dictionary
                    System.out.println("Try" + (countOfRights+1) + " (" + guess + "):");
                    System.out.print(feedBack(guess, target));
                    countOfRights++;

                }else {
                    System.out.println("Try" + (countOfRights+1) + " (" + guess + ") : The word does not exist in the dictionary!");
                    countOfRights++;
                }
            }else {
                System.out.println("Try" + (countOfRights+1) + " (" + guess + ") : The length of word must be five!");
                countOfRights++;
            }
        }
        if(args.length > 6){ // if the user enters more than 6
            System.out.println("You exceeded maximum try shot!");
        }

        if (countOfRights == 6) { //if there is no right to try
            System.out.println("You failed! The key word is: " + target);
            win = false;
        }


        if (win) { // if win is true (it means user won the game)
            System.out.print("Congratulations! You guess right in " + (countOfRights+1));
            switch (countOfRights) {
                case 0:
                    System.out.println("st shot!");
                    break;

                case 1:
                    System.out.println("nd shot!");
                    break;

                case 2:
                    System.out.println("rd shot!");
                    break;

                default:
                    System.out.println("th shot!");
                    break;
            }
        }
    }
    public static boolean CheckLength(String givenWord){
        if (givenWord.length() != 5 ){ // if the word's length is not 5, return false
            return false;
        }
        return true;
    }
    public  static boolean IsInDict (String givenWord, String[] array){
        for(String word : array){
            if (word.equalsIgnoreCase(givenWord)){
                return true;
            }
        }
        return false; // if the word is not in the dictionary, return false
    }
    public static  String chooseWord(String[] array){
        Random random = new Random();
        int ind = random.nextInt(array.length);
        String target = array[ind];
        return target;
    }
    public static String feedBack(String guess, String target) {

        String feedback = "";
        int howMany = 0;

        for(int ind1 = 0; ind1 < guess.length(); ind1++) {
            boolean IsIn = true;
            char guessChar = guess.charAt(ind1); // the character of the guess variable in the 'ind1' index is

            for(int ind2 = 0; ind2 < target.length(); ind2++) {
                char targetChar = target.charAt(ind2);// the character of the target variable in the 'ind2' index is
                if (guessChar == targetChar){ // if the char(letter) is exists

                    if (ind1 == ind2) { // if the char is in correct position
                        feedback += (++howMany + ". letter exists and located in right position. " + "\n");
                        IsIn = false;

                    }else { // if the char is in wrong position
                        feedback += (++howMany + ". letter exists but located in wrong position." + "\n");
                        IsIn = false;
                    }
                    break;
                }
            }
            if (IsIn) {  // if the char does not exist
                feedback += (++howMany + ". letter does not exist." + "\n");
            }
        }
        return feedback;
    }
}
