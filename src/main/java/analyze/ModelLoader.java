package analyze;

import train.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class ModelLoader {
    private static File beurnoulliLaplas = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModelLp.txt");
    private static File multinominalLaplas = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModelLp.txt");
    private static File beurnoulliLids = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModel.txt");
    private static File multinominalLids = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModel.txt");
    private static double negativeApriori;
    private static double positiveApriori;
    private static double nullNegativeBeurnoulliToken;
    private static double nullPositiveBeurnoulliToken;
    private static double nullNegativeMultinominalToken;
    private static double nullPositiveMultinominalToken;


    public static double getNegativeApriori() {
        return negativeApriori;
    }

    public static double getPositiveApriori() {
        return positiveApriori;
    }

    public static double getNullNegativeBeurnoulliToken() {
        return nullNegativeBeurnoulliToken;
    }

    public static double getNullPositiveBeurnoulliToken() {
        return nullPositiveBeurnoulliToken;
    }

    public static double getNullNegativeMultinominalToken() {
        return nullNegativeMultinominalToken;
    }

    public static double getNullPositiveMultinominalToken() {
        return nullPositiveMultinominalToken;
    }


    public static Set<Token> loadModel(String model) {
        Set<Token> result = new LinkedHashSet<>();
        Scanner scBeur = null;
        Scanner scMulti = null;
        try {
            scBeur = new Scanner(model.equals("lids")?beurnoulliLids:beurnoulliLaplas, "utf8");
            scMulti = new Scanner(model.equals("lids")?multinominalLids:multinominalLaplas, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // LOGGER.log(Level.WARNING,e.toString());
        }
        String[] paramsB = scBeur.nextLine().split(",");
        String[] paramsM = scMulti.nextLine().split(",");
        negativeApriori = Double.valueOf(paramsB[0]);
        positiveApriori = Double.valueOf(paramsB[1]);
        nullNegativeBeurnoulliToken = Double.valueOf(paramsB[2]);
        nullPositiveBeurnoulliToken = Double.valueOf(paramsB[3]);
        nullNegativeMultinominalToken = Double.valueOf(paramsM[2]);
        nullPositiveMultinominalToken = Double.valueOf(paramsB[3]);
        StringBuilder stringBuilder = new StringBuilder();
        assert scBeur != null;
        while (scBeur.hasNextLine() && scMulti.hasNextLine()) {
            String[] tokenInfoBeurnoulli = scBeur.nextLine().split(",");
            String[] tokenInfoMultinominal = scMulti.nextLine().split(",");
            Token token = new Token(tokenInfoBeurnoulli[0]);
            token.setBernoulliAposterioriPositive(Double.valueOf(tokenInfoBeurnoulli[1]));
            token.setBernoulliAposterioriNegative(Double.valueOf(tokenInfoBeurnoulli[2]));
            token.setMultinominalAposterioriPositive(Double.valueOf(tokenInfoMultinominal[1]));
            token.setMultinominalAposterioriNegative(Double.valueOf(tokenInfoMultinominal[2]));
            result.add(token);


            //LOGGER.log(Level.INFO,type.toString()+" READ TWEET " + tweet.toString());
        }
        return result;
    }


}
