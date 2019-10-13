import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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
        CommonUtils.sleep(1000);
    }
}
