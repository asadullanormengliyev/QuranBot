package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.util.BotUtil;

import java.util.ArrayList;
import java.util.List;

public class PrayerTimeBotService {
    public SendMessage getNamozVatlari(Long chatId) {
        List<String> list = new ArrayList<>();
        list.add("Namoz vaqtlari");
        list.add("Back");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(list, 1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
