package train;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

public class ModelGenerator {

    private static final Logger LOGGER = Logger.getLogger(Train.class.getName());
    private static final double SMOOTHING = 1;
    private static final File bernoulliModel = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModel.txt");
    private static final File multinominalModel = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModel.txt");


    public void buildProbabilityModelMatrix(Set<Token> tokenSet, Set<Tweet> tweetSet) {
        long documentCount = tweetSet.size();
        long tokenCount = tokenSet.size();
        long negativeDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.NEGATIVE)).count();
        long positiveDocuments = tweetSet.stream().filter(a -> a.getTweetType().equals(SentimentalType.POSITIVE)).count();
        long tokensDictionaryNegativeDocuments = tokenSet.stream().filter(Token::isPresentInNegativeDocuments).count();
        long tokensDictionaryPositiveDocuments = tokenSet.stream().filter(Token::isPresentInPositiveDocuments).count();
        LOGGER.log(Level.INFO, "BUILDING PARAMS " + " Document count : " + documentCount + " Token Count : " + tokenCount + " negative Documents : " + negativeDocuments + " positive Documents : " + positiveDocuments + " token Base negative Documents : " + tokensDictionaryNegativeDocuments + " token Base positive Documents : " + tokensDictionaryPositiveDocuments);
        for (Token token : tokenSet) {
            // LOGGER.log(Level.INFO,token.getBernoulliParameters().getNegativeDocumentWithToken()  );
            token.setBernoulliAposterioriNegative((token.getBernoulliParameters().getNegativeDocumentWithToken() + SMOOTHING) / (negativeDocuments + documentCount * SMOOTHING));
            token.setBernoulliAposterioriPositive((token.getBernoulliParameters().getPositiveDocumentWithToken() + SMOOTHING) / (positiveDocuments + documentCount * SMOOTHING));
            token.setMultinominalAposterioriNegative((token.getMultinominalParametrs().getNegativeDocumentTokensCount() + SMOOTHING) / (tokensDictionaryNegativeDocuments + tokenCount * SMOOTHING));
            token.setMultinominalAposterioriPositive((token.getMultinominalParametrs().getPositiveDocumentTokensCount() + SMOOTHING) / (tokensDictionaryPositiveDocuments + tokenCount * SMOOTHING));
        }
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
