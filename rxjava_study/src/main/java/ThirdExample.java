import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ThirdExample {
    /**
     * 190915
     *
     * fromIterable 함수 사용을 테스트해보는 클래스로
     * fromIterable은 Iterator 인터페이스를 구현한 클래스인 경우 적용해 사용이 가능
     * Iterator 인터페이스에는 boolean hasNext(), E next() 가 포함됨.
     *
     **/

    public static void main(String[] args) {
        Function<String, Integer> ballToIndex = ball -> {
            switch(ball) {
                case "RED": return 1;
                case "YELLOW": return 2;
                case "GREEN": return 3;
                case "BLUE": return 5;
                default: return -1;
            }
        };

        String[] balls = {"1", "2", "3", "5"};
        String[] colors = {"RED", "BLUE", "BLUE"};
        Observable<String> source = Observable.fromArray(balls)
                .map(ball -> ball + "<>");
        Observable<Integer> source2 = Observable.fromArray(colors)
                .map(ballToIndex);

        source.subscribe(output -> System.out.println(output));
        source2.subscribe(output -> System.out.println(output));

        // map과의 차이는 flatMap의 경우에는 observable을 반환하기 때문에 데이터를 여러번 뺄 수 있음. (from source)
        Observable<String> flatMapSource = Observable.fromArray(colors)
                .flatMap(color -> Observable.just(color + " 색깔 " + color + " 색깔"));

        flatMapSource.subscribe(output -> System.out.println(output));

        // 어디 한번 해봅시다. 구구단 만들기.
        // 이건 기존 자바코드.

        Scanner in = new Scanner(System.in);
        System.out.println("gugudan output");
        int dan = Integer.parseInt(in.nextLine());

        for (int row = 1; row <= 9; ++row) {
            System.out.println(dan + " * " + row + " = " + dan * row);
        }

        
    }

    public static String getDiamond(String ball) {
        return ball + "<>";
    }
}
