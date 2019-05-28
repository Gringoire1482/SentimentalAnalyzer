package train;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Train {
    private static final Logger LOGGER = Logger.getLogger(Train.class.getName());

    private static Set<String> stopWords;
    private static final DataLoader dataLoader = new DataLoader();

    public static void main(String[] args) {
        System.out.println(new Date());

        try {
            LogManager.getLogManager().readConfiguration(
                    Train.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.log(Level.INFO,"LOGGING START");
        Set<Tweet> data = dataLoader.create();
        LOGGER.log(Level.INFO,"Data Loaded");
        stopWords = dataLoader.loadStopWordsList();
        LOGGER.log(Level.INFO,"StopWords Loaded");
        data.forEach(Train::preprocess);
        TexterraCient texterraCient = new TexterraCient();
        data = texterraCient.lemmatize(data,BatchSize.TRAINING);
//        for (Tweet tweet: data){
//            System.out.println(tweet);
//        }
        System.out.println("Tweet set size"+ data.size());

//        long before = System.currentTimeMillis();
//       String s = data.stream().map(Tweet::getContent).collect(Collectors.joining(" "));
//        Set<String> soset =  new LinkedHashSet<>(Arrays.asList(s.split(" ")));
//        System.out.println(System.currentTimeMillis()-before);
//        System.out.println(soset.size());

       long before = System.currentTimeMillis();
        Set<String> tokens = new LinkedHashSet<>();
        data.stream().map(Tweet::getTokenList).forEach(tokens::addAll);
        tokens= tokens.stream().filter(a->a.trim().length()>2&&!a.contains(" ")).collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(System.currentTimeMillis()-before);
        System.out.println(tokens.size());

//  int matches=0;
//        for (String token: soset){
//            if (!s2.contains(token)){ System.out.println("AA"+ token+"_");}
//            else matches++;
//        }
//        System.out.println(matches);

//        Set<String> aa = data.stream().map(Tweet::getTokenList).map(List::toArray).collect(Collectors.toCollection(()->new LinkedHashSet<String>()));
        Set<Token> tokenSet = tokens.stream().map(Token::new).collect(Collectors.toCollection(LinkedHashSet::new));
        LOGGER.log(Level.INFO,"GOT TOKENS");

        Tokenizer tokenizer= new Tokenizer();
        tokenSet= tokenizer.processTokens(tokenSet,data);
        ModelGenerator modelGenerator= new ModelGenerator();
        modelGenerator.buildProbabilityModelMatrix(tokenSet,data);
        System.out.println(new Date());


    }

    private static Tweet preprocess(Tweet tweet) {

        //LOGGER.log(Level.INFO,"start preprocessing  of " + tweet.getContent() );
        String content = tweet.getContent();
        content= content.replaceAll("(;\")|(\";)","");
        String[] array = content.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringContent : array) {
            if ((stringContent.length() > 2 || stringContent.equals("не")) && !stopWords.contains(stringContent)) {
                stringBuilder.append(stringContent).append(" ");
            }
        }
       tweet.setContent(stringBuilder.toString());
        //LOGGER.log(Level.INFO,"Preprocess result " + stringBuilder.toString());
        return tweet;

    }


}
