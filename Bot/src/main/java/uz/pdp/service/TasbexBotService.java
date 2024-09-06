package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.util.BotUtil;

import java.util.List;

public class TasbexBotService {
    private static int countTasbex = 0;

    public SendMessage getTasbex(long chatId, String data) {
        countTasbex = 0;
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = createInlineKeyboardMarkup(data + "\uD83D\uDCFF", "tasbex");
        sendMessage.setChatId(chatId);
        sendMessage.setText("Tasbex 0/33");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    public EditMessageReplyMarkup getUpdateTasbex(long chatId, Integer messageId) {
        countTasbex++;
        if (countTasbex == 33) {
            countTasbex = 0;
        }

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
