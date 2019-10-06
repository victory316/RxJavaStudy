import common.CommonUtils;
import common.Shape;
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

        groupedSource.subscribe(obj -> {
            obj.filter(val -> obj.getKey().equals("BALL"))
                    .subscribe( val -> System.out.println("AFTER FLITERING : " + obj.getKey() + "\t Value:" + val));
        });

        Observable<String> sourceForScan = Observable.fromArray(balls)
                .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");

        sourceForScan.subscribe(System.out::println);

        String[] shapes = {"BALL", "PENTAGON", "STAR"};
        String[] coloredTriangles = {"2-T", "6-T", "4-T"};

        Observable<String> sourceForZip = Observable.zip(
                Observable.fromArray(shapes).map(Shape::getSuffix),
                Observable.fromArray(coloredTriangles).map(Shape::getColor),
                (suffix, color) -> color + suffix);

        sourceForZip.subscribe(System.out::println);

        Observable sourceByInteger = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                Observable.just(1,2,3),
                (a, b, c) -> a + b + c);

        sourceByInteger.subscribe(System.out::println);
    }
}
