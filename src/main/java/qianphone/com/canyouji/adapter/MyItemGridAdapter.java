package qianphone.com.canyouji.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qianphone.com.canyouji.R;
import qianphone.com.canyouji.net.NetControl;
import qianphone.com.qftravel.bean.Destination;

/**
 * Created by Admin on 2016/12/6.
 */

public class MyItemGridAdapter extends BaseAdapter implements NetControl.OnBitmapCallbackListener {

    private ArrayList<Destination> data = new ArrayList<>();
    private NetControl netControl;


    private Handler handler;

    public MyItemGridAdapter(ArrayList<Destination> data) {
        handler = new Handler();
        this.data = data;
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

        netControl = new NetControl();
    }

    private LruCache<String, Bitmap> bitmapCache;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Destination getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.destination_grid_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.coverImg = (ImageView) convertView.findViewById(R.id.destination_cover_img);
            viewHolder.desCountTv = (TextView) convertView.findViewById(R.id.destination_count_tv);
            viewHolder.desNameTv = (TextView) convertView.findViewById(R.id.destination_name_zh_tv);
            viewHolder.desEnNameTv = (TextView) convertView.findViewById(R.id.destination_name_en_tv);

            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        Destination destination = data.get(position);
        holder.desCountTv.setText(destination.getPoi_count() + " 旅行地");
        holder.desNameTv.setText(destination.getName_zh_cn());
        holder.desEnNameTv.setText(destination.getName_en());

        String imgUrl = destination.getImage_url();
        holder.coverImg.setTag(imgUrl);
        Bitmap bitmap = bitmapCache.get(imgUrl);
        if (bitmap == null) {
            holder.coverImg.setImageResource(R.drawable.default_photo);
            netControl.getImageFromNet(holder.coverImg, imgUrl, this, -1, -1);
        } else {
            holder.coverImg.setImageBitmap(bitmap);
        }

        return convertView;
    }

    @Override
    public void onBitmapCallback(final String url, final Bitmap bitmap, final ImageView coverView) {
        bitmapCache.put(url, bitmap);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (coverView.getTag().equals(url)) {
                    coverView.setImageBitmap(bitmap);
                }
            }
        });
    }

    class ViewHolder {
        ImageView coverImg;
        TextView desNameTv,desEnNameTv,desCountTv;
    }
}
