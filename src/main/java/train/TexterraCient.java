package train;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TexterraCient {
    private static final Logger LOGGER = Logger.getLogger(TexterraCient.class.getName());

    private static final String API_KEY = "eb7412e251e7ca28097f42c483c5371d06963f0a";
    private static final String URI = "https://api.ispras.ru/texterra/v1/nlp" +
            "?targetType=lemma&apikey="+API_KEY;

    public Set<Tweet> lemmatize(Set<Tweet> tweetSet, BatchSize batchSize) {
        Set<Tweet> lemmatizedSet = new LinkedHashSet<>();
        int partitionCount = (tweetSet.size() / batchSize.value)+1;
        List<Set<Tweet>> sets = new LinkedList<>();
        for (int i = 0; i < partitionCount; i++) {
            sets.add(new LinkedHashSet<>());
        }
        LOGGER.log(Level.INFO,"LEMMATIZATION START "+"// Input Elements : "+ tweetSet.size()+
                "// Batch Size : "+ batchSize.value+"// Partition count: "+ partitionCount + "// Sets created "+ sets.size() );
        int index = 0;
        for (Tweet tweet : tweetSet) {
            sets.get(index++ / batchSize.value).add(tweet);

        }
        for (Set<Tweet> tweetSets : sets) {
            JSONArray requestJSON = buildJSON(tweetSets);
            LOGGER.log(Level.INFO,"BATCH CREATING WITH ELEMENTS " +requestJSON.length()+ " "+ sets.indexOf(tweetSets));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(URI);
            httpPost.setEntity(new StringEntity(requestJSON.toString(), "UTF-8"));
            setHeaders(httpPost);
            HttpResponse response = null;
            try {

                LOGGER.log(Level.INFO,"Request Send"+ " "+ sets.indexOf(tweetSets));
                response = client.execute(httpPost);
                LOGGER.log(Level.INFO,"GET RESPONSE" + " "+ sets.indexOf(tweetSets));
                lemmatizedSet.addAll(parseJSON(tweetSets, new JSONArray(EntityUtils.toString(response.getEntity()))));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return lemmatizedSet;
    }

    private Set<Tweet> parseJSON(Set<Tweet> tweetSet, JSONArray responseJSON) {
        LOGGER.log(Level.INFO,"START PARSING RESPONSE SIZE " + responseJSON.length());
        // Iterator<Tweet> tweetIterator = tweetSet.iterator();
        int i = 0;
        for (Tweet tweet: tweetSet){
            JSONArray lemma = responseJSON.getJSONObject(i).getJSONObject("annotations").getJSONArray("lemma");
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < lemma.length(); j++) {
                stringBuilder.append(lemma.getJSONObject(j).getString("value")).append(" ");
            }
            tweet.setTokenized(stringBuilder.toString());
            LOGGER.log(Level.INFO,"Compare texts # " + i + " "+ tweet.getContent().equals(responseJSON.getJSONObject(i).getString("text")));
            i++;
        }
//        private Set<Tweet> parseJSON(Set<Tweet> tweetSet, JSONArray responseJSON) {
//        LOGGER.log(Level.INFO,"START PARSING RESPONSE SIZE " + responseJSON.length());
//        Iterator<Tweet> tweetIterator = tweetSet.iterator();
//        int i = 0;
//        while (tweetIterator.hasNext()) {
//            JSONArray lemma = responseJSON.getJSONObject(i).getJSONObject("annotations").getJSONArray("lemma");
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int j = 0; j < lemma.length(); j++) {
//                stringBuilder.append(lemma.getJSONObject(j).getString("value")).append(" ");
//            }
//            tweetIterator.next().setTokenized(stringBuilder.toString());
//            LOGGER.log(Level.INFO,"Compare" + );
//            i++;
//        }
        return tweetSet;
    }

    private JSONArray buildJSON(Set<Tweet> tweetSet) {
        JSONArray jsonArray = new JSONArray();
        for (Tweet tweet : tweetSet) {
            jsonArray.put(new JSONObject().put("text", tweet.getContent()));
        }
        return jsonArray;
    }

    private void setHeaders(HttpPost httpPost) {
        httpPost.setHeader("Accept", "application/json; charset=utf-8");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
    }


}
