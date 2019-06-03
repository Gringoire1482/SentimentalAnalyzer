package analyze;

import train.DataLoader;
import train.TexterraCient;
import train.Token;

import java.util.Arrays;
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
        tokenizedMessage= postprocess(tokenizedMessage);
        tokenizedMessage = tokenizedMessage.stream().filter(a -> a.trim().length() > 2 && !a.contains(" ")).map(a->a.replaceAll("(?i)([а-яА-ЯёЁ])\\1{1,}","$1")).collect(Collectors.toCollection(LinkedList::new));
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
     message= message.replaceAll("\\(+","bbadsmilee");
     message=message.replaceAll("\\)+","ggoodsmilee");
      message=message.replaceAll("(х[аи]|[аи]х){2,}","llaughh");

        StringBuilder stringBuilder = new StringBuilder();
        for (String stringContent : array) {
            System.out.println(stringContent+"--check"+Boolean.valueOf(stringContent.length() > 2) + Boolean.valueOf((stringContent.equals("не")))+Boolean.valueOf(!stopWords.contains(stringContent)));
            if ((stringContent.length() > 2 || stringContent.equals("не")) && !stopWords.contains(stringContent)) {
                stringBuilder.append(stringContent).append(" ");
                System.out.println(stringContent);
            }
        }

        return stringBuilder.toString();
    }

    private static List<String> postprocess(List<String> tokenizedMessage) {
        List<String> result= new LinkedList<>();
      String [] tokens = tokenizedMessage.toArray(new String[0]);
      StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i!=tokens.length-1&&(tokens[i].equals("не") || tokens[i].equals("очень")) && tokens[i + 1].length() > 3) {
                stringBuilder.append(tokens[i]).append("_").append(tokens[++i]).append(" ");
            } else {
                stringBuilder.append(tokens[i]).append(" ");
            }

        }
      result= Arrays.asList(stringBuilder.toString().split(" "));
        return result;
    }
    public static void main(String[] args) {
//        for (Token token : ModelLoader.loadLids()) {
//            System.out.println(token);
//        }
        DataLoader dataLoader = new DataLoader();
        stopWords = dataLoader.loadStopWordsList();
        String message = "Для; вычисления значений столбцов  очень допускается также использование  не подзапросов. Например, требуется укомплектовать все портативные компьютеры самыми быстрыми процессорами из имеющихся в наличии.";
         String sad ="Вечно мне белять не везёт     то моник то с учёбы отпустили  зря сука ехал  ";
         message = preprocess(message);
        System.out.println(message);
        List<String> tokenizedMessage = TexterraCient.lemmatizeOrderMessageString(message);
        tokenizedMessage.forEach(System.out::println);
        System.out.println("-----------");
        tokenizedMessage= postprocess(tokenizedMessage);
        tokenizedMessage = tokenizedMessage.stream().filter(a -> a.trim().length() > 2 && !a.contains(" ")).collect(Collectors.toCollection(LinkedList::new));
        tokenizedMessage.forEach(System.out::println);
        System.out.println(evaluate(
                        "я микрофраза :( учимся срать кирпичами в режиме &amp;quot;нон-стоп&amp;quot; ","lapl" ));  }
}
