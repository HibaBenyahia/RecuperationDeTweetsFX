package sample.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sample.model.ConnecteurTwitter;
import sample.model.Tweet;
import sample.model.TweetsXmlWriter;
import twitter4j.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainGuiController {

    private ObservableList<Tweet> listeDeTweet = FXCollections.observableArrayList();
    private boolean allowRecuperation;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField tfMotAChercher;
    @FXML
    private Text tvProgress;
    @FXML
    private Button btnPauseRecupererTweet;
    @FXML
    private Button btnRecupererTweet;
    @FXML
    private TableView<Tweet> tweetTable;
    @FXML
    private TableColumn<Tweet, String> compteColumn;
    @FXML
    private TableColumn<Tweet, LocalDate> dateColumn;
    @FXML
    private TableColumn<Tweet, String> geoLocationColumn;
    @FXML
    private TableColumn<Tweet, String> messageColumn;

    @FXML
    void recupererTweetsEnArrierePlan(){
        btnRecupererTweet.setDisable( true );
        Task<Void> moteurDeRecuperation = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                ConnecteurTwitter connecteurTwitter = new ConnecteurTwitter();

                int repetition = 1;
                while ( repetition <= 20 ){

                    try {

                        if (allowRecuperation){

                            //Recupération sou forme de Status
                            List<Status> listeDeTweetsRecuperes =  connecteurTwitter.getTweets(5, tfMotAChercher.getText());
                            System.out.println( listeDeTweetsRecuperes );

                            //Conversion à Tweet pour attacher à Observable List
                            ArrayList<Tweet> nouvelsTweets = convertListFromStatusToTweet( listeDeTweetsRecuperes );

                            //Attachement
                            listeDeTweet.addAll( nouvelsTweets );

                            //Enregistrement des nouvels tweets
                            saveTweetsRecupererDansFichierXML(repetition, listeDeTweetsRecuperes);

                            repetition ++;

                        }

                        Thread.sleep( 10000 );  //16 mn
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    final double prog = repetition * 0.05;
                    final String step = repetition+"/20";

                    Platform.runLater(() -> {
                        progressBar.setProgress(prog);
                        tvProgress.setText(step);
                    });




                }


                return null;
            }

        };

        //start Task
        new Thread( moteurDeRecuperation ).start();

    }

    private void saveTweetsRecupererDansFichierXML(int repetition, List<Status> listeDeTweetsRecuperes) {
        TweetsXmlWriter tweetsXmlWriter = new TweetsXmlWriter( repetition );
        tweetsXmlWriter.saveXmlFile( listeDeTweetsRecuperes );
    }

    private ArrayList<Tweet> convertListFromStatusToTweet(List<Status> listeDeTweetsRecuperes) {
        ArrayList<Tweet> result = new ArrayList<>();

        for (Status status: listeDeTweetsRecuperes) {
            String id = status.getId()+"";
            String compte = status.getUser().getScreenName();
            Date input = status.getCreatedAt();
            LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String message = status.getText();
            String geoLocation = "";
            if (status.getGeoLocation() != null)
                geoLocation = status.getGeoLocation().toString();

            Tweet tweet = new Tweet(id, compte, date, geoLocation, message, "");
            result.add( tweet );

        }


        return result;
    }

    @FXML
    private void initialize() {

        // add observable action to list
        addListenerToListOffTweet();

        // Initialize the tweet table with the two columns.
        compteColumn.setCellValueFactory(cellData -> cellData.getValue().compteProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        geoLocationColumn.setCellValueFactory(cellData -> cellData.getValue().geoLocationProperty());
        messageColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

        allowRecuperation = true;

    }

    private void addListenerToListOffTweet() {
        listeDeTweet.addListener((ListChangeListener) change -> updateTableOfTweets() );
    }

    private void updateTableOfTweets() {
        tweetTable.setItems( listeDeTweet );
    }

    @FXML
    void AllowDontAllowRecuperation(){
        allowRecuperation = ( ! allowRecuperation );
        if (allowRecuperation)
            btnPauseRecupererTweet.setText( "Pause" );
        else
            btnPauseRecupererTweet.setText( "Continuer..");
    }

}
