package uz.pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.model.PrayerTime;
import uz.pdp.util.BotUtil;
import uz.pdp.util.FilePath;
import uz.pdp.util.JsonUtil;
import uz.pdp.servicex.MonthlyPrayerTimesUtil;
import uz.pdp.utill.ObjectUtil;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrayerTimeInlineBotService {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Map<Long, Integer> dayMap = new HashMap<>();

    public String getToday() {
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        String[] split = str.split("-");
        String day = split[2];
        return day;
    }

    public String getMonth() {
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        String[] split = str.split("-");
        String month = split[1];
        return month;
    }

    private PrayerTime root() {
        String month = getMonth();
        String day = getToday();
        List<PrayerTime> roots = JsonUtil.readGson(FilePath.PATH_PRAYERTIMES, new TypeReference<>() {
        });
        PrayerTime todayRoot = new PrayerTime();
        for (PrayerTime root : roots) {
            if (root.getDay() == Integer.parseInt(day) && root.getMonth() == Integer.parseInt(month)) {
                todayRoot = root;
            }
        }
        return todayRoot;
    }

    public SendMessage sendPrayerTimesKeyboard(long chatId) {
        ObjectUtil.monthlyPrayerTimesUtil.write();
        PrayerTime root = root();
        String monthName = new DateFormatSymbols().getMonths()[root.getMonth() - 1];

        List<String> date = List.of("Bomdod:  " + root.getTimes().getTong_saharlik(),
                "Peshin:  " + root.getTimes().getPeshin(), "Asr:  " + root.getTimes().getAsr(), "Shom:  " + root.getTimes().getShom_iftor(),
                "Xufton:  " + root.getTimes().getHufton());
        List<String> calbackQuary = List.of("bomdod", "peshin", "asr", "shom", "xufton");

        InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(date, calbackQuary, 1);

        List<String> stringList = List.of("◀\uFE0F ", root.getDay() + " - " + monthName, " ▶\uFE0F ");
        List<String> backk = List.of("backk", "date", "nextt");
        InlineKeyboardMarkup inlineKeyboardMarkup1 = BotUtil.inlineKeyboardMarkup(stringList, backk, 3);

        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMarkup.getKeyboard();
        keyboard.add(inlineKeyboardMarkup1.getKeyboard().get(0));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    private PrayerTime nextRoot(Long chatId, String callbackData) {
        if (dayMap.isEmpty()) {
            int currentDay = Integer.parseInt(getToday());
            dayMap.put(chatId, currentDay);
        }
        Integer day = dayMap.get(chatId);
        List<PrayerTime> prayerTimes = JsonUtil.readGson(FilePath.PATH_PRAYERTIMES, new TypeReference<>() {
        });
        PrayerTime nexPreyerTime = new PrayerTime();
        for (PrayerTime prayerTime : prayerTimes) {
            if (prayerTime.day == day) {
                nexPreyerTime = prayerTime;
            }
        }
        if (callbackData.equals("nextt")) {
            dayMap.put(chatId, dayMap.get(chatId) + 1);
        } else if (callbackData.equals("backk")) {
            dayMap.put(chatId, dayMap.get(chatId) - 1);
        }
        return nexPreyerTime;
    }

    public EditMessageText nextAndBackSendPrayerTimes(Long chatId, Integer messageId, String callbackData) {
        PrayerTime root = nextRoot(chatId, callbackData);
        String monthName = new DateFormatSymbols().getMonths()[root.getMonth() - 1];
        List<String> date = List.of("Bomdod:  " + root.getTimes().getTong_saharlik(),
                "Peshin:  " + root.getTimes().getPeshin(), "Asr:  " + root.getTimes().getAsr(), "Shom:  " + root.getTimes().getShom_iftor(),
                "Xufton:  " + root.getTimes().getHufton());

        List<String> calbackQuary = List.of("bomdod", "peshin", "asr", "shom", "xufton");
        InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(date, calbackQuary, 1);

        List<String> stringList = List.of("◀\uFE0F ", root.getDay() + " - " + monthName, " ▶\uFE0F ");
        List<String> backk = List.of("backk", "date", "nextt");
        InlineKeyboardMarkup inlineKeyboardMarkup1 = BotUtil.inlineKeyboardMarkup(stringList, backk, 3);

        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMarkup.getKeyboard();
        keyboard.add(inlineKeyboardMarkup1.getKeyboard().get(0));

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(messageId);
        editMessageText.setText("Namoz vaqtlari: \n" + root.getDay() + " - " + monthName);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

}
