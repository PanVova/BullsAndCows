import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    static Game game = new Game();
    static String answer = game.createNumber();
    static boolean game_is = false;


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        System.out.println(answer);
        System.out.println("------");

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        String player_answer = update.getMessage().getText();
        SendMessage message = new SendMessage();
        switch (player_answer) {
            case "new":
                answer = game.createNumber();
                game_is = true;
                game.setCounter(0);
                message.setText("NEW GAME");
                message.setChatId(update.getMessage().getChatId());
                break;
            case "exit":
                game_is = false;
                message.setText("OK,FINE");
                message.setChatId(update.getMessage().getChatId());
                break;
            case "help":
                message.setText("digits do not repeat in the number \n" +
                        "Example :\n" +
                        "bot made a 3-digit number\n" +
                        "your task is to guess it\n" +
                        "for example, he randomized number 567\n" +
                        "There are bulls and cows in this game. Bulls - this is when one of the digit stands in its place, for example 568 - there will be 2 bulls, because 5 and 6 stand in their place. The cows are the numbers that are in number, but are not in their place, 765 - there will be 2 cows and 1 bull (6 stands in its place, there will be one bull, and 2 cows 7 and 5 do not stand in their places, but they contains in randomized number)");
                message.setChatId(update.getMessage().getChatId());
                break;
        }


        if (game_is && player_answer.length() == 3 && isInteger(player_answer)) {
            if (answer.equals(player_answer)) {
                message.setText("You won\nYour number of tries: " + game.getCounter());
                message.setChatId(update.getMessage().getChatId());
            } else {
                game.CheckBullsCows(answer, player_answer);
                message.setText("Bulls:" + game.getBulls() + "\nCows:" + (game.getCows() - game.getBulls()));
                message.setChatId(update.getMessage().getChatId());
            }
        } else if (isInteger(player_answer)) {
            message.setText("You need to write 3-digit value or maybe you need to start a new game(new)");
            message.setChatId(update.getMessage().getChatId());
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "Bulls&Cows";
    }

    public String getBotToken() {
        return "673279294:AAGCrugIQqkKpXX4juAEuZHIUFBDPaYZdhg";
    }
}
