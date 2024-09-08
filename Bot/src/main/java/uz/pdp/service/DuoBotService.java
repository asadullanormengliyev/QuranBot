package uz.pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.model.Duolar;
import uz.pdp.util.BotUtil;
import uz.pdp.util.FilePath;
import uz.pdp.util.JsonUtil;

import java.util.List;

public class DuoBotService {

    public  SendMessage duolarMessage(Long chatId,String duoName){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Duolar duolar = duolar(duoName);
        if (duolar != null){
            sendMessage.setText(duolar.getText());
        }else {
            sendMessage.setText("Dua not found.");
        }
        return sendMessage;
    }

    private Duolar duolar(String duoName) {
        List<Duolar> duolars = JsonUtil.readGson(FilePath.PATH_DUO, new TypeReference<>() {});
        for (Duolar duolar1 : duolars) {
            if (duolar1.getName().equalsIgnoreCase(duoName)) {
                return duolar1;
            }
        }
        return null;
    }

    public SendMessage duolarMenu(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<String> list = List.of("\uD83E\uDD32 Qur`onda kelgan duolar", "\uD83C\uDF19 Sunnatda kelgan duolar", "\uD83C\uDFD9\uFE0F\uD83C\uDF03Tongi va kechki duolar","\uD83D\uDED0Nomozdagi duolar","Back");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(list, 2);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage tongiVaKechkiduolar(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<String> list = List.of("Tong va kechqurunda", "Juma tongida", "Uyquda qo'rqqanda", "Tushida yoqtirmagan narsani ko'rganda", "Uyqudan uyg'onganda", "Back");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(list, 2);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage nomozdagiDuolar(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<String> list = List.of("Taxorat qilganda", "Masjidga kirayotganda va undan chiqayotganda", "Azon va iqomat eshitganda", "Nomoz boshlanganda", "Nomozdagi rukuda", "Rukudan bosh ko'targanda", "Sajda payitida", "Tashaxxud","Vitr nomozida qunt duosi", "Tashaxxuddan keyingi salovatlar", "Namoz tugaganda","Istig'for", "Back");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(list, 3);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
