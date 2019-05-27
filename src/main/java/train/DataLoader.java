package train;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DataLoader {
    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());
    private static Set<Tweet> tweetSet;
    private static long idCounter = 0;
    private File fileNegative = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\negative-general.csv");
    private File filePositive = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\positive-general.csv");

    private File stopFile = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\stopwords.txt");

    public Set<Tweet> create() {
        tweetSet = new LinkedHashSet<>();
        tweetSet.addAll(trainLoad(fileNegative, SentimentalType.NEGATIVE));
        tweetSet.addAll(trainLoad(filePositive, SentimentalType.POSITIVE));
        return tweetSet;
    }

    private Set<Tweet> trainLoad(File file, SentimentalType type) {
        Set<Tweet> result = new LinkedHashSet<>();
        Scanner sc = null;
        try {
            sc = new Scanner(file, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,e.toString());
        }

        StringBuilder stringBuilder = new StringBuilder();
        assert sc != null;
        int i=0;
        while (sc.hasNextLine()&& i<7000) {
            stringBuilder.setLength(0);
            stringBuilder.append(sc.nextLine());
            while (!stringBuilder.toString().endsWith("\";") && sc.hasNextLine()) {
                stringBuilder.append(" ").append(sc.nextLine());
            }
            Tweet tweet = new Tweet();
            tweet.setId(idCounter++);
            tweet.setContent(stringBuilder.toString());
            tweet.setTweetType(type);
            result.add(tweet);
            LOGGER.log(Level.INFO,type.toString()+" READ TWEET " + tweet.toString());
            i++;
        }
        return result;
    }

    public Set<String> loadStopWordsList() {
        Set<String> stopWords = new HashSet<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(stopFile, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,e.toString());
        }
        while (scanner.hasNextLine()) {
            stopWords.add(scanner.nextLine());
        }

        return stopWords;
    }
}
