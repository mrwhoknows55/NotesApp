package com.mrwhoknows.notes.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.mrwhoknows.notes.R;

public class LinedEditText extends AppCompatEditText {

    private Rect r;
    private Paint paint;

    @SuppressLint("ResourceAsColor")
    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        r = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(R.color.primaryDarkColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = ((View) this.getParent()).getHeight();
        int lineHeight =  getLineHeight();
        int numberOfLines  = height / lineHeight;

        Rect r = this.r;
        Paint paint = this.paint;

        int baseline = getLineBounds(0, r);

        for (int i=0; i< numberOfLines; i++){
            canvas.drawLine(r.left, baseline+1, r.right, baseline+1, paint);
            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
