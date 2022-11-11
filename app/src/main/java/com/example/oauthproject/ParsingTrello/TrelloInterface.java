package com.example.oauthproject.ParsingTrello;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrelloInterface {
    @GET("members/me/boards?fields=name,url&")
    Call<List<Board>> getBoards(@Query("key") String key, @Query("token") String token);

    @GET("{id}/?ields=name&lists=all&list_fields=name,url&")
    Call<BoardLists> getBoardsLists(@Path("id") String id, @Query("key") String key,
                                    @Query("token") String token);
}
