package sample.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Oussama on 17/04/2016.
 */
public class Tweet {

    private StringProperty idTweet;
    private StringProperty compte;
    private ObjectProperty<LocalDate> date;
    private StringProperty geoLocation;
    private StringProperty message;
    private StringProperty sentiment;

    public Tweet(StringProperty idTweet, StringProperty compte, ObjectProperty<LocalDate> date, StringProperty geoLocation, StringProperty message, StringProperty sentiment) {
        this.idTweet = idTweet;
        this.compte = compte;
        this.date = date;
        this.geoLocation = geoLocation;
        this.message = message;
        this.sentiment = sentiment;
    }

    public Tweet(String idTweet, String compte, LocalDate date, String geoLocation, String message, String sentiment) {

        this.idTweet = new SimpleStringProperty(idTweet);
        this.compte = new SimpleStringProperty(compte);
        this.date = new SimpleObjectProperty<LocalDate>( date );
        this.geoLocation = new SimpleStringProperty( geoLocation );
        this.message = new SimpleStringProperty( message );
        this.sentiment = new SimpleStringProperty( sentiment );
    }

    public String getIdTweet() {
        return idTweet.get();
    }

    public StringProperty idTweetProperty() {
        return idTweet;
    }

    public void setIdTweet(String idTweet) {
        this.idTweet.set(idTweet);
    }

    public String getCompte() {
        return compte.get();
    }

    public StringProperty compteProperty() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte.set(compte);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getGeoLocation() {
        return geoLocation.get();
    }

    public StringProperty geoLocationProperty() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation.set(geoLocation);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getSentiment() {
        return sentiment.get();
    }

    public StringProperty sentimentProperty() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment.set(sentiment);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "idTweet=" + idTweet +
                ", compte=" + compte +
                ", date=" + date +
                ", geoLocation=" + geoLocation +
                ", message=" + message +
                ", sentiment=" + sentiment +
                '}';
    }
}
