package analyze;

import train.SentimentalType;

public class Verdict {
    private double beurnoulliNegativeScore;
    private double beurnoulliPositiveScore;
    private double multinominalNegativeScore;
    private double multinominalPositiveScore;

    public Verdict(double beurnoulliNegativeScore, double beurnoulliPositiveScore, double multinominalNegativeScore, double multinominalPositiveScore) {
        this.beurnoulliNegativeScore = beurnoulliNegativeScore;
        this.beurnoulliPositiveScore = beurnoulliPositiveScore;
        this.multinominalNegativeScore = multinominalNegativeScore;
        this.multinominalPositiveScore = multinominalPositiveScore;
        if (Math.max(beurnoulliNegativeScore, beurnoulliPositiveScore) == beurnoulliNegativeScore) {
            beurnoulliVerdict = SentimentalType.NEGATIVE;
        } else beurnoulliVerdict = SentimentalType.POSITIVE;
        if (Math.max(multinominalNegativeScore, multinominalPositiveScore) == multinominalNegativeScore) {
            mulltinominalVerdict = SentimentalType.NEGATIVE;
        } else mulltinominalVerdict = SentimentalType.POSITIVE;
    }

    private SentimentalType beurnoulliVerdict;
    private SentimentalType mulltinominalVerdict;

    @Override
    public String toString() {
        return "beurnoulliNegativeScore=" + beurnoulliNegativeScore +System.lineSeparator()+
                ", beurnoulliPositiveScore=" + beurnoulliPositiveScore + System.lineSeparator()+
                ", beurnoulliVerdict=" + beurnoulliVerdict + System.lineSeparator()+
                ", multinominalNegativeScore=" + multinominalNegativeScore +System.lineSeparator()+
                ", multinominalPositiveScore=" + multinominalPositiveScore + System.lineSeparator()+
                ", mulltinominalVerdict=" + mulltinominalVerdict + System.lineSeparator();
    }
}
