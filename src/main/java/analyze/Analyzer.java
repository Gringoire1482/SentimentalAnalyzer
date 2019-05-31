package analyze;

import train.Token;

import java.util.Set;

public class Analyzer {
    private static final String REGEX = "(@\\w+)|((http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)|(&\\w+;)|(quot;)|(:+)|(#+)|(\\.+)|(RT)|(\\|+)|(\\=+)|(\\d+)|(\\++)|(\\!+)|(\\?+)|(\\,+)|(\\\\+)|(\\/+)|(\\[+)|(\\]+)|(\\*+)|(~+)|(`+)|(@+)|(%+)|(&+)|(_+)|(-+)|(\\^+)|(\\^+)|(\\*+)";
   private static Set<Token> laplasModel;
   private  static Set<Token> lidsModel;
    public static  Verdict evaluate(String message){
        if(laplasModel==null||lidsModel==null){
            laplasModel=ModelLoader.loadLaplas();
            lidsModel=ModelLoader.loadLids();
        }



    }
    private static  String preprocess(String message){
   message=message.replaceAll(REGEX," ");


        String[] array = content.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringContent : array) {
            if ((stringContent.length() > 2 || stringContent.equals("не")) && !stopWords.contains(stringContent)) {
                stringBuilder.append(stringContent).append(" ");
            }
        }
        tweet.setContent(stringBuilder.toString());

    }
}
