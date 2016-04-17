package sample.model;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import twitter4j.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by hiba on 26/08/2015.
 */
public class TweetsXmlWriter {


    private Document doc;
    private Element tweets;
    private int fileNumber;

    public TweetsXmlWriter(int fileNumber) {
        this.tweets = new Element("tweets");
        this.doc = new Document(tweets);
        this.fileNumber = fileNumber;
        //doc.setRootElement(tweets);
    }

    public void saveXmlFile(List<Status> tweetList){
        for (Status tweet : tweetList){


            long id = tweet.getId();
            long[] contributors = tweet.getContributors();
            int nbrFavoris = tweet.getFavoriteCount();
            String lang = tweet.getLang();
            Place place = tweet.getPlace();
            GeoLocation loc = tweet.getGeoLocation();
            int nbrRetweet = tweet.getRetweetCount();
            HashtagEntity[] hashtagEntities = tweet.getHashtagEntities();
            String hashtagEntitiesStr = hashtagEntities.toString();
            MediaEntity[] mediaEntities = tweet.getMediaEntities();


            String user = tweet.getUser().getScreenName();
            String msg = tweet.getText();
            String date = tweet.getCreatedAt().toString();

            String placeFullName = "";
            if (place != null){
                placeFullName = place.getFullName();
            }


            Element tweetElement = new Element("tweet");
            tweetElement.setAttribute(new Attribute("id", id+"" ));
            tweetElement.addContent(new Element("user").setText( user ));
            tweetElement.addContent(new Element("tweettext").setText( msg ));
            if (loc != null){
                Element geoloc = new Element("location");
                geoloc.addContent(new Element("latitude").setText( tweet.getGeoLocation().getLatitude()+"" ));
                geoloc.addContent(new Element("longitude").setText( tweet.getGeoLocation().getLongitude()+"" ));
                tweetElement.addContent(geoloc);
            }
            tweetElement.addContent(new Element("date").setText( date ));
            tweetElement.addContent(new Element("favoris").setText( nbrFavoris+"" ));
            tweetElement.addContent(new Element("language").setText( lang ));
            if (place != null){
                tweetElement.addContent(new Element("placename").setText( placeFullName ));
            }
            tweetElement.addContent(new Element("retweets").setText(nbrRetweet + ""));
            tweetElement.addContent(new Element("sentiment").setText( "" ));


            doc.getRootElement().addContent(tweetElement);




        }

        // new XMLOutputter().output(doc, System.out);
        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlOutput.output(doc, new FileWriter("tweets"+fileNumber+".xml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("- - - - - - - File #"+fileNumber+" Saved!");


    }




}