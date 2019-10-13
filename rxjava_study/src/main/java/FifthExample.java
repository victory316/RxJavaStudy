import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class FifthExample {

    public static void main(String[] args) {

        Observable.just("Hello", "RxJava 2!!")
                .subscribe(Log::i);

        String[] objs = {"1-S", "2-T", "3-P"};
        Observable<String> source = Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(Shape::flip);

        source.subscribe(Log::i);
        CommonUtils.sleep(500);

        String[] orgs = {"1", "3", "5"};
        Observable<String> source2 = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a,b) -> a);

        // 구독 #1
        source2.map(item -> "<<" + item +">>")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        // 구독 #2
        source2.map(item -> "##" + item + "##")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        // 트램폴린 스케줄러
        source2.map(item -> "by Trampoline : " + item)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(Log::i);


        CommonUtils.sleep(1000);
    }
}
