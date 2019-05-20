package train;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class DataLoader {
    private static Set<Tweet> tweetSet;
    private static long idCounter = 0;
    private File fileNegative = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\demonegatile.txt");
    private File filePositive = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\demopositive.txt");

    public Set<Tweet> create() {
        tweetSet = new HashSet<>();
        tweetSet.addAll(trainLoad(fileNegative, SentimentalType.NEGATIVE));
        tweetSet.addAll(trainLoad(filePositive, SentimentalType.POSITIVE));
        return tweetSet;
    }

    private Set<Tweet> trainLoad(File file, SentimentalType type) {
        Set<Tweet> result = new HashSet<>();
        Scanner sc = null;
        try {
            sc = new Scanner(file, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        assert sc != null;
        while (sc.hasNextLine()) {
            stringBuilder.append(sc.nextLine());
            while (!stringBuilder.toString().endsWith("\";") && sc.hasNextLine()) {
                stringBuilder.append(" ").append(sc.nextLine());
            }
            Tweet tweet = new Tweet();
            tweet.setId(idCounter++);
            tweet.setContent(stringBuilder.toString());
            tweet.setTweetType(type);
            result.add(tweet);
            System.out.println(stringBuilder);
            System.out.println("========================");
        }
        return result;
    }
}
