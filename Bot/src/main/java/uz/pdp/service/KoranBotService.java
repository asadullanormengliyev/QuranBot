package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.model.SurahDigitState;
import uz.pdp.model.Surahs;
import uz.pdp.util.BotUtil;
import uz.pdp.utill.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class KoranBotService {
    private static final int PAGE_SIZE = 10;
    private static final int TOTAL_SURAHS = 114;

    private static List<Surahs> surahs = new ArrayList<>();
    private static List<SurahDigitState> surahsState = new ArrayList<>();

    public SendMessage getSurah(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Surahs");
        InlineKeyboardMarkup inlineKeyboardMarkup = createInlineKeyboard(0);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public EditMessageReplyMarkup getSurah(long chatId, Integer messageId, String action) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        int currentIndex = getIndex(chatId, action);
        editMessage.setReplyMarkup(createInlineKeyboard(currentIndex));
        return editMessage;
    }

    private int getIndex(long chatId, String action) {
        surahsState = ObjectUtil.koranService.getSurahsState();
        for (SurahDigitState state : surahsState) {
            if (state.getChatId() == chatId) {
                if ("nextSurax".equals(action)) {
                    int newId = state.getId();
                    state.setId(Math.min(newId, TOTAL_SURAHS - PAGE_SIZE));
                } else if ("backSurax".equals(action)) {
                    int newId = state.getId();
                    state.setId(Math.max(newId, 0));
                }
                ObjectUtil.koranService.setSurahsState(surahsState);
                return state.getId();
            }
        }
        return 0;
    }

    private InlineKeyboardMarkup createInlineKeyboard(int n) {
        surahs = ObjectUtil.koranService.getSurahs();
        List<Surahs> surah = ObjectUtil.koranService.getStringList(surahs, n);
        InlineKeyboardMarkup inlinedKeyboardMarkup1 = BotUtil.inlineKeyboardMarkup(surah, 1);

        List<String> back = List.of("Back", "Next");
        List<String> next = List.of("backSurax", "nextSurax");

        InlineKeyboardMarkup inlineKeyboardMarkup2 = BotUtil.inlineKeyboardMarkup(back, next, 2);
        List<List<InlineKeyboardButton>> keyboard = inlinedKeyboardMarkup1.getKeyboard();
        keyboard.add(inlineKeyboardMarkup2.getKeyboard().get(0));

        return inlinedKeyboardMarkup1;
    }


    public static SendMessage surahList(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<String> list = List.of("Quron suralari\uD83E\uDEC0", " Back\uD83D\uDD19");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(list, 1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}

