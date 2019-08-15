import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class FirstExample {
    public void emit() {
        Observable.just("Hello", "RxJava 2!!")
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {

        Observable<Integer> source = Observable.create(
                (ObservableEmitter<Integer> emitter) -> {
                    emitter.onNext(100);
                    emitter.onNext(200);
                    emitter.onNext(300);
                    emitter.onComplete();
                }
        );

        Integer[] arr = {100, 200, 300};
        Observable<Integer> source_2 = Observable.fromArray(arr);

        source.subscribe(System.out::println);
        source.subscribe(data -> System.out.println("Result : " + data));
        source_2.subscribe(System.out::println);
    }
}
