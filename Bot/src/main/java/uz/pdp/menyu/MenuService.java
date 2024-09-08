package uz.pdp.menyu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.util.BotUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuService {
    public static SendMessage showMenyu(Long chatId) {

        List<String> stringList = List.of("Namoz Vaqtlari\uD83D\uDD58", "Eng Yaqin Namoz\uD83D\uDCAC", "Eng yaqin Masjidlar\uD83D\uDD4C",
                "Tasbex\uD83D\uDCFF", "Quroni Karimdan Bazi Suralar\uD83D\uDCD6", "Xijriy Yil Xisobi\uD83D\uDCC6", "✨Allohning go`zal ismlari", "Quran☪\uFE0F","\uD83E\uDD32Duolar");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Welcome Bot");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(stringList, 3);
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }
}