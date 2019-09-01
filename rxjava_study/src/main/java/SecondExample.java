import common.Order;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.ReplaySubject;
import jdk.nashorn.internal.ir.Block;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class SecondExample {
    /**
     * 190819
     *
     * fromIterable 함수 사용을 테스트해보는 클래스로
     * fromIterable은 Iterator 인터페이스를 구현한 클래스인 경우 적용해 사용이 가능
     * Iterator 인터페이스에는 boolean hasNext(), E next() 가 포함됨.
     *
     **/

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Jerry");
        names.add("Tom");
        names.add("BOB");

        Observable<String> source = Observable.fromIterable(names);
        source.subscribe(System.out::println);

        Set<String> cities = new HashSet<>();
        cities.add("Seoul");
        cities.add("London");
        cities.add("Paris");

        Observable<String> citiesSource = Observable.fromIterable(cities);
        citiesSource.subscribe(System.out::println);

        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(100);

        orderQueue.add(new Order("ORD-1"));
        orderQueue.add(new Order("ORD-2"));
        orderQueue.add(new Order("ORD-3"));

        Observable<Order> orderSource = Observable.fromIterable(orderQueue);
        orderSource.subscribe(order -> System.out.println(order.getId()));

        // Callable 테스트
        // Callable : Runhnable 인터페이스터럼 메서드가 하나이지만 return값을 준다는 차이점이 있음.

        // Callable은 인자가 없는 관계로 람다 관계식을 활용. 간단하게 표기함.
        Callable<String> callable = () -> {
            sleep(1000);
            return "Helllo Callable";
        };

        Observable<String> callableSoruce  = Observable.fromCallable(callable);
        callableSoruce.subscribe(System.out::println);

        // Future는 비등기 결과값을 활용할 때 사용함.
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            sleep(1000);
            return "Hello Future";
        });

        Observable<String> futureSource = Observable.fromFuture(future);
        futureSource.subscribe(System.out::println);

        Publisher<String> publisher = (Subscriber<? super String> s) -> {
            s.onNext("Hello Observable.fromPublisher()");
            s.onComplete();
        };

        Observable<String> publisherSource = Observable.fromPublisher(publisher);
        publisherSource.subscribe(System.out::println);

        // ---- Single 클래스 테스트 ---------
        // Single 클래스는 오직 1개의 데이터만 발행하도록 한정함.(Observable은 무한)
        // 결과가 유일한 서버 API 호출시 유용하게 활용가능.
        // 데이터 하나 발행함과 동시에 onSuccess 호출됨.

        // !! single을 여러 인자를 가진 함수로부터 처리없이 가져오면 에러 발생 !!

        Observable<String> singleSource = Observable.just("Hello Single");
        Single.fromObservable(singleSource)
                .subscribe(System.out::println);

        Observable.just("HelloSingle")
                .single("default Item")
                .subscribe(System.out::println);

        String[] colors = {"Red", "Blue", "Gold"};
        Observable.fromArray(colors)
                .first("default value") // first호출시 Single 객체로 변환됨.
                .subscribe(System.out::println);

        Observable.empty()
                .single("defalut value")
                .subscribe(System.out::println);

        Observable.just(new Order("ORD-1"), new Order("ORD-2"))
                .take(1)    // take()를 통해 Single 객체를 생성함.
                .single(new Order("default order"))
                .subscribe(System.out::println);


        // 2.5 Subject Classes

        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(data -> System.out.println("Subscriber #1 => " + data ));
        asyncSubject.onNext("1");
        asyncSubject.onNext("3");
        asyncSubject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        asyncSubject.onNext("5");
        asyncSubject.onComplete();

        Float[] temperature = {10.1f, 13.4f, 12.5f};
        Observable<Float> temperatureSource = Observable.fromArray(temperature);

        AsyncSubject<Float> asyncTemperatureSubject = AsyncSubject.create();
        asyncTemperatureSubject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        temperatureSource.subscribe(asyncTemperatureSubject);   // AsyncSubject가 Observer를 상속함과 동시에 Observer 인터페이스를 구현하기 때문.

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("6");
        behaviorSubject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        behaviorSubject.onNext("1");
        behaviorSubject.onNext("3");
        behaviorSubject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        behaviorSubject.onNext("5");
        behaviorSubject.onComplete();

        ReplaySubject<String> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(data -> System.out.println("Subscribner #1 => " + data));
        replaySubject.onNext("1");
        replaySubject.onNext("3");
        replaySubject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        replaySubject.onNext("5");
        replaySubject.onComplete();

        String[] dt = {"1", "3", "5"};
        Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);
        ConnectableObservable<String> connectableObservableSoruce = balls.publish();
        connectableObservableSoruce.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        connectableObservableSoruce.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        connectableObservableSoruce.connect();

        try {
            sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectableObservableSoruce.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
