package cn.net.darking.safeapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Zrzc on 2017/8/15.
 */

public class BottomView extends LinearLayout {
    public BottomView(Context context) {
        this(context, null);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
