package qianphone.com.canyouji.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

/**
 * Created by Admin on 2016/11/23.
 */

public class AdvertismentContainer extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private float scale = 2.5316f;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec =  MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) / scale), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static final int SWTICH_PAGER = 11111;
    private long interval = 3000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SWTICH_PAGER:

                    int curIndex = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(++curIndex, true);

                    handler.sendEmptyMessageDelayed(SWTICH_PAGER, interval);
                    break;
            }
        }
    };

    public AdvertismentContainer(Context context) {
        this(context, null);
    }

    public AdvertismentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 设置广告的切换间隔时间
     *
     * @param interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    private boolean isAutoPlay;

    /**
     * 开始自动播放广告
     */
    public void startAutoPlay() {
        handler.sendEmptyMessageDelayed(SWTICH_PAGER, interval);
        isAutoPlay = true;
    }

    private ViewPager mViewPager;
    private RadioGroup mPiontContainer;

    private void init(Context context) {
        mViewPager = new ViewPager(context);
        mPiontContainer = new RadioGroup(context);
        mPiontContainer.setOrientation(RadioGroup.HORIZONTAL);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(params);

        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = 20;
        mPiontContainer.setLayoutParams(params);

        addView(mViewPager);
        addView(mPiontContainer);
    }

    private AdvertisementAdapter mAdapter;

    public void setAdapter(AdvertisementAdapter adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;


        //根据适配器中真实的广告页数去初始化点的个数
        initPoint();
        mViewPager.setAdapter(adapter);
        int showPosition = adapter.getCount() >> 1;
        //为了让默认显示的是第0页
        showPosition = showPosition - (showPosition % adapter.getRealCount());
        mViewPager.setCurrentItem(showPosition);

        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 设置“点”的图片资源
     *
     * @param res
     */
    public void setPointDrawableRes(int res) {
        int count = mPiontContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View button = mPiontContainer.getChildAt(i);
            button.setBackgroundResource(res);
        }
    }

    private void initPoint() {
        for (int i = 0; i < mAdapter.getRealCount(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            //去掉自带的图标
            radioButton.setButtonDrawable(new ColorDrawable(0x00000000));
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(50, 50);
            params.leftMargin = 10;
            radioButton.setLayoutParams(params);
            mPiontContainer.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int showIndex = position % mAdapter.getRealCount();
        RadioButton btn = (RadioButton) mPiontContainer.getChildAt(showIndex);
        btn.setChecked(true);
    }


    private boolean isStoped;

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                if (!isAutoPlay) {
                    return;
                }
                handler.removeMessages(SWTICH_PAGER);
                isStoped = true;

                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (isStoped && isAutoPlay) {
                    handler.sendEmptyMessageDelayed(SWTICH_PAGER, interval);
                    isStoped = false;
                }
                break;
            case ViewPager.SCROLL_STATE_SETTLING:

                break;
        }

    }
}
