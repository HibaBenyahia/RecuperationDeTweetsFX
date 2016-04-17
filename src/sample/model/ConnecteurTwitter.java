package sample.model;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiba on 22/08/2015.
 */
public class ConnecteurTwitter {
    private Twitter twitter;


    public ConnecteurTwitter() {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        //Apple operation keys
        cb.setOAuthConsumerKey("q7VCCCbWLURzhx80BLwcQdE2s");
        cb.setOAuthConsumerSecret("dQ8jDqlJ5bFkP0LbbKggWF0I0REz1DDGo2Zjr6NIaA1SdBNVMn");
        cb.setOAuthAccessToken("2273766550-unTd0iavXtXuNmuhKwwduam7tDdgc1bvWPrxUEv");
        cb.setOAuthAccessTokenSecret("VVdSiqlK5yJpSUkZRiuXUBn9QKSlzkHflqFlYgnMrSOBD");

        //cb.setOAuthConsumerKey("mJVOvMPw7NBNm3Cuo9emhg");
        //cb.setOAuthConsumerSecret("OkzHuztUaFn6sx1QlUJ3kgvmpgw1JtC2OmlhGb6hqs");
        //cb.setOAuthAccessToken("2273766550-ohIFgxq8cmP1DXs6PYx6sZkEEnu3Hg2HNFbESfb");
        //cb.setOAuthAccessTokenSecret("NpniD914tJr70KVh1gR0pjBEOE0vfN46t9trzEuh7gtto");

        //cb.setOAuthConsumerKey("dVfnWgU5nJciIFBrvAHQ");
        //cb.setOAuthConsumerSecret("tCnCVzyXOeAbFWr5eTa9BcZGYznwfhtLgsRE1XO7XIY");
        //cb.setOAuthAccessToken("2392911902-Xa1d0oZXkogRFHagZ2FOSBcviE4el11Db7nHjSP");
        //cb.setOAuthAccessTokenSecret("nSgM8NFVE30s5vg6ZZAQWlwFSdNat9H5mi7lVP3Zt6hCB");
        this.twitter = new TwitterFactory(cb.build()).getInstance();

    }

    public List<Status> getTweets(int numberTweets, String motAChercher){
        List<Status> tweets =new ArrayList<Status>();

        //lang:en AND iphone SE
        Query query = new Query("lang:en AND "+motAChercher);     //  http://tinyurl.com/je2p4ch
        //query.setUntil("2015-11-14");
        long lastID = Long.MAX_VALUE;

        while (tweets.size () < numberTweets) {
            if (numberTweets - tweets.size() > 100)
                query.setCount(100);
            else
                query.setCount(numberTweets - tweets.size());
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t: tweets)
                    if(t.getId() < lastID) lastID = t.getId();

            }

            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            };
            query.setMaxId(lastID-1);

        }



        return tweets;
    }

    public Status getTweetOfId(String idTweet){ //126377656416612353
        Status result = null;

        try {
            result = twitter.showStatus(Long.parseLong( idTweet));
            System.out.println("@" + result.getUser().getScreenName() + " - " + result.getText());
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to show status: " + te.getMessage());
            System.exit(-1);
        }
        return  result;
    }

}
