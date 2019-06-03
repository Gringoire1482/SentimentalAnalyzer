package analyze;

import train.DataLoader;
import train.SentimentalType;
import train.Train;
import train.Tweet;

import java.util.Set;

/**
 * Created by user on 03.06.2019.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        DataLoader dataLoader = new DataLoader();
        Set<Tweet> dataset = dataLoader.create();
        //dataset.forEach(Train::preprocess);
        dataset.forEach(a->a.setContent(a.getContent().replaceAll("(;\")|(\";)","")));
        int beurFuckUp=0;
        int mutliFuckUp=0;
        System.out.println("FULL" + dataset.size());
        int i=0;
        for (Tweet tweet:dataset){
            Thread.currentThread().sleep(1000);
            Verdict verdict = new Order(tweet.getContent()).getVerdict();
             if(verdict.getBeurnoulliVerdict()!= SentimentalType.POSITIVE)beurFuckUp++;
             if(verdict.getMulltinominalVerdict()!=SentimentalType.POSITIVE)mutliFuckUp++;
             i++;
        }
        System.out.println(beurFuckUp);
        System.out.println(mutliFuckUp);
    }
}
