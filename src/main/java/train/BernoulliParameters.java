package train;

public class BernoulliParameters {
    private long positiveDocumentWithToken;
    private long negativeDocumentWithToken;

    public long getPositiveDocumentWithToken() {
        return positiveDocumentWithToken;
    }

    public void setPositiveDocumentWithToken(long positiveDocumentWithToken) {
        this.positiveDocumentWithToken = positiveDocumentWithToken;
    }

    public long getNegativeDocumentWithToken() {
        return negativeDocumentWithToken;
    }

    public void setNegativeDocumentWithToken(long negativeDocumentWithToken) {
        this.negativeDocumentWithToken = negativeDocumentWithToken;
    }

    @Override
    public String toString() {
        return "BernoulliParameters{" +
                "positiveDocumentWithToken=" + positiveDocumentWithToken +
                ", negativeDocumentWithToken=" + negativeDocumentWithToken +
                '}';
    }
}
