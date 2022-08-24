package com.tbum.prayertv.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.tbum.prayertv.R;


@SuppressLint({"AppCompatCustomView"})
public class VerticalScrollingTextView extends TextView {

    /* renamed from: a */
    public float f4616a = 30.0f;

    /* renamed from: a */
    public Scroller f4617a;

    /* renamed from: a */
    public boolean f4618a = true;

    public VerticalScrollingTextView(Context context) {
        super(context);
        m5978a((AttributeSet) null, 0);
        mo4778a(context);
    }

    public VerticalScrollingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m5978a(attributeSet, 0);
        mo4778a(context);
    }

    public VerticalScrollingTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m5978a(attributeSet, i);
        mo4778a(context);
    }

    /* renamed from: a */
    private void m5978a(AttributeSet attributeSet, int i) {
        mo4779a(getContext().obtainStyledAttributes(attributeSet, new int[]{R.attr.myTextStyle}, i, 0));
    }

    /* renamed from: a */
    public void mo4777a() {
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int lineCount = height + (getLineCount() * getLineHeight());
        this.f4617a.startScroll(0, -1 * height, 0, lineCount, (int) (((float) lineCount) * this.f4616a));
    }

    /* renamed from: a */
    public void mo4778a(Context context) {
        this.f4617a = new Scroller(context, new LinearInterpolator());
        setScroller(this.f4617a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo4779a(TypedArray typedArray) {
        String string = typedArray.getString(0);
        if (string != null && !string.equals((Object) null) && !string.equals("")) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), string));
        }
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.f4617a != null && this.f4617a.isFinished() && this.f4618a) {
            mo4777a();
        }
    }

    public float getSpeed() {
        return this.f4616a;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f4617a != null && this.f4617a.isFinished() && this.f4618a) {
            mo4777a();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f4617a.isFinished()) {
            mo4777a();
        }
    }

    public void setContinuousScrolling(boolean z) {
        this.f4618a = z;
    }

    public void setSpeed(float f) {
        this.f4616a = f;
    }
}
