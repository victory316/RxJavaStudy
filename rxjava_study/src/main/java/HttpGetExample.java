import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;

import java.io.IOException;

import static common.CommonUtils.GITHUB_ROOT;

public class HttpGetExample {

    private static final String FIRST_URL = "https://api.github.com/zen";
    private static final String SECOND_URL = GITHUB_ROOT + "/samples/callback_hell";

    private final OkHttpClient client = new OkHttpClient();

    private Callback onSuccess = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(response.body().string());
        }
    };

    public void run() {
        Request request = new Request.Builder()
                .url(FIRST_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Request request = new Request.Builder()
                        .url(SECOND_URL)
                        .build();

                client.newCall(request).enqueue(onSuccess);
            }
        });
    }

    public static void main(String[] args) {
//        HttpGetExample demo = new HttpGetExample();
//        demo.run();
//
        Observable<String> first = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);
        Observable<String> second = Observable.just(SECOND_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable.zip(first, second, (a,b) -> ("\n>> " + a + " \n>> " + b ))
                .subscribe(Log::it);

        CommonUtils.sleep(5000);
    }
}
