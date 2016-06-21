package com.he.widget;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class MaskedImage extends ImageView {
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;

	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public MaskedImage(Context paramContext) {
		super(paramContext);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public abstract Bitmap setDrawableMask();

	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null)
			return;
		try {
			if (this.paint == null) {
				this.paint = new Paint();
				this.paint.setFilterBitmap(false);
				Xfermode xfermode = MASK_XFERMODE;
				@SuppressWarnings("unused")
				Xfermode xfermode1 = this.paint.setXfermode(xfermode);
			}
			float f1 = getWidth();
			float f2 = getHeight();
			int i = canvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
			drawable.setBounds(0, 0, j, k);
			drawable.draw(canvas);
			Bitmap bitmap;
			if ((this.mask == null) || (this.mask.isRecycled())) {
				 bitmap = setDrawableMask();
				this.mask = bitmap;
			}
			canvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
			canvas.restoreToCount(i);

			return;
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap. View ID = ");
			System.out.println("localStringBuilder=="+localStringBuilder);
		}
	}
}
