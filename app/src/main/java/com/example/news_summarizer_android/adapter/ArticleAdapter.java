package com.example.news_summarizer_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news_summarizer_android.R;
import com.example.news_summarizer_android.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context context;
    private final List<Article> articles = new ArrayList<>();

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    public void setArticles(List<Article> newArticles) {
        articles.clear();
        if (newArticles != null) {
            articles.addAll(newArticles);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.textViewTitle.setText(article.getTitle() != null ? article.getTitle() : "");

        if (article.getSource() != null && article.getSource().getName() != null) {
            holder.textViewSource.setText(article.getSource().getName());
            holder.textViewSource.setVisibility(View.VISIBLE);
        } else {
            holder.textViewSource.setVisibility(View.GONE);
        }

        if (article.getDescription() != null && !article.getDescription().isEmpty()) {
            holder.textViewDescription.setText(article.getDescription());
            holder.textViewDescription.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDescription.setVisibility(View.GONE);
        }

        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Glide.with(context)
                    .load(article.getUrlToImage())
                    .centerCrop()
                    .into(holder.imageViewArticle);
            holder.imageViewArticle.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewArticle.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (article.getUrl() != null && !article.getUrl().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewArticle;
        TextView textViewSource;
        TextView textViewTitle;
        TextView textViewDescription;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewArticle = itemView.findViewById(R.id.imageViewArticle);
            textViewSource = itemView.findViewById(R.id.textViewSource);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
