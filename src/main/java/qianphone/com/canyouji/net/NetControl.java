package qianphone.com.canyouji.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import qianphone.com.canyouji.bean.YouJi;
import qianphone.com.qftravel.bean.Destination;


/**
 * Created by Admin on 2016/11/9.
 */

public class NetControl {


    private static final int TIME_OUT = 8000;

    public static interface OnYouJiCallBackListener {
        void onNetCallback(ArrayList<YouJi> youJis);
    }

    public static interface OnDestinationsCallBackListener {
        void OnDestinationsCallBack(HashMap<Integer, ArrayList<Destination>> destinations);
    }

    public static interface OnBitmapCallbackListener {
        void onBitmapCallback(String url, Bitmap bitmap, ImageView coverView);
    }

    private ExecutorService exService;

    public NetControl() {
        //初始化一个固定有5条线程的线程池
        exService = Executors.newFixedThreadPool(5);
    }

    private static Context mContext;
    private static File cacheDir;


    public static void init(Context context) {
        mContext = context;
        //获取应用缓存路径
        cacheDir = mContext.getCacheDir();
//        System.out.println("缓存路径：  " + cacheDir.getAbsolutePath());
    }


    /**
     * 从网络中获取图片并且回调
     *
     * @param coverView
     * @param path
     * @param listener  回调的对象
     */
    public final void getImageFromNet(final ImageView coverView, final String path, final OnBitmapCallbackListener listener, final int width, final int height) {
        //用线程池来执行任务
        exService.execute(new Runnable() {
            @Override
            public void run() {
                //从url中截取出图片的名字
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                File file = new File(cacheDir, fileName);
                //看看图片文件是否存在
                if (file.exists()) {//存在则说明本地有此图片，直接从本地读取
                    Bitmap bitmap = deCodeBitmap(file, coverView, width, height);
                    listener.onBitmapCallback(path, bitmap, coverView);
                    System.out.println("本地有" + fileName + "直接从本地读取");
                } else {//本地无该图，联网获取
                    fromNet(path, listener, coverView, width, height);
                    System.out.println("本地没有" + fileName + "从网络获取");
                }
            }
        });


    }

    /**
     * 按照Item的大小对图片进行采样
     *
     * @param file
     * @param coverView
     * @param width
     * @param height    @return
     */
    private Bitmap deCodeBitmap(File file, ImageView coverView, int width, int height) {
        if (width == -1 || height == -1) {//宽和高传-1则不用采样
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        //计算采样率
        int scaleW = options.outWidth / width;
        int scaleH = options.outHeight / height;
        int scale = Math.max(scaleW, scaleH);
        options.inSampleSize = scale;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }

    private void fromNet(String path, OnBitmapCallbackListener listener, ImageView coverView, int width, int height) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIME_OUT);
            conn.connect();

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                //从url中截取出图片的名字
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                File file = new File(cacheDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);

                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                fos.close();
                inputStream.close();

                //把从文件中读取图片
                Bitmap bitmap = deCodeBitmap(file, coverView, width, height);
                //回调结果
                listener.onBitmapCallback(path, bitmap, coverView);
//                //把图片存到本地缓存中 第一个参数，图片格式  第二个参数 存储质量  第三个参数输出流
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //回调结果
            listener.onBitmapCallback(path, null, coverView);
        }
    }

    //游记
    public static final String YOUJI_PATH = "http://chanyouji.com/api/trips/featured.json?page=";
    //目的地
    public static final String DESTINATIONS_PATH = "http://chanyouji.com/api/destinations.json";

    /**
     * 获取目的地的数据
     *
     * @param listener
     */
    public void getDestinations(final OnDestinationsCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String json = getJson(DESTINATIONS_PATH);
                    HashMap<Integer, ArrayList<Destination>> data = new HashMap<>();
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int category = jsonObject.getInt("category");
                        ArrayList<Destination> destinations = new ArrayList<Destination>();
                        JSONArray jArray = jsonObject.getJSONArray("destinations");
                        for (int j = 0; j < jArray.length(); j++) {
                            JSONObject jObject = jArray.getJSONObject(j);
                            String id = jObject.getString("id");
                            String name_zh_cn = jObject.getString("name_zh_cn");
                            String name_en = jObject.getString("name_en");
                            String poi_count = jObject.getString("poi_count");
                            double lat = jObject.getDouble("lat");
                            double lng = jObject.getDouble("lng");
                            String image_url = jObject.getString("image_url");
                            String updated_at = jObject.getString("updated_at");

                            Destination destination = new Destination(category, id, name_zh_cn, name_en, poi_count, lat, lng, image_url, updated_at);

                            destinations.add(destination);
                        }
                        data.put(category, destinations);
                    }

                    listener.OnDestinationsCallBack(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.OnDestinationsCallBack(null);
                }
            }
        }).start();
    }


    /**
     * 获取游记的数据
     *
     * @param listener
     * @param page
     */
    public void getYouJiList(final OnYouJiCallBackListener listener, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = getJson(YOUJI_PATH + page);
                    ArrayList<YouJi> books = parseJson(json);
                    listener.onNetCallback(books);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onNetCallback(null);
                }
            }
        }).start();
    }

    /**
     * 获取Json
     * @param path
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private String getJson(String path) throws IOException, JSONException {
        URL url = new URL(path);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIME_OUT);

        conn.connect();

        int code = conn.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            String json = readStream2String(is);
            System.out.println(json);
            return json;
        }

        return null;
    }

    private ArrayList<YouJi> parseJson(String json) throws JSONException {
        ArrayList<YouJi> youJIs = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String photos_count = jsonObject.getString("photos_count");
            String start_date = jsonObject.getString("start_date");
            String end_date = jsonObject.getString("end_date");
            String days = jsonObject.getString("days");
            String level = jsonObject.getString("level");
            String views_count = jsonObject.getString("views_count");
            String comments_count = jsonObject.getString("comments_count");
            String likes_count = jsonObject.getString("likes_count");
            String source = jsonObject.getString("source");
            String front_cover_photo_url = jsonObject.getString("front_cover_photo_url");
            boolean featured = jsonObject.getBoolean("featured");

            JSONObject userJson = jsonObject.getJSONObject("user");

            String userId = userJson.getString("id");
            String userName = userJson.getString("name");
            String userHeader = userJson.getString("image");

            YouJi.User user = new YouJi.User(userId, userName, userHeader);


            YouJi youJi = new YouJi(id, name, photos_count, start_date, end_date, days, level, views_count, comments_count, likes_count, source, front_cover_photo_url, featured, user);

            youJIs.add(youJi);
        }

        return youJIs;
    }


    public static String readStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        String json = baos.toString("utf-8");
        baos.close();
        return json;
    }
}
