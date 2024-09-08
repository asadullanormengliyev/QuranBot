package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.util.BotUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasbexBotService {
    private static final Map<Long, Integer> tasbexMap = new HashMap<>();

    public SendMessage getTasbex(long chatId, String data) {
        tasbexMap.put(chatId,0);
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = createInlineKeyboardMarkup(data + "\uD83D\uDCFF", "tasbex");
        sendMessage.setChatId(chatId);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    public EditMessageReplyMarkup getUpdateTasbex(long chatId, Integer messageId) {
        int countTasbex = tasbexMap.getOrDefault(chatId,0);
        countTasbex++;
        if (countTasbex == 33) {
            countTasbex = 0;
        }
        tasbexMap.put(chatId,countTasbex);
        InlineKeyboardMarkup inlineKeyboardMarkup = createInlineKeyboardMarkup(countTasbex + "\uD83D\uDCFF", "tasbex");

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);

        return editMessageReplyMarkup;
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(String buttonText, String callbackData) {
        return BotUtil.inlineKeyboardMarkup(List.of(buttonText), List.of(callbackData), 1);
    }
}
