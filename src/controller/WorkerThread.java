package controller;

import javafx.application.Platform;
import main.MVC;
import model.Party;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class WorkerThread implements Runnable {
    private final String SERVER = "https://sendvotes.herokuapp.com/SendVotes?";
    private final Map<String, Integer> votesFromServer = new HashMap<>();
    private final MVC mvc;

    public WorkerThread(MVC mvc) {
        this.mvc = mvc;
    }

    @Override
    public void run() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
                String partiesStringTmp = "";

                for(Party party: mvc.model.getPartyList())
                    partiesStringTmp = partiesStringTmp.concat(party.getName() + ",");

                return partiesStringTmp.substring(0, partiesStringTmp.length() - 1);
        });

        Platform.runLater(futureTask);

        try {
            String partyString = futureTask.get();

            URL  url = new URL(SERVER + "howmany=1000&parties=" + URLEncoder.encode(partyString, "UTF-8"));
            URLConnection conn = url.openConnection();

            try (BufferedReader in = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
                String response;

                while((response = in.readLine()) != null) {
                    if(!votesFromServer.containsKey(response))
                        votesFromServer.put(response, 0);

                    votesFromServer.put(response, votesFromServer.get(response) + 1);
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

        for(String party: votesFromServer.keySet())
            Platform.runLater(() -> mvc.model.serverVotes(party, votesFromServer.get(party)));
    }
}
