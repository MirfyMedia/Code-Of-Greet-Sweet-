package com.greetsweet.plant.greetapp.Fragments.More;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class LoadHtmlActivity extends AppCompatActivity {
    String myHtmlString,orderid;
    private OkHttpClient client;
    WebSocket ws = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_html);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        WebView mWebView = (WebView) findViewById(R.id.myWebView);
        PhotoApp.getInstance().createLog("myHtmlString");
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            myHtmlString= null;
        } else {
            orderid= extras.getString(Cons.orderid);
            myHtmlString= extras.getString("myHtmlString");

            PhotoApp.getInstance().createLog("myHtmlString"+myHtmlString);
        }
        try {
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadDataWithBaseURL("", myHtmlString, "text/html", "UTF-8", "");
            start();
            //            OkHttpClient client = new OkHttpClient.Builder().build();
//            String url="ws://13.232.178.62/api/v1/payment-"+orderid+"/";
//            String httpUrl = url.replaceFirst("^http:", "ws:");
//            PhotoApp.getInstance().createLog("urk"+httpUrl+"url"+url);
//            Request request = new Request.Builder().url(httpUrl).build();
//            EchoWebSocketListener listener = new EchoWebSocketListener();
//            WebSocket ws = client.newWebSocket(request, listener);
//            ws.send("hi");
//
//            client.dispatcher().executorService().shutdown();
        }
        catch (Exception e)
        {
            PhotoApp.getInstance().createLog("exception"+e.toString());
        }
    }
    private void start() {
        PhotoApp.getInstance().createLog("url"+"ws://13.232.178.62/api/v1/payment-"+orderid+"/");

        Request request = new Request.Builder().url("ws://13.232.178.62/api/v1/payment-"+orderid+"/").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
//        listener.onOpen("hii");
        OkHttpClient client = new OkHttpClient();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }
    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PhotoApp.getInstance().createLog("logout error"+txt);
                Toast.makeText(getApplicationContext(),"Output"+ txt,Toast.LENGTH_LONG).show();
            }
        });
    }
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello, it's SSaurel !");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving : " + text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }


    private WebSocketClient mWebSocketClient;
//    private void start() {
//        client = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
//                return chain.proceed(request);
//            }
//        }).build();
//
//        Request request = new Request.Builder().url("ws://13.232.178.62/api/v1/payment-"+orderid+"/").build();
//
//        EchoWebSocketListener listener = new EchoWebSocketListener();
//        WebSocket ws = client.newWebSocket(request, listener);
//
//        client.dispatcher().executorService().shutdown();
//    }
//    private void output(final String txt) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                PhotoApp.getInstance().createLog(txt);
//                PhotoApp.getInstance().showToast(txt);
//
//            }
//        });
//    }

    public void connect_to_server() throws NoSuchAlgorithmException {
        URI uri;
        try {
            uri = new URI("ws://13.232.178.62/api/v1/payment-"+orderid+"/");
        } catch (URISyntaxException e) {
            PhotoApp.getInstance().createLog("URISyntaxException"+e.toString());

            e.printStackTrace();
            return;
        }

        Map<String, String> headers = new HashMap<>();
        SSLContext sslContext = SSLContext.getInstance("SSL");

// set up a TrustManager that trusts everything
        try {
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    System.out.println("getAcceptedIssuers =============");
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                                               String authType) {
                    System.out.println("checkClientTrusted =============");
                }

                public void checkServerTrusted(X509Certificate[] certs,
                                               String authType) {
                    System.out.println("checkServerTrusted =============");
                }
            } }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                PhotoApp.getInstance().createLog("Websocket"+ "Opened");

                mWebSocketClient.send("Hello from websocket client");
            }

            @Override
            public void onMessage(String s) {
                Log.i("Websocket", "onMessage"+s);
                PhotoApp.getInstance().createLog("Websocket"+"onMessage"+s);
            }

            @Override
            public void onClose(int code, String s, boolean b) {
                PhotoApp.getInstance().createLog("Websocket+"+ code+ "+Closed " + s);

                Log.i("Websocket", code + ": Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                PhotoApp.getInstance().createLog("Websocket"+"Error " + e.getMessage());

                Log.i("Websocket", "Error " + e.getMessage());
            }
        };

        mWebSocketClient.connect();
    }
}
