# News Summarizer (Android) 📰

This project is inspired by my Spring Boot backend project [`news-summarizer-with-mistralAI`](https://github.com/your-username/news-summarizer-with-mistralAI). While this Android app doesn't do AI summarization just yet, it serves as the mobile frontend that hooks into [News API](https://newsapi.org/) to pull the top news headlines for the default country (`us`) and post them straight to a clean Android UI.

---

## Screenshots / App preview

Here are a few preview images from the application:

<div align="center">
   <img width="200" alt="mobile 3" src="https://github.com/user-attachments/assets/3e89df4e-32f3-45cb-bce7-67eb0feb6c6f" />
   <img width="200" alt="mobile 2" src="https://github.com/user-attachments/assets/ee246a8f-3458-40f6-8f37-1906077d1c35" />
   <img width="200" alt="mobile 1" src="https://github.com/user-attachments/assets/ec4168af-f470-4dcf-b73f-d94ebb761a95" />
   <img width="200" alt="mobile 4" src="https://github.com/user-attachments/assets/f238ff60-4d01-4d13-9488-4d63bd162d23" />
</div>

Simply tap any news card to launch the full article directly in your web browser.

---

## Features

- **Top Headlines Feed**: Fetches real-time top news headlines from News API (`/v2/top-headlines`).
- **Card Layout**: Displays news items in Material 3 CardViews with article thumbnail images, titles, news sources, and description snippets.
- **Async Image Loading**: Uses [Glide](https://github.com/bumptech/glide) for smooth image caching and loading.
- **In-App Browser Jump**: Tapping on any news card opens the full original article in your browser.
- **Response Logging**: Built-in Logcat integration (using OkHttp `HttpLoggingInterceptor` and custom `NewsAPI` tags) to easily inspect API responses and debug network calls.

---

## Tech Stack & Dependencies

- **Language**: Java / Android SDK
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) + [Gson Converter](https://github.com/google/gson)
- **Logging**: OkHttp `HttpLoggingInterceptor`
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **UI Components**: `RecyclerView`, `MaterialCardView`, `ConstraintLayout`, `ProgressBar`

---

## Getting Started

1. **Clone the Repo**:
   ```bash
   git clone https://github.com/your-username/news-summarizer-android.git
   cd news-summarizer-android
   ```

2. **Open in Android Studio**:
   Open the project folder in Android Studio and let Gradle sync the dependencies.

3. **Get a News API Key**:
   Sign up for a free API key at [newsapi.org](https://newsapi.org/).

4. **Add your API Key**:
   Open `MainActivity.java` (`app/src/main/java/com/example/news_summarizer_android/MainActivity.java`) and update the `API_KEY` field:
   ```java
   private static final String API_KEY = "YOUR_NEWS_API_KEY_HERE";
   ```

5. **Run the App**:
   Press **Run** (`Shift + F10`) to build and launch the app on an emulator or physical Android device.

---

## Debugging & Logs

Want to inspect the raw JSON coming from News API? Open the **Logcat** window in Android Studio and filter by:
```text
tag:NewsAPI
```
or 
```text
tag:OkHttp
```
You'll see the full request/response headers, status codes, and JSON response payload in real-time.

---

## Future Ideas

- [ ] Connect with the `news-summarizer-with-mistralAI` Spring Boot backend to generate AI bullet-point summaries for each article.
- [ ] Add category tabs (Technology, Business, Sports, Entertainment, etc.).
- [ ] Add country selector and search functionality.


Thank you for reading!
