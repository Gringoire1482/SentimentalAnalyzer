package train;

import java.util.Set;

public class ModelGenerator {
    private static final double SMOOTHING =1;
    public void buildBernoulliModelMatrix(Set<Token> tokenSet, Set<Tweet> tweetSet){
        long negativeDocuments= tweetSet.stream().filter(a->a.getTweetType().equals(SentimentalType.NEGATIVE)).count();
        long posititiveDocuments= tweetSet.stream().filter(a->a.getTweetType().equals(SentimentalType.POSITIVE)).count();
        tokenSet.stream().forEach(a->a.setBernoulliAposterioriNegative(a.getBernoulliParameters().getNegativeDocumentWithToken()+1/negativeDocuments+tweetSet.size()));
        tokenSet.stream().forEach(a->a.setBernoulliAposterioriNegative(a.getBernoulliParameters().getNegativeDocumentWithToken()+1/negativeDocuments+tweetSet.size()));

    }
}
