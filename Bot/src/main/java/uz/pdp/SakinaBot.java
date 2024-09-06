package uz.pdp;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.menyu.MenuService;
import uz.pdp.service.*;
import uz.pdp.utill.ObjectUtil;

import java.util.Date;

import static uz.pdp.BotConstants.*;
import static uz.pdp.NomozConstants.*;

public class SakinaBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String text = message.getText();
                long chatId = message.getChatId();
                System.out.println(chatId);
                if (StringUtils.equals(text, START) || text.startsWith(BACK)) {
                    SendMessage sendMessage = MenuService.showMenyu(chatId);
                    execute_(sendMessage);
                } else if (StringUtils.equals(text, ALLOH_NAME)) {
                    SendAudio sendAudio = ObjectUtil.namesAllahBotService.allohName(chatId);
                    sendAudio.setCaption("Audio formatda");
                    setSendAudio(sendAudio);
                } else if (message.getText().equals(NAMOZ_VAQT_2)) {
                    SendMessage sendMessage = ObjectUtil.prayerTimeInlineBotService.sendPrayerTimesKeyboard(chatId);
                    sendMessage.setText(new Date().toString());
                    execute_(sendMessage);
                } else if (text.startsWith(NAMOZ_VAQT)) {
                    SendMessage sendMessage = ObjectUtil.prayerTimeBotService.getNamozVatlari(chatId);
                    sendMessage.setText(NAMOZ_VAQT);
                    execute_(sendMessage);
                } else if (text.startsWith(ENG_YAQIN_NAMOZ)) {
                    SendMessage sendMessage = ObjectUtil.nearestPrayerTimeBotService.getNextPrayer(chatId);
                    execute_(sendMessage);
                } else if (text.equals(HIJRIY_YIL_XISOB)) {
                    String hijriDate = ObjectUtil.hijriDateBotService.fetchHijriDate();
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(hijriDate);
                    execute_(sendMessage);
                } else if (text.equals(ENG_YAQIN_MASJID)) {
                    SendMessage sendMessage = ObjectUtil.mosqueBotService.requestUserLocation(chatId);
                    sendMessage.setText(ENG_YAQIN_MASJID);
                    execute_(sendMessage);
                } else if (text.equals(TASBEX)) {
                    SendMessage sendMessage = ObjectUtil.tasbexBotService.getTasbex(message.getChatId(), "0");
                    sendMessage.setChatId(chatId);
                    execute_(sendMessage);
                } else if (text.equals(QURONI_KARIMDAN_SURAH)) {
                    ObjectUtil.koranService.newMenyu(chatId, 0);
                    SendMessage sendMessage = ObjectUtil.koranBotService.getSurah(chatId);
                    sendMessage("Surahs",chatId);
                    execute_(sendMessage);
                } else if (text.equals(QURAN)) {
                    SendMessage sendMessage = KoranBotService.surahList(chatId);
                    sendMessage.setText("Suralar");
                    execute_(sendMessage);
                } else if (text.equals(QURAN_SURALARI)) {
                    SendMessage sendMessage = ImamNamesBotService.imamMenu(chatId);
                    sendMessage.setText("Imom menu");
                    execute_(sendMessage);
                } else if (ImamNamesBotService.IMAM_MENU.containsKey(text)) {
                    sendMultipleAudios(chatId, text);
                }
            } else if (message != null && message.hasLocation()) {
                Location userLocation = message.getLocation();
                long chatId = message.getChatId();
                String mapLink = ObjectUtil.mosqueBotService.getMapLink(userLocation);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Here is a link to find the nearest mosques on Google Maps:\n" + mapLink);
                execute_(sendMessage);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            Integer inlineMessageId = callbackQuery.getMessage().getMessageId();
            if (data.equals(SOME_CALLBACK_DATA)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Callback query handled: " + data);
                execute_(sendMessage);
            }
            if (data.equals(NEXT_SURAX)) {
                ObjectUtil.koranService.newMenyu(chatId, 10);
                EditMessageReplyMarkup surah = ObjectUtil.koranBotService.getSurah(chatId, inlineMessageId, data);
                execute_update(surah);
            }
            if (data.equals(BACK_SURAX)) {
                ObjectUtil.koranService.newMenyu(chatId, -10);
                EditMessageReplyMarkup surah = ObjectUtil.koranBotService.getSurah(chatId, inlineMessageId, data);
                execute_update(surah);
            }
            if (data.startsWith(SURAH_STARTS)) {
                SendMessage sendMessage = ObjectUtil.surahLanguageBotService.quranMenyu(chatId, data);
                execute_(sendMessage);
            }
            if (data.startsWith(LSURALAR_STARTS)) {
                String surah = ObjectUtil.surahLanguageBotService.getLotinSurah(data);
                sendMessage(surah, chatId);
            }
            if (data.startsWith(ASURALAR_STARTS)) {
                String surah = ObjectUtil.surahLanguageBotService.getAraSurah(data);
                sendMessage(surah, chatId);
            }
            if (data.equals(BOMDOD)) {
                sendMessage(bomdodText, chatId);
            }
            if (data.equals(PESHIN)) {
                sendMessage(peshinText, chatId);
            }
            if (data.equals(ASR)) {
                sendMessage(asrText, chatId);
            }
            if (data.equals(SHOM)) {
                sendMessage(shomText, chatId);
            }
            if (data.equals(XUFTON)) {
                sendMessage(xuftonText, chatId);
            }
            if (data.equals(TASBEX_CALLBACK)) {
                EditMessageReplyMarkup markup = ObjectUtil.tasbexBotService.getUpdateTasbex(chatId, inlineMessageId);
                execute_update(markup);
            }
            if (data.equals(NEXTT) || data.equals(BACKK)) {
                EditMessageText editMessageText = ObjectUtil.prayerTimeInlineBotService.nextAndBackSendPrayerTimes(chatId, inlineMessageId, data);
                executeEditMessage(editMessageText);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@Uzbekmen_bot";
    }

    @Override
    public String getBotToken() {
        return "7385826567:AAFA8skdLgRWh3-bPGa7qPLh3NkVIMSWIc8";
    }

    private void executeEditMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void execute_(SendMessage t) {
        try {
            execute(t);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute_update(EditMessageReplyMarkup t) {
        try {
            execute(t);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setSendAudio(SendAudio sendAudio) {
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage sendMessage(String text, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return sendMessage;
    }

    public SendAudio sendMultipleAudios(Long chatId, String imomName) {
        SendAudio sendAudio = new SendAudio();
        String pathName = ImamNamesBotService.IMAM_MENU.get(imomName);
        if (pathName == null) {
            throw new RuntimeException("Imom topilmadi");
        }

        int n = 1;
        for (int i = 1; i < 115; i++) {
            String formatted = String.format("%03d", n);
            String audioUrls = "https://server8.mp3quran.net/" + pathName + "/" + formatted + ".mp3";
            sendAudio.setChatId(chatId);
            System.out.println("Attempting to send audio from URL: " + audioUrls);
            sendAudio.setAudio(new InputFile(audioUrls));
            sendAudio.setCaption("Quran");
            setSendAudio(sendAudio);
            n += 1;
        }
        return sendAudio;
    }
}
