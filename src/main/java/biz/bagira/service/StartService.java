package biz.bagira.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartService extends BotCommand {

    private Logger logger = LoggerFactory.getLogger(StartService.class);

    public StartService(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chat.getId().toString());
        message.setText("Hello");
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. ", e.getMessage()));
            e.printStackTrace();
        }
    }
}
