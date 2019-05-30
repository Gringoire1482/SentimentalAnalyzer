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
            beurnoulliRezolution = TextClass.SPAM;
        }else  beurnoulliRezolution=TextClass.HAM;
        if (Math.max(multinominalNegativeScore, multinominalPositiveScore) == multinominalNegativeScore) {
            mulltinominalRezolution = TextClass.SPAM;
        } else mulltinominalRezolution= TextClass.HAM;
    }
    SentimentalType beurnoulliRezolution;
    SentimentalType mulltinominalRezolution;

}
