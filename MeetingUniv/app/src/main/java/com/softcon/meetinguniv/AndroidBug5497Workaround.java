package com.softcon.meetinguniv;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class AndroidBug5497Workaround {

    // For more information, see https://issuetracker.google.com/issues/36911528
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private Display display;
    private DisplayMetrics displayMetrics;

    private AndroidBug5497Workaround(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        this.displayMetrics = new DisplayMetrics();
//        this.display =
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        System.out.println(content.getChildCount());
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        System.out.println(usableHeightNow);
        if (usableHeightNow != usableHeightPrevious) {
            System.out.println(usableHeightPrevious);
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            System.out.println(usableHeightSansKeyboard);
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            System.out.println(heightDifference);
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                System.out.println("Hello");
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                System.out.println(frameLayoutParams.height);
            } else {
                System.out.println("hi~!");
                // keyboard probably just became hidden
//                frameLayoutParams.height = usableHeightSansKeyboard;
                frameLayoutParams.height = usableHeightNow;
            }

            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
//        Point size = new Point();
//        this.display.getRealSize(size);
//        Display display =
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
//        return this.displayMetrics.heightPixels;
    }
}
