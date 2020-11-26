package biz.bagira.telega;

import biz.bagira.service.StartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class GuessBot extends TelegramLongPollingCommandBot {

    private Logger logger = LoggerFactory.getLogger(GuessBot.class);

    private final String BOT_NAME;
    private final String BOT_TOKEN;

//    private final NonCommand nonCommand;

    public GuessBot(String BOT_NAME, String BOT_TOKEN) {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;
        register(new StartService("start", "Старт"));
//        this.nonCommand = new NonCommand();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        User user = msg.getFrom();
        String userName = user.getUserName();

        String answer = "Не верный ввод. Попробуй снова.";
        setAnswer(chatId, userName, answer);
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}
