package com.example.oauthproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oauthproject.ParsingTrello.Board;
import com.example.oauthproject.ParsingTrello.BoardLists;
import com.example.oauthproject.ParsingTrello.ListObj;
import com.example.oauthproject.ParsingTrello.TrelloInterface;
import com.example.oauthproject.ParsingTrello.TrelloParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    public int active = 0;
    public String ans = "";

    String token;
    String key = "4e9a82e4c4c9ee1debd9dbda562eb260";
    TextView textView;
    ListView listView;
    TrelloParser trelloParser;

    Map<String, List<String>> mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mp = new HashMap<>();
        listView = findViewById(R.id.listView);
//        textView = findViewById(R.id.textView);

        token = getIntent().getStringExtra("token");

        trelloParser = new TrelloParser();
        trelloParser.getBoardAPI().getBoards(key, token).enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(@NonNull Call<List<Board>> call, @NonNull Response<List<Board>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    updateData(response.body());
                } else {
                    Toast.makeText(ResultActivity.this, "Something go wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Board>> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(List<Board> body) {

        StringBuilder ans = new StringBuilder();
        for (Board i : body) {
            active += 1;
            trelloParser.getBoardListAPI().getBoardsLists(i.getId(), key, token).enqueue(new Callback<BoardLists>() {
                @Override
                public void onResponse(Call<BoardLists> call, Response<BoardLists> response) {
                    active -= 1;
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        for (ListObj i : response.body().getLists()) {
                            Log.d("Step", i.getName());
                            List<String> tmp = mp.get(response.body().getName());
                            if (tmp == null)
                                tmp = new ArrayList<>();
                            tmp.add(i.getName());
                            mp.put(response.body().getName(), tmp);

                        }
                        if (active <= 0) {
                            populateTextView(mp);
                        }
                    } else {
                        Toast.makeText(ResultActivity.this, "Something go wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BoardLists> call, Throwable t) {

                }
            });
            ans.append(i.getName()).append("\n");
        }

//        textView.setText(ans);

    }

    private void populateTextView(Map<String, List<String>> mp) {
        List<String> ans = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : mp.entrySet()) {
            StringBuilder tmpAns = new StringBuilder(entry.getKey());
            tmpAns.append("\n");
            List<String> tmp = entry.getValue();
            for (String i : tmp) {
                tmpAns.append("   ").append(i).append('\n');
            }
            ans.add(tmpAns.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ans);
        listView.setAdapter(adapter);
    }
}