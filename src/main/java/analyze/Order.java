package analyze;

public class Order {
    private  String content;
    private  Verdict verdict;

    public Order(String content) {
        this.content = content;
        verdict= Analyzer.evaluate(content,"lapl");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

}
