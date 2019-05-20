package train;

public class Tweet {
    private long id;
    private  String content;
    private String  tokenized;
    private SentimentalType tweetType;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTokenized() {
        return tokenized;
    }

    public void setTokenized(String tokenized) {
        this.tokenized = tokenized;
    }

    public SentimentalType getTweetType() {
        return tweetType;
    }

    public void setTweetType(SentimentalType tweetType) {
        this.tweetType = tweetType;
    }
}
