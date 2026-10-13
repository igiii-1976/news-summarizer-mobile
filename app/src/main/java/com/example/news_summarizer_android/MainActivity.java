package com.example.news_summarizer_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_summarizer_android.adapter.ArticleAdapter;
import com.example.news_summarizer_android.api.NewsApiService;
import com.example.news_summarizer_android.api.RetrofitClient;
import com.example.news_summarizer_android.model.Article;
import com.example.news_summarizer_android.model.NewsResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NewsAPI";
    private static final String API_KEY = "5df80bfa35b444f7acc6cfebd7f07700";
    private static final String DEFAULT_COUNTRY = "us";

    private RecyclerView recyclerViewArticles;
    private ProgressBar progressBarLoading;
    private TextView textViewStatusMessage;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewArticles = findViewById(R.id.recyclerViewArticles);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        textViewStatusMessage = findViewById(R.id.textViewStatusMessage);

        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(this);
        recyclerViewArticles.setAdapter(articleAdapter);

        fetchTopHeadlines();
    }

    private void fetchTopHeadlines() {
        progressBarLoading.setVisibility(View.VISIBLE);
        textViewStatusMessage.setVisibility(View.GONE);

        if ("YOUR_NEWS_API_KEY".equals(API_KEY)) {
            progressBarLoading.setVisibility(View.GONE);
            textViewStatusMessage.setText("Please set your News API key in MainActivity.java to load top headlines.\nGet a free key at newsapi.org.");
            textViewStatusMessage.setVisibility(View.VISIBLE);
            return;
        }

        Log.d(TAG, "Fetching top headlines for country: " + DEFAULT_COUNTRY);

        NewsApiService apiService = RetrofitClient.getApiService();
        apiService.getTopHeadlines(DEFAULT_COUNTRY, API_KEY).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                progressBarLoading.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    String jsonResponse = new Gson().toJson(newsResponse);
                    Log.d(TAG, "Response Success [" + response.code() + "]: " + jsonResponse);

                    List<Article> articles = newsResponse.getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        Log.d(TAG, "Loaded " + articles.size() + " articles.");
                        articleAdapter.setArticles(articles);
                    } else {
                        Log.w(TAG, "Response contained no articles.");
                        textViewStatusMessage.setText("No news articles found.");
                        textViewStatusMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    String errorBodyStr = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBodyStr = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                    Log.e(TAG, "Response Failed [" + response.code() + "]: " + errorBodyStr);

                    textViewStatusMessage.setText("Failed to load news (Error " + response.code() + "). Please check your API key.");
                    textViewStatusMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                progressBarLoading.setVisibility(View.GONE);
                Log.e(TAG, "Network call failed: " + t.getLocalizedMessage(), t);

                textViewStatusMessage.setText("Network error: " + t.getLocalizedMessage());
                textViewStatusMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
