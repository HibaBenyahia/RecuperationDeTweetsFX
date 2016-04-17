package sample.test;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CollectionsDemo {

    public static void main(String[] args) {

        // Use Java Collections to create the List.
        ArrayList<String> list = new ArrayList<String>();

        // Now add observability by wrapping it with ObservableList.
        ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("Detected a change! ");
            }
        });

        // Changes to the observableList WILL be reported.
        // This line will print out "Detected a change!"
        observableList.add("item one");

        // Changes to the underlying list will NOT be reported
        // Nothing will be printed as a result of the next line.
        list.add("item two");

        System.out.println("Size: "+observableList.size());

    }
}
