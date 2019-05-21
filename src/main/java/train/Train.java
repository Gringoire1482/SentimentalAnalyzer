package train;

import java.util.Set;
import java.util.logging.Logger;

public class Train {
    private static final Logger LOGGER = Logger.getLogger(Train.class.getName());

    private static Set<String> stopWords;
    private static final DataLoader dataLoader = new DataLoader();

    public static void main(String[] args) {
        Set<Tweet> data = dataLoader.create();
        stopWords = dataLoader.loadStopWordsList();
        data.forEach(a -> preprocess(a.getContent()));
    }

    private static String preprocess(String content) {
        String[] array = content.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringContent : array) {
            if ((stringContent.length() > 2 || stringContent.equals("не")) && !stopWords.contains(stringContent)) {
                stringBuilder.append(stringContent).append(" ");
            }

        }
        return stringBuilder.toString();

    }


}
