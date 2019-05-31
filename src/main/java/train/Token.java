package train;

public class Token {
    private String content;
    private BernoulliParameters bernoulliParameters = new BernoulliParameters();
    private MultinominalParametrs multinominalParametrs = new MultinominalParametrs();
    private boolean isPresentInPositiveDocuments;
    private boolean isPresentInNegativeDocuments;

    private double bernoulliAposterioriNegative;
    private double bernoulliAposterioriPositive;
    private double multinominalAposterioriNegative;
    private double multinominalAposterioriPositive;


    @Override
    public String toString() {
        return "Token{ " + content +
                " bernoulliAposterioriNegative=" + bernoulliAposterioriNegative +
                ", bernoulliAposterioriPositive=" + bernoulliAposterioriPositive +
                ", multinominalAposterioriNegative=" + multinominalAposterioriNegative +
                ", multinominalAposterioriPositive=" + multinominalAposterioriPositive +
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

    public double getBernoulliAposterioriNegative() {
        return bernoulliAposterioriNegative;
    }

    public void setBernoulliAposterioriNegative(double bernoulliAposterioriNegative) {
        this.bernoulliAposterioriNegative = bernoulliAposterioriNegative;
    }

    public double getBernoulliAposterioriPositive() {
        return bernoulliAposterioriPositive;
    }

    public void setBernoulliAposterioriPositive(double bernoulliAposterioriPositive) {
        this.bernoulliAposterioriPositive = bernoulliAposterioriPositive;
    }

    public double getMultinominalAposterioriNegative() {
        return multinominalAposterioriNegative;
    }

    public void setMultinominalAposterioriNegative(double multinominalAposterioriNegative) {
        this.multinominalAposterioriNegative = multinominalAposterioriNegative;
    }

    public double getMultinominalAposterioriPositive() {
        return multinominalAposterioriPositive;
    }

    public void setMultinominalAposterioriPositive(double multinominalAposterioriPositive) {
        this.multinominalAposterioriPositive = multinominalAposterioriPositive;
    }
}
