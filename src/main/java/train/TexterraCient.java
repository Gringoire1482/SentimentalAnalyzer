package train;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TexterraCient {
    private static final Logger LOGGER = Logger.getLogger(TexterraCient.class.getName());
    private static int   key_Counter =0;
    private static final String API_KEY = "eb7412e251e7ca28097f42c483c5371d06963f0a";
    private static final String API_KEY2 = "4d1841f74d570d730ec732ee0cd9726d4dd32b31";
    private static final String URI = "https://api.ispras.ru/texterra/v1/nlp" +
            "?targetType=lemma&apikey=";

    public Set<Tweet> lemmatize(Set<Tweet> tweetSet, BatchSize batchSize) {
        tweetSet=tweetSet.stream().filter(a->!a.getContent().trim().isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new));
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
            HttpPost httpPost = new HttpPost(URI + (key_Counter%2==0? API_KEY:API_KEY2));
           // System.out.println(key_Counter%2==0? API_KEY:API_KEY2);
            httpPost.setEntity(new StringEntity(requestJSON.toString(), "UTF-8"));
            setHeaders(httpPost);
            HttpResponse response = null;
            try {

                LOGGER.log(Level.INFO,"Request Send"+ " "+ sets.indexOf(tweetSets));
                response = client.execute(httpPost);
                key_Counter++;
                LOGGER.log(Level.INFO,"GET RESPONSE" + " "+ sets.indexOf(tweetSets));
                String resp=EntityUtils.toString(response.getEntity());
                //System.out.println(resp);
                lemmatizedSet.addAll(parseJSON(tweetSets, new JSONArray(resp)));
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
            JSONArray lemma=null;
            try{ lemma = responseJSON.getJSONObject(i).getJSONObject("annotations").getJSONArray("lemma");
            }catch (JSONException e){
                e.printStackTrace();
                System.out.println(responseJSON.getJSONObject(i).getJSONObject("annotations").toString(4));
            }
           // JSONArray lemma = responseJSON.getJSONObject(i).getJSONObject("annotations").getJSONArray("lemma");
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
        for (Tweet tweet : tweetSet)
            jsonArray.put(new JSONObject().put("text", tweet.getContent()));
        return jsonArray;
    }

    private  static void setHeaders(HttpPost httpPost) {
        httpPost.setHeader("Accept", "application/json; charset=utf-8");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
    }
       public static List<String> lemmatizeOrderMessageString(String message){
        List<String> tokenizedMessage= new LinkedList<>();
           JSONArray jsonArray = new JSONArray();
           jsonArray.put(new JSONObject().put("text", message));
           HttpClient client = HttpClientBuilder.create().build();
           HttpPost httpPost = new HttpPost(URI + API_KEY2);
           httpPost.setEntity(new StringEntity(jsonArray.toString(), "UTF-8"));
           setHeaders(httpPost);
           JSONArray responseJSON=null;
           HttpResponse response = null;

           try {

               //LOGGER.log(Level.INFO,"Request Send"+ " "+ sets.indexOf(tweetSets));
               response = client.execute(httpPost);
               //key_Counter++;
               //LOGGER.log(Level.INFO,"GET RESPONSE" + " "+ sets.indexOf(tweetSets));
               String resp=EntityUtils.toString(response.getEntity());
                responseJSON= new JSONArray(resp);
               //System.out.println(resp);
               //lemmatizedSet.addAll(parseJSON(tweetSets, new JSONArray(resp)));
           } catch (IOException e) {
               e.printStackTrace();
           }
           JSONArray lemma=null;
           try{ lemma = responseJSON.getJSONObject(0).getJSONObject("annotations").getJSONArray("lemma");
           }catch (JSONException e){
               e.printStackTrace();
               System.out.println(responseJSON.getJSONObject(0).getJSONObject("annotations").toString(4));
           }
           // JSONArray lemma = responseJSON.getJSONObject(i).getJSONObject("annotations").getJSONArray("lemma");
           StringBuilder stringBuilder = new StringBuilder();
           for (int j = 0; j < lemma.length(); j++) {

               tokenizedMessage.add(lemma.getJSONObject(j).getString("value"));
           }

            return  tokenizedMessage;
       }

}
