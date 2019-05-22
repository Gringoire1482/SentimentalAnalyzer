package train;

public class Token {
    private String content;
    private BernoulliParameters bernoulliParameters = new BernoulliParameters();
    private MultinominalParametrs multinominalParametrs = new MultinominalParametrs();
    private boolean isPresentInPositiveDocuments;
    private boolean isPresentInNegativeDocuments;

    private double bernoulliAposteriori;
    private double multinominalAposteriori;


    @Override
    public String toString() {
        return "Token{" +
                "content='" + content + '\'' +
                ", bernoulliParameters=" + bernoulliParameters +
                ", multinominalParametrs=" + multinominalParametrs +
                ", isPresentInPositiveDocuments=" + isPresentInPositiveDocuments +
                ", isPresentInNegativeDocuments=" + isPresentInNegativeDocuments +
                '}';
    }

    public boolean isPresentInPositiveDocuments() {
        return isPresentInPositiveDocuments;
    }

    public void setPresentInPositiveDocuments(boolean presentInPositiveDocuments) {
        isPresentInPositiveDocuments = presentInPositiveDocuments;
    }

    public boolean isPresentInNegativeDocuments() {
        return isPresentInNegativeDocuments;
    }

    public void setPresentInNegativeDocuments(boolean presentInNegativeDocuments) {
        isPresentInNegativeDocuments = presentInNegativeDocuments;
    }

    public Token(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BernoulliParameters getBernoulliParameters() {
        return bernoulliParameters;
    }

    public void setBernoulliParameters(BernoulliParameters bernoulliParameters) {
        this.bernoulliParameters = bernoulliParameters;
    }

    public MultinominalParametrs getMultinominalParametrs() {
        return multinominalParametrs;
    }

    public void setMultinominalParametrs(MultinominalParametrs multinominalParametrs) {
        this.multinominalParametrs = multinominalParametrs;
    }
}
