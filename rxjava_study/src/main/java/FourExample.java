import common.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;

import java.util.concurrent.TimeUnit;

public class FourExample {

    public static void main(String[] args) {

        String[] balls = {"1", "3", "5"};

        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                .map(notUsed -> ball + "<>")
                .take(2));

        source.subscribe(System.out::println);

        String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};
        Observable<GroupedObservable<String, String>> groupedSource =
                Observable.fromArray(objs).groupBy(CommonUtils::getShape);

        groupedSource.subscribe(obj-> {
            obj.subscribe(
                    val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val)
            );
        });
    }
}
