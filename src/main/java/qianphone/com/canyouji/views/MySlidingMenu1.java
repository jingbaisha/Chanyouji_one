package qianphone.com.canyouji.views;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2016/12/5.
 * 主交互面板(视差效果的滑动主面板)
 */

public class MySlidingMenu1 extends ViewGroup {

    //获取用户操作意图的距离
    private static final double GET_OPERATE_DISTANCE = 30;

    public MySlidingMenu1(Context context) {
        this(context, null);
    }

    public MySlidingMenu1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量导航栏,导航栏占本容器宽度的一半
        naviContainer.measure(MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.7f), MeasureSpec.EXACTLY), heightMeasureSpec);
        contentContainer.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int naviLeft = -contentContainer.getMeasuredWidth() / 5;
        naviContainer.layout(naviLeft, t, naviLeft + naviContainer.getMeasuredWidth(), b);
//        naviContainer.layout(l, t, l + naviContainer.getMeasuredWidth(), b);
        contentContainer.layout(l, t, r, b);
    }

    private PointF pointF = new PointF();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointF.x = ev.getX();
                pointF.y = ev.getY();
                super.dispatchTouchEvent(ev);//DOWN事件正常下发事件
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isGetOperatType) {
                    getEventType(ev);
                } else {
                    if (isLeftRight) {
//                        System.out.println("左右滑动");
                        //计算移动的距离
                        float x = ev.getX();
                        int dx = (int) (x - pointF.x);
                        pointF.x = x;
                        //主面板可以移动的最大距离
                        int contentMaxLeft = (int) (contentContainer.getMeasuredWidth() * 0.7f);
                        //本次理论上主面板应该移动的距离
                        int contentLeft = contentContainer.getLeft() + dx;
                        if (contentLeft < 0) {
                            contentLeft = 0;
                        } else if (contentLeft > contentMaxLeft) {
                            contentLeft = contentMaxLeft;
                        }
                        //主面板移动
                        contentContainer.layout(contentLeft, contentContainer.getTop(), contentLeft + contentContainer.getMeasuredWidth(), contentContainer.getBottom());
                        //导航栏移动
                        //根据主面板移动的距离计算导航栏应该移动的距离
                        //1.计算出主面板当前移动距离所占百分比
                        float disPercent = contentLeft / (float) contentMaxLeft;
                        //2.根据百分比计算导航栏应该移动的距离
                        int naviMaxLeft = (-contentContainer.getMeasuredWidth() / 5);
                        int curNaviLeft = (int) (naviMaxLeft * disPercent);
                        curNaviLeft = naviMaxLeft - curNaviLeft;
                        naviContainer.layout(curNaviLeft, naviContainer.getTop(), naviContainer.getMeasuredWidth() + curNaviLeft, naviContainer.getBottom());
                    } else {
//                        System.out.println("上下滑动");
                        super.dispatchTouchEvent(ev);//正常分发事件
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isGetOperatType = false;
                super.dispatchTouchEvent(ev);
                break;
        }
//        super.dispatchTouchEvent(ev);
        return true;
    }

    //是否是左右操作
    private boolean isLeftRight;

    //标识是否获取到了用户的操作意图
    private boolean isGetOperatType;

    //获取用户的操作意图
    private void getEventType(MotionEvent ev) {
        System.out.println("未判断出用户操作意图");
        float x = ev.getX();
        float y = ev.getY();
        //根据手指移动的坐标计算两个点之间的距离
        double distance = Math.sqrt(Math.pow(pointF.x - x, 2) + Math.pow(pointF.y - y, 2));

        //距离大于30，看操作类型是上下操作还是左右操作，如果是上下操作，那么事件就正常下发。
        // 如果是左右操作则不下发事件，而是本容器来展开或者关闭导航栏
        if (distance > GET_OPERATE_DISTANCE) {
            //根据X偏移量和Y偏移量来区分是什么样的操作
            if (Math.abs(pointF.x - x) >= Math.abs(pointF.y - y)) {//左右滑动

                //更新计算的起始点
                pointF.x = x;
                pointF.y = y;

                isLeftRight = true;
                //虚拟一个CANCEL事件发下去
                MotionEvent event = MotionEvent.obtain(ev);
                event.setAction(MotionEvent.ACTION_CANCEL);
                super.dispatchTouchEvent(event);
            } else {//上下操作
                isLeftRight = false;
            }
            isGetOperatType = true;
        }

    }


    //导航栏
    private ViewGroup naviContainer;
    //内容显示区域
    private ViewGroup contentContainer;

    @Override
    protected void onFinishInflate() {
        //获取导航栏和内容显示容器
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalArgumentException("本容器必须只能有2个容器作为直接Child");
        }

        View child = getChildAt(0);
        if (!(child instanceof ViewGroup)) {
            throw new IllegalArgumentException("本容器的直接Child，必须是ViewGroup!");
        }
        naviContainer = (ViewGroup) child;

        child = getChildAt(1);
        if (!(child instanceof ViewGroup)) {
            throw new IllegalArgumentException("本容器的直接Child，必须是ViewGroup!");
        }
        contentContainer = (ViewGroup) child;
    }
}
