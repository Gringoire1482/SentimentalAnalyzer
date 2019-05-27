package train;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tokenizer {
    private static final Logger LOGGER = Logger.getLogger(Tokenizer.class.getName());

    public Set<Token> processTokens(Set<Token> tokenSet, Set<Tweet> tweetSet) {
        System.out.println("Start Tokenizing"+ new Date());
        LOGGER.log(Level.INFO, "START TOKEN PROCESSING");
        for (Token token : tokenSet) {
            LOGGER.log(Level.INFO, "TOKEN " + token.getContent());


            token
                    .getBernoulliParameters().setNegativeDocumentWithToken(
                    tweetSet
                            .stream()
                            .filter(
                                    a -> a
                                            .getTweetType()
                                            ==
                                                    SentimentalType.NEGATIVE

                                            &&
                                            a
                                                    .getTokenList()
                                                    .contains(
                                                            token.getContent()
                                                    )
                            )
                            .count()
            );


            token
                    .getBernoulliParameters()
                    .setPositiveDocumentWithToken(
                            tweetSet
                                    .stream()
                                    .filter(
                                            a -> a
                                                    .getTweetType()
                                                    ==
                                                            SentimentalType.POSITIVE

                                                    &&
                                                    a
                                                            .getTokenList()
                                                            .contains(
                                                                    token.getContent())
                                    )
                                    .count()
                    );


            token
                    .getMultinominalParametrs()
                    .setNegativeDocumentTokensCount(
                            tweetSet
                                    .stream()
                                    .filter(
                                            a -> a
                                                    .getTweetType()
                                                    ==
                                                            SentimentalType.NEGATIVE

                                    )
                                    .mapToLong(
                                            a -> a
                                                    .getTokenList()
                                                    .stream()
                                                    .filter(
                                                            s -> s
                                                                    .equals(
                                                                            token.getContent()
                                                                    )
                                                    )
                                                    .count()
                                    )
                                    .sum()
                    );


            token.getMultinominalParametrs()
                    .setPositiveDocumentTokensCount(
                            tweetSet
                                    .stream()
                                    .filter(
                                            a -> a
                                                    .getTweetType()
                                                    ==
                                                            SentimentalType.POSITIVE

                                    )
                                    .mapToLong(
                                            a -> a
                                                    .getTokenList()
                                                    .stream()
                                                    .filter(
                                                            s -> s
                                                                    .equals(
                                                                            token.getContent()
                                                                    )
                                                    )
                                                    .count()
                                    )
                                    .sum()
                    );
            LOGGER.log(Level.INFO, "PROCESSING RESULT " + token.getBernoulliParameters().toString() + " " + token.getMultinominalParametrs().toString());

        }
       // tokenSet.stream().filter(a->a.getBernoulliParameters().getNegativeDocumentWithToken()>0).forEach(a->a.setPresentInNegativeDocuments(true));
       // tokenSet.stream().filter(a->a.getBernoulliParameters().getPositiveDocumentWithToken()>0).forEach(a->a.setPresentInPositiveDocuments(true));
        return tokenSet;
    }


}
