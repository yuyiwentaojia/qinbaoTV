package com.iqinbao.android.songstv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/9/21.
 */
public class GridViewTV extends GridView {

    public GridViewTV(Context context) {
        this(context, null);
    }

    public GridViewTV(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public boolean isInTouchMode() {
        return !(hasFocus() && !super.isInTouchMode());
    }

    private void init(Context context, AttributeSet attrs) {
        this.setChildrenDrawingOrderEnabled(true);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        View currentFocused = findFocus();
//        if (currentFocused == this) currentFocused = null;
//        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && event.getAction() == KeyEvent.ACTION_DOWN){
//            View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, View.FOCUS_LEFT);
//            if(nextFocused == null){
//                Log.d("========", "no move to left");
//            }
//        }else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && event.getAction() == KeyEvent.ACTION_DOWN){
//            View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, View.FOCUS_RIGHT);
//            if(nextFocused == null){
//                Log.d("========", "no move to right");
//            }
//        }
//
//        boolean ret = super.dispatchKeyEvent(event);
//        if(ret == true && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && event.getAction() == KeyEvent.ACTION_DOWN ||
//                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && event.getAction() == KeyEvent.ACTION_DOWN)){
//
//        }
//        return ret;
//    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.d("======", childCount+"==getChildDrawingOrder====i===="+i);
        if (this.getSelectedItemPosition() != -1) {
            if (i + this.getFirstVisiblePosition() == this.getSelectedItemPosition()) {// 这是原本要在最后一个刷新的item
                return childCount - 1;
            }
            if (i == childCount - 1) {// 这是最后一个需要刷新的item
                return this.getSelectedItemPosition() - this.getFirstVisiblePosition();
            }
        }
        return i;
    }

    public void setDefualtSelect(int pos) {
        requestFocusFromTouch();
        setSelection(pos);
    }

}