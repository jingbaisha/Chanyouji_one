package qianphone.com.canyouji.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import qianphone.com.canyouji.R;
import qianphone.com.canyouji.bean.YouJi;
import qianphone.com.canyouji.net.NetControl;


/**
 * Created by Admin on 2016/11/9.
 */

public class TravelListAdapter extends BaseAdapter implements NetControl.OnBitmapCallbackListener {

    private NetControl mControl;

    public TravelListAdapter() {
        mControl = new NetControl();

        //获取当前可以使用的内存大小

        int size = (int) (Runtime.getRuntime().maxMemory() / 8);

        bitmapCache = new LruCache<String, Bitmap>(size) {
            /**
             * 可以返回个数，和占内存的大小
             * @param key
             * @param value
             * @return
             */
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    private ArrayList<YouJi> data = new ArrayList<YouJi>();


    private LruCache<String, Bitmap> bitmapCache;
    //用于存放图片的容器
//    private HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();

    /**
     * 向适配器中添加数据的方法
     *
     * @param books
     */

    public void addData(ArrayList<YouJi> books) {
        data.addAll(books);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public YouJi getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Activity context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            context = (Activity) parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.travel_list_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.coverView = (ImageView) convertView.findViewById(R.id.cover_img);
            holder.nameView = (TextView) convertView.findViewById(R.id.user_name_tv);
            holder.titleView = (TextView) convertView.findViewById(R.id.title_tv);
            holder.routeView = (TextView) convertView.findViewById(R.id.route_line_tv);
            holder.user_head_img = (ImageView) convertView.findViewById(R.id.user_head_img);

            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        YouJi youJi = data.get(position);
        holder.nameView.setText(youJi.getUser().getUname());
        holder.titleView.setText(youJi.getName());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(youJi.getStart_date()).append(" / ").append(youJi.getDays()).append("天,").append(youJi.getPhotos_count()).append("图");
        holder.routeView.setText(stringBuilder.toString());

        //设置封面
        setCoverImage(holder, youJi, parent);

        setHeadImage(holder, youJi);
        return convertView;
    }

    private void setHeadImage(ViewHolder holder, YouJi youJi) {

        YouJi.User user = youJi.getUser();
        String headUrl = user.getHead_url();
        Bitmap bitmap = bitmapCache.get(headUrl);
        if (bitmap == null) {
            holder.user_head_img.setImageResource(R.mipmap.head_default);
            holder.user_head_img.setTag(headUrl);
            mControl.getImageFromNet(holder.user_head_img, headUrl, this, -1, -1);
        } else {
            holder.user_head_img.setImageBitmap(bitmap);
        }
    }

    private void setCoverImage(ViewHolder holder, YouJi youJi, ViewGroup parent) {
        String coverUrl = youJi.getFront_cover_photo_url();
        //通过图片的URL到图片缓存容器中获取图片
        Bitmap bitmap = bitmapCache.get(coverUrl);
        if (bitmap == null) {
            //从网络中获取这张图片，并且设置默认图片
            holder.coverView.setImageResource(R.drawable.default_photo);
            //用当前数据的图片的地址作为ImageView的标识
            holder.coverView.setTag(coverUrl);
            mControl.getImageFromNet(holder.coverView, coverUrl, this, parent.getWidth(), (int) (parent.getWidth() * 0.618f));
//            System.out.println("内存中没有第" + position + "张图,从网络中获取");
        } else {
            holder.coverView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBitmapCallback(final String url, final Bitmap bitmap, final ImageView coverView) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bitmap == null) {
                    Toast.makeText(context, "图片请求异常！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //把网络请求回来的图片放到缓存集合中
                bitmapCache.put(url, bitmap);
                if (url.equals((String) coverView.getTag())) {//判断取回图片的Url跟View上的Url是否一致，一致则说明View没有被复用过
                    //把请求得到的图片设置给Imageview
                    coverView.setImageBitmap(bitmap);
                }
//                notifyDataSetChanged();
            }
        });

    }

    class ViewHolder {
        TextView titleView, routeView, nameView;
        ImageView coverView, user_head_img;
    }

}
