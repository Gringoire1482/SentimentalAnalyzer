package train;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModelGenerator {

    private static final Logger LOGGER = Logger.getLogger(Train.class.getName());
    private static final double SMOOTHING = 1;
    private static final File bernoulliModel = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModel.txt");
    private static final File multinominalModel = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModel.txt");


    public void buildProbabilityModelMatrix(Set<Token> tokenSet, Set<Tweet> tweetSet) {
        System.out.println("Matrix building " + new Date());
        long documentCount = tweetSet.size();
        long tokenCount = tokenSet.size();
        double negativeDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.NEGATIVE)).count();
        double positiveDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.POSITIVE)).count();
        double tokensCountNegativeDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.NEGATIVE)).mapToLong(a -> a.getTokenList().size()).sum();
        double tokensCountPositiveDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.POSITIVE)).mapToLong(a -> a.getTokenList().size()).sum();
        LOGGER.log(Level.INFO, "BUILDING PARAMS " + " Document count : " + documentCount + " Token Count : " + tokenCount + " negative Documents : " + negativeDocuments + " positive Documents : " + positiveDocuments + " token Base negative Documents : " + tokensCountNegativeDocuments + " token Base positive Documents : " + tokensCountPositiveDocuments);
        for (Token token : tokenSet) {
            // LOGGER.log(Level.INFO,token.getBernoulliParameters().getNegativeDocumentWithToken()  );
            token.setBernoulliAposterioriNegative(token.getBernoulliParameters().getNegativeDocumentWithToken() / negativeDocuments);
            token.setBernoulliAposterioriPositive(token.getBernoulliParameters().getPositiveDocumentWithToken() / positiveDocuments);
            token.setMultinominalAposterioriNegative(token.getMultinominalParametrs().getNegativeDocumentTokensCount() / tokensCountNegativeDocuments);
            token.setMultinominalAposterioriPositive(token.getMultinominalParametrs().getPositiveDocumentTokensCount() / tokensCountPositiveDocuments);
        }
        System.out.println("Matrix writing " + new Date());
        writeTofiles(tokenSet);

    }

    private static void writeTofiles(Set<Token> tokenSet) {
        if (bernoulliModel.exists() || multinominalModel.exists()) {
            bernoulliModel.delete();
            multinominalModel.delete();
        }


        BufferedWriter bernoulli = null;
        BufferedWriter multinominal = null;
        try {
            bernoulliModel.createNewFile();
            multinominalModel.createNewFile();
            bernoulli = new BufferedWriter(new FileWriter(bernoulliModel));
            multinominal = new BufferedWriter(new FileWriter(multinominalModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Token token : tokenSet) {
            try {
                bernoulli.write(token.getContent() + "," + token.getBernoulliAposterioriPositive() + "," + token.getBernoulliAposterioriNegative());
                multinominal.write(token.getContent() + "," + token.getMultinominalAposterioriPositive() + "," + token.getMultinominalAposterioriNegative());
                bernoulli.newLine();
                multinominal.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bernoulli.close();
            multinominal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
