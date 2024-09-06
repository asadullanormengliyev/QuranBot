package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.util.BotUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImamNamesBotService {
    public static Map<String, String> IMAM_MENU = new HashMap<>() {{
        put("Sheikh Mishary Rashid", "afs");
        put("Abdullah Al-Matrod", "mtrod");
        put("Adel Al Kalbani", "a_klb");
        put("Sheikh Adel Ryan", "ryan");
        put("Sheikh Fares Abbad", "frs_a");
        put("Shayx Muhammad Ayyub", "ayyub");
        put("Shayx Yusuf Nuh", "noah");
    }};

    public static SendMessage imamMenu(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        List<String> imamList = new ArrayList<>(IMAM_MENU.keySet());
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(imamList, 2);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Back\uD83D\uDD19"));
        replyKeyboardMarkup.getKeyboard().add(keyboardRow);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

}
