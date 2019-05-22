package train;

import java.util.Arrays;
import java.util.List;

public class Tweet {
    private long id;
    private  String content;
    private String  tokenized;
    private SentimentalType tweetType;

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", tokenized='" + tokenized + '\'' +
                ", tweetType=" + tweetType +
                '}';
    }

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

    public List<String>  getTokenList(){
        return Arrays.asList(tokenized.split(" "));
    }
}
