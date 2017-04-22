package space.conj.chin;

import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class TaskActivity extends AppCompatActivity {

    private String[] task = {"【爬虫】京东显卡数据", "ip池维护", "【爬虫】京东硬盘数据", "【爬虫】京东手机数据",
            "【爬虫】京东主板数据", "【爬虫】京东显示器数据", "【爬虫】京东相机数据", "【爬虫】京东电视数据", "【爬虫】京东空调数据",
            "【爬虫】京东洗衣机数据", "【爬虫】京东冰箱数据", "【爬虫】京东笔记本数据", "【爬虫】京东平板数据",
            "【爬虫】京东路由器数据", "【数据仓库source】京东数据sqoop同步"};
    private TextView httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TaskActivity.this,
                R.layout.support_simple_spinner_dropdown_item, task);
        ListView tasks = (ListView) findViewById(R.id.list_task);
        httpResponse = (TextView) findViewById(R.id.http_response);

        OkHttpClient client = new OkHttpClient().setCookieHandler(new CookieManager());

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("user_name", "xxxxxxx");
        builder.add("password", "xxxxxxxxxxx");
        Request request = new Request.Builder()
                .url("http://chin.nazgrim.com/login")
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("COOKIE", response.header("Set-Cookie"));
            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        request = new Request.Builder()
                .url("http://chin.nazgrim.com/get_log_by_page?order=asc&offset=10&limit=10")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("COOKIE2", response.header("Set-Cookie"));
                Log.i("HTML", response.body().string());
            }
        });

        assert tasks != null;
        tasks.setAdapter(adapter);
    }


}