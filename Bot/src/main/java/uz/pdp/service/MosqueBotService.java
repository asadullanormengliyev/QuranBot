package uz.pdp.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.util.BotUtil;

import java.util.List;

public class MosqueBotService {

    public SendMessage requestUserLocation(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please share your location to find the nearest mosque.");

        List<String> stringList = List.of("Share Location", "Back");
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.replyKeyboardMarkup(stringList, 1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    public String getMapLink(Location userLocation) {
        double latitude = userLocation.getLatitude();
        double longitude = userLocation.getLongitude();

        // Google Maps URL for nearby mosques
        return String.format(
                "https://www.google.com/maps/search/?api=1&query=mosque&location=%f,%f",
                latitude, longitude);
    }
}
