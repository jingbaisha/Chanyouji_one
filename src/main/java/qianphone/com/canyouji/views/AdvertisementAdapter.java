package qianphone.com.canyouji.views;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Admin on 2016/11/23.
 */

public abstract class AdvertisementAdapter extends PagerAdapter {
    /**
     * 返回真实的广告页数
     *
     * @return
     */
    public abstract int getRealCount();

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);

    @Override
    public abstract void destroyItem(ViewGroup container, int position, Object object);
}
