
import java.util.*;

public class Game {

    private int bulls = 0;
    private int cows = 0;
    private int counter = 0;

    public static String createNumber() {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            l.add(Integer.toString(i));
        }
        Collections.shuffle(l);
        l = l.subList(0, 3);

        String listString = "";

        for (String s : l) {
            listString += s;
        }

        return listString;
    }

    public void CheckBullsCows(String answer, String player_answer) {
        bulls = 0;
        cows = 0;
        for (int i = 0; i < 3; i++) {
            if (answer.toCharArray()[i] == player_answer.toCharArray()[i]) {
                bulls++;
            }
            for (int j = 0; j < 3; j++) {
                if (answer.toCharArray()[i] == player_answer.toCharArray()[j]) {
                    cows++;
                }
            }
        }
        counter++;

    }

    public void setCounter(int i) {
        counter = i;
    }


    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }


    public int getCounter() {
        return counter;
    }

}
