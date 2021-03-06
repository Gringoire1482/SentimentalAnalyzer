package train;

import java.util.Arrays;
import java.util.List;

public class Tweet {
    private long id;
    private String content;
    private String tokenized;
    private SentimentalType tweetType;
    private  List<String> tokenList;

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
        StringBuilder stringBuilder = new StringBuilder();
        // tokenized=tokenized.replaceAll("ё","е").replaceAll("й","и");
        //content=content.replaceAll("ё","е").replaceAll("й","и");
       tokenized =  tokenized.replaceAll("(?i)([а-яА-ЯёЁ])\\1{1,}","$1");
        String[] tokens = tokenized.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (i!=tokens.length-1&&(tokens[i].equals("не") || tokens[i].equals("очень")) && tokens[i + 1].length() > 3) {
                stringBuilder.append(tokens[i]).append("_").append(tokens[++i]).append(" ");
            } else {
                stringBuilder.append(tokens[i]).append(" ");
            }

        }
        this.tokenList= Arrays.asList(stringBuilder.toString().split(" "));
        this.tokenized = stringBuilder.toString();
    }

    public SentimentalType getTweetType() {
        return tweetType;
    }

    public void setTweetType(SentimentalType tweetType) {
        this.tweetType = tweetType;
    }

    public List<String> getTokenList() {return tokenList;
    }
}
