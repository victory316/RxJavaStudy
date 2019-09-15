import io.reactivex.Observable;
import io.reactivex.functions.Function;

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

    }

    public static String getDiamond(String ball) {
        return ball + "<>";
    }
}
