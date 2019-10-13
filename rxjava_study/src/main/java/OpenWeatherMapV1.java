import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenWeatherMapV1 {

    private static final String URL =
            "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";

    private static final String API_KEY = "58806017f0a4b3f5ada86426dd9ed288";


    public void run() {
//        Observable<String> source = Observable.just(URL + API_KEY)
//                .map(OkHttpHelper::getWithLog)
//                .subscribeOn(Schedulers.io());
//
//        Observable<String> temperature = source.map(this::parseTemperature);
//        Observable<String> city = source.map(this::parseCityName);
//        Observable<String> country = source.map(this::parseCountry);
//        CommonUtils.exampleStart();
//
//        Observable.concat(temperature, city, country)
//                .observeOn(Schedulers.newThread())
//                .subscribe(Log::it);
//
//        CommonUtils.sleep(1000);

        // REST API를 한번만 호출하게 개선하면

        Observable<String> source2 = Observable.just(URL + API_KEY)
                .map(OkHttpHelper::getWithLog)
                .subscribeOn(Schedulers.io())
                .share()
                .observeOn(Schedulers.newThread());

        source2.map(this::parseTemperature).subscribe(Log::it);
        source2.map(this::parseCityName).subscribe(Log::it);
        source2.map(this::parseCountry).subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private String parseTemperature(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }

    private String parseCityName(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*");
    }

    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }

    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        if (match.find()) {
            return match.group();
        }

        return "N/A";
    }

    public static void main(String[] args) {
        OpenWeatherMapV1 demo = new OpenWeatherMapV1();
        demo.run();
    }
}
