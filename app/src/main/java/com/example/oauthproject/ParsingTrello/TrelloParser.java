package com.example.oauthproject.ParsingTrello;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrelloParser {
    Retrofit retrofit;
    Retrofit retrofitList;
    private final TrelloInterface boardAPI;
    private final TrelloInterface boardListAPI;

    public TrelloParser(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.trello.com/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        boardAPI = retrofit.create(TrelloInterface.class);

        retrofitList = new Retrofit.Builder()
                .baseUrl("https://api.trello.com/1/boards/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        boardListAPI = retrofitList.create(TrelloInterface.class);
    }

    public TrelloInterface getBoardAPI() {
        return boardAPI;
    }

    public TrelloInterface getBoardListAPI() {
        return boardListAPI;
    }
}
