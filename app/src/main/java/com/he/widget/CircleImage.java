package com.he.widget;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class CircleImage extends MaskedImage {
	public CircleImage(Context paramContext) {
		super(paramContext);
	}

	public CircleImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CircleImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public Bitmap setDrawableMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap bitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(1);
		paint.setColor(Color.WHITE);
		//paint.setColor(-16777216);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF rectF = new RectF(0.0F, 0.0F, f1, f2);
		canvas.drawOval(rectF, paint);
		//canvas.drawRoundRect(rectF, 30, 30, paint);
		return bitmap;
	}
}
