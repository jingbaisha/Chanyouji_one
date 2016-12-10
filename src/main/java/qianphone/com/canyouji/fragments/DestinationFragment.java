package qianphone.com.canyouji.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import qianphone.com.canyouji.R;
import qianphone.com.canyouji.adapter.MyDestinationAdapter;
import qianphone.com.canyouji.net.NetControl;
import qianphone.com.qftravel.bean.Destination;

/**
 * Created by Admin on 2016/12/5.
 */

public class DestinationFragment extends Fragment implements NetControl.OnDestinationsCallBackListener {

    private NetControl netControl;
    private View mRootView;

    private MyDestinationAdapter mAdapter;
    private Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netControl = new NetControl();

        //获取目的地的数据
        netControl.getDestinations(this);

        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.destination_fragment, container, false);
        ListView listView = (ListView) mRootView.findViewById(R.id.destination_listview);
        mAdapter = new MyDestinationAdapter();
        listView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void OnDestinationsCallBack(final HashMap<Integer, ArrayList<Destination>> destinations) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(destinations);
            }
        });

    }
}
