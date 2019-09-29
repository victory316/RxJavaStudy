import io.reactivex.Observable;
import io.reactivex.functions.Function;
import sun.rmi.runtime.Log;

import java.util.Scanner;

public class GugudanExample {
    /**
     * 190929
     *
     * RxJava로 구구단 짜보기
     *
     **/

    public static void main(String[] args) {

        // 어디 한번 해봅시다. 구구단 만들기.
        // 이건 기존 자바코드.

        Scanner in = new Scanner(System.in);
        System.out.println("gugudan output");
        int dan = Integer.parseInt(in.nextLine());

        for (int row = 1; row <= 9; ++row) {
            System.out.println(dan + " * " + row + " = " + dan * row);
        }

        Scanner in2 = new Scanner(System.in);
        System.out.println("gugudan by rxJava");
        int dan_2 = Integer.parseInt(in.nextLine());

        Integer[] numbers = {1,2,3,4,5,6,7,8,9};

        Observable<Integer> source = Observable.fromArray(numbers)
                .flatMap(number -> Observable.just(number * dan_2));

        // Observable<Integer> source = Observable.range(1,9); 로 대체 가능

        source.subscribe(System.out::println);
    }
}
