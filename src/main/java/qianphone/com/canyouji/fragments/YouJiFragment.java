package qianphone.com.canyouji.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import qianphone.com.canyouji.R;
import qianphone.com.canyouji.adapter.TravelListAdapter;
import qianphone.com.canyouji.bean.YouJi;
import qianphone.com.canyouji.net.NetControl;
import qianphone.com.canyouji.views.AdvertisementAdapter;
import qianphone.com.canyouji.views.AdvertismentContainer;

/**
 * Created by Admin on 2016/12/5.
 */

public class YouJiFragment extends Fragment implements AdapterView.OnItemClickListener, NetControl.OnYouJiCallBackListener {
    private View mRootView;
    private ListView listView;
    private TravelListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.youji_fragment_layout, container, false);

        AdvertismentContainer advertismentContainer = new AdvertismentContainer(getActivity());
        advertismentContainer.setAdapter(new MyAdvertiseMentAdapter());


        ListView listView = (ListView) mRootView.findViewById(R.id.listView);
        listView.addHeaderView(advertismentContainer);

        adapter = new TravelListAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        NetControl netControl = new NetControl();
        netControl.getYouJiList(this, 1);
        return mRootView;
    }

    @Override
    public void onNetCallback(final ArrayList<YouJi> books) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addData(books);
            }
        });
    }

    private class MyAdvertiseMentAdapter extends AdvertisementAdapter {

        @Override
        public int getRealCount() {
            System.out.println("getRealCount");
            return 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(R.drawable.test);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
