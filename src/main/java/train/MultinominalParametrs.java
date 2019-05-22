package train;

public class MultinominalParametrs {
    private long positiveDocumentTokensCount;
    private long negativeDocumentTokensCount;

    public long getPositiveDocumentTokensCount() {
        return positiveDocumentTokensCount;
    }

    public void setPositiveDocumentTokensCount(long positiveDocumentTokensCount) {
        this.positiveDocumentTokensCount = positiveDocumentTokensCount;
    }

    public long getNegativeDocumentTokensCount() {
        return negativeDocumentTokensCount;
    }

    public void setNegativeDocumentTokensCount(long negativeDocumentTokensCount) {
        this.negativeDocumentTokensCount = negativeDocumentTokensCount;
    }

    @Override
    public String toString() {
        return "MultinominalParametrs{" +
                "positiveDocumentTokensCount=" + positiveDocumentTokensCount +
                ", negativeDocumentTokensCount=" + negativeDocumentTokensCount +
                '}';
    }
}
