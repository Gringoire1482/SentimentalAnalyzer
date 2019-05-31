package analyze;

import train.DataLoader;
import train.TexterraCient;
import train.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.log;

public class Analyzer {
    private static final String REGEX = "(@\\w+)|((http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)|(&\\w+;)|(;+)|(quot;)|(:+)|(#+)|(\\.+)|(RT)|(\\|+)|(\\=+)|(\\d+)|(\\++)|(\\!+)|(\\?+)|(\\,+)|(\\\\+)|(\\/+)|(\\[+)|(\\]+)|(\\*+)|(~+)|(`+)|(@+)|(%+)|(&+)|(_+)|(-+)|(\\^+)|(\\^+)|(\\*+)";
    private static Set<Token> model;
    private static Set<String> stopWords;

    private static double beurnoulliNegativeScore;
    private static double beurnoulliPositiveScore;
    private static double multinominalNegativeScore;
    private static double multinominalPositiveScore;

    public static Verdict evaluate(String message, String smooth) {
        if (model == null || stopWords == null) {
            model = ModelLoader.loadModel(smooth);

            DataLoader dataLoader = new DataLoader();
            stopWords = dataLoader.loadStopWordsList();
        }
        message = preprocess(message);
        List<String> tokenizedMessage = TexterraCient.lemmatizeOrderMessageString(message);
        tokenizedMessage = tokenizedMessage.stream().filter(a -> a.trim().length() > 2 && !a.contains(" ")).collect(Collectors.toCollection(LinkedList::new));
        beurnoulliNegativeScore = log(ModelLoader.getNegativeApriori());
        beurnoulliPositiveScore = log(ModelLoader.getPositiveApriori());
        multinominalNegativeScore = log(ModelLoader.getNegativeApriori());
        multinominalPositiveScore = log(ModelLoader.getPositiveApriori());
        for (String word : tokenizedMessage) {
            Token token = model.stream().filter(a -> a.getContent().equals(word)).findAny().orElse(null);
            if (token == null) {
                System.out.println(word + "null");
                beurnoulliNegativeScore += log(ModelLoader.getNullNegativeBeurnoulliToken());
                beurnoulliPositiveScore += log(ModelLoader.getNullPositiveBeurnoulliToken());
                multinominalNegativeScore += log(ModelLoader.getNullNegativeMultinominalToken());
                multinominalPositiveScore += log(ModelLoader.getNullPositiveMultinominalToken());
            } else {
                System.out.println(token);
                beurnoulliNegativeScore += log(token.getBernoulliAposterioriNegative());
                beurnoulliPositiveScore += log(token.getBernoulliAposterioriPositive());
                multinominalNegativeScore += log(token.getMultinominalAposterioriNegative());
                multinominalPositiveScore += log(token.getMultinominalAposterioriPositive());
            }
        }

        return new Verdict(beurnoulliNegativeScore,beurnoulliPositiveScore,multinominalNegativeScore,multinominalPositiveScore);
    }

    private static String preprocess(String message) {
        message = message.replaceAll(REGEX, " ");


        String[] array = message.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringContent : array) {
            if ((stringContent.length() > 2 || stringContent.equals("не")) && !stopWords.contains(stringContent)) {
                stringBuilder.append(stringContent).append(" ");
            }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
//        for (Token token : ModelLoader.loadLids()) {
//            System.out.println(token);
//        }
        DataLoader dataLoader = new DataLoader();
        stopWords = dataLoader.loadStopWordsList();
        String message = "Для; вычисления значений столбцов допускается также использование подзапросов. Например, требуется укомплектовать все портативные компьютеры самыми быстрыми процессорами из имеющихся в наличии.";
         String sad ="Вечно мне белять не везёт     то моник то с учёбы отпустили  зря сука ехал  ";
         message = preprocess(message);
        System.out.println(message);
        List<String> tokenizedMessage = TexterraCient.lemmatizeOrderMessageString(message);
        tokenizedMessage = tokenizedMessage.stream().filter(a -> a.trim().length() > 2 && !a.contains(" ")).collect(Collectors.toCollection(LinkedList::new));
        tokenizedMessage.forEach(System.out::println);
        System.out.println(evaluate(
                "Как все надоело господи сколько можно уже bbadsmilee","lapl"));
    }
}
