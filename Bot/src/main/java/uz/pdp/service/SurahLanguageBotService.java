package uz.pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.model.Koran;
import uz.pdp.util.BotUtil;
import uz.pdp.util.FilePath;
import uz.pdp.util.JsonUtil;

import java.util.List;

public class SurahLanguageBotService {
    public SendMessage quranMenyu(long chatId, String data) {

        List<String> stringDate = List.of("Lotin alifbosida", "باللغة العربية");
        List<String> stringCallbackQuaryDate = List.of("l" + data, "a" + data);

        InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(stringDate, stringCallbackQuaryDate, 2);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Tilni Tanlang");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    public String getLotinSurah(String str) {
        List<Koran> list = JsonUtil.readGson(FilePath.PATH_KORAN, new TypeReference<>() {
        });
        Koran quranKarimRoot = new Koran();
        for (Koran root : list) {
            if (str.endsWith(root.getTransliteration())) {
                quranKarimRoot = root;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Koran.Verse verse : quranKarimRoot.getVerses()) {
            stringBuilder.append(verse.id + "  " + verse.transliteration + "\n");
        }
        return String.valueOf(stringBuilder);
    }

    public String getAraSurah(String str) {
        List<Koran> list = JsonUtil.readGson(FilePath.PATH_KORAN, new TypeReference<>() {
        });
        Koran quranKarimRoot = new Koran();
        for (Koran root : list) {
            if (str.endsWith(root.getTransliteration())) {
                quranKarimRoot = root;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Koran.Verse verse : quranKarimRoot.getVerses()) {
            stringBuilder.append(verse.id + "  " + verse.text + "\n");
        }
        return String.valueOf(stringBuilder);
    }
}
