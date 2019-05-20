package train;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TexterraCient {
    private static final String URI = "https://api.ispras.ru/texterra/v1/nlp?targetType=lemma&apikey=eb7412e251e7ca28097f42c483c5371d06963f0a";
    private static final String API_KEY = "eb7412e251e7ca28097f42c483c5371d06963f0a";

    public Set<Tweet> lemmatize(Set<Tweet> tweetSet, BatchSize batchSize) {
        int partitionCount = tweetSet.size() / batchSize.size + 1;
        List<Set<Tweet>> sets = new ArrayList<Set<Tweet>>(partitionCount);
        for (int i = 0; i < partitionCount; i++) {
            sets.add(new HashSet<Tweet>());
        }
        int index = 0;
        for (Tweet tweet : tweetSet) {
            sets.get(index++ / partitionCount).add(tweet);

        }
        for (Set<Tweet> tweetSets : sets) {
            String requestString = buildJSON(tweetSets);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(URI);
            httpPost.setEntity(new StringEntity(requestString, "UTF-8"));
            setHeaders(httpPost);
            HttpResponse response=null;
            try {
                response = client.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private String buildJSON(Set<Tweet> tweetSet) {
        JSONArray jsonArray = new JSONArray();
        for (Tweet tweet : tweetSet) {
            jsonArray.put(new JSONObject().put("text", tweet.getContent()));
        }
        return jsonArray.toString();
    }

    private HttpPost setHeaders(HttpPost httpPost) {
        httpPost.setHeader("Accept", "application/json; charset=utf-8");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("targetType", "lemma");
        httpPost.setHeader("apikey", API_KEY);
        return httpPost;
    }


}
