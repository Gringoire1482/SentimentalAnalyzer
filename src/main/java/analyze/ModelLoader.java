package analyze;

import train.Token;
import train.Tweet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

public class ModelLoader {
    private static File beurnoulliLaplas = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModelLp.txt");
    private static File multinominalLaplas = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModelLp.txt");
    private static File beurnoulliLids = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\BernoulliModel.txt");
    private static File multinominalLids = new File(new File("").getAbsoluteFile() + "\\src\\main\\resources\\static\\trained\\MultinominalModel.txt");
    private static double negativeApriori;
    private static double positiveApriori;
    private static double nullNegativeBeurnoulliTokenLaplas;
    private static double nullPositiveBeurnoulliTokenLaplas;
    private static double nullNegativeMultinominalTokenLaplas;
    private static double nullPositiveMultinominalTokenLaplas;
    private static double nullNegativeBeurnoulliTokenLids;
    private static double nullPositiveBeurnoulliTokenLids;
    private static double nullNegativeMultinominalTokenLids;
    private static double nullPositiveMultinominalTokenLids;

    public static Set<Token> loadLaplas() {
        Set<Token> result = new LinkedHashSet<>();
        Scanner scBeur = null;
        Scanner scMulti = null;
        try {
            scBeur = new Scanner(beurnoulliLaplas, "utf8");
            scMulti = new Scanner(multinominalLaplas, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // LOGGER.log(Level.WARNING,e.toString());
        }
        String[] paramsB = scBeur.nextLine().split(",");
        String[] paramsM = scMulti.nextLine().split(",");
        negativeApriori = Double.valueOf(paramsB[0]);
        positiveApriori = Double.valueOf(paramsB[1]);
        nullNegativeBeurnoulliTokenLaplas = Double.valueOf(paramsB[2]);
        nullPositiveBeurnoulliTokenLaplas = Double.valueOf(paramsB[3]);
        nullNegativeMultinominalTokenLaplas = Double.valueOf(paramsM[2]);
        nullPositiveMultinominalTokenLaplas = Double.valueOf(paramsB[3]);
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

    public static Set<Token> loadLids() {
        Set<Token> result = new LinkedHashSet<>();
        Scanner scBeur = null;
        Scanner scMulti = null;
        try {
            scBeur = new Scanner(beurnoulliLids, "utf8");
            scMulti = new Scanner(multinominalLids, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // LOGGER.log(Level.WARNING,e.toString());
        }
        String[] paramsB = scBeur.nextLine().split(",");
        String[] paramsM = scMulti.nextLine().split(",");
        //negativeApriori = Double.valueOf(paramsB[0]);
        //positiveApriori = Double.valueOf(paramsB[1]);
        nullNegativeBeurnoulliTokenLids = Double.valueOf(paramsB[2]);
        nullPositiveBeurnoulliTokenLids = Double.valueOf(paramsB[3]);
        nullNegativeMultinominalTokenLids = Double.valueOf(paramsM[2]);
        nullPositiveMultinominalTokenLids = Double.valueOf(paramsB[3]);
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
