package analyze;

import train.SentimentalType;

public class Verdict {
    double beurnoulliNegativeScore;
    double beurnoulliPositiveScore;
    double multinominalNegativeScore;
    double multinominalPositiveScore;
    public Verdict(double beurnoulliNegativeScore, double beurnoulliPositiveScore, double multinominalNegativeScore, double multinominalPositiveScore) {
        this.beurnoulliNegativeScore = beurnoulliNegativeScore;
        this.beurnoulliPositiveScore = beurnoulliPositiveScore;
        this.multinominalNegativeScore = multinominalNegativeScore;
        this.multinominalPositiveScore = multinominalPositiveScore;
        if (Math.max(beurnoulliNegativeScore, beurnoulliPositiveScore) == beurnoulliNegativeScore) {
            beurnoulliVerdict = SentimentalType.NEGATIVE;
        }else  beurnoulliVerdict =SentimentalType.POSITIVE;
        if (Math.max(multinominalNegativeScore, multinominalPositiveScore) == multinominalNegativeScore) {
            mulltinominalVerdict = SentimentalType.NEGATIVE;
        } else mulltinominalVerdict = SentimentalType.POSITIVE;
    }
    SentimentalType beurnoulliVerdict;
    SentimentalType mulltinominalVerdict;

}
