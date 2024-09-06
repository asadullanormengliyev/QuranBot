package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.InputFile;

public class NamesAllahBotService {

    public SendAudio allohName(Long chatId) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        sendAudio.setAudio(new InputFile("https://file.uzhits.net/music/dl2/2020/02/15/dildora_niyozova_-_allohning_99_ta_gozal_ismlari_(uzhits.net).mp3"));
        sendAudio.setCaption("Allohning go'zal ismlari ");
        return sendAudio;
    }
}
