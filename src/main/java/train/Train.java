package train;

import java.util.Set;

public class Train {
    private static  Set<String> stopWords;
    
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        Set<Tweet> data = dataLoader.create();
        data.forEach(a->preprocess(a.getContent()));
    }

    private static void preprocess(String content ){
        String [] array = content. split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for(String stringContent :array){
            if (stringContent.length()>2) stringBuilder.append(stringContent).append(" ");
        }



    }

    private  static  loadStopWordsList(){}
}
