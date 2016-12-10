package qianphone.com.canyouji.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Admin on 2016/12/6.
 */

public class MyDestinationItemContainer extends RelativeLayout {
    public MyDestinationItemContainer(Context context) {
        super(context);
    }

    public MyDestinationItemContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec)*2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (width * 0.618f), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
