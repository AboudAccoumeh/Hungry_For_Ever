package com.rachad.hungryforever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
	public static Context ctx;
	ImageView img;
	LinearLayout container;

	Canvas canvas;
	Paint paint;
	Paint strokePaint;
	Paint gradientPaint;
	Bitmap bitmap;
	float cloudX = 0;
	float cloudVx = 0.05f;

	ArrayList<Food> foods = new ArrayList<>();
	ArrayList<Styl> styles = new ArrayList<>();

	Player player = new Player();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctx = MainActivity.this;
		container = findViewById(R.id.all);
		img = findViewById(R.id.imageViewCanvas);
		paint = new Paint();
		strokePaint = new Paint();
		gradientPaint = new Paint();
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(5);
		styles.add(new Styl(0.05f,0.68f,0.3f,0.24f,0,0,3,2));
		styles.add(new Styl(0.65f,0.68f,0.3f,0.24f,0,0,4,2));
		styles.add(new Styl(0.2f,0.1f,0.25f,0.1f,0.0015f,0,2,2));
		styles.add(new Styl(0.35f,0.73f,0.15f,0.17f,0,0,5,2));
		styles.add(new Styl(0.5f,0.69f,0.16f,0.22f,0,0,5,2));
		new CountDownTimer(200, 200) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {

				resizeImg(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, 1/1.65f);
				startTimer(Glob.intervalTime);
				animate();
			/*	resizeImg(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, (float) 1.65);
				animate();*/
			}
		}.start();
	}

	public void resizeImg(int screenOrientation, float rate) {
		setRequestedOrientation(screenOrientation);
		int height = container.getHeight();
		int width = (int) (container.getHeight() * rate);
		img.getLayoutParams().width = width;
		img.getLayoutParams().height = height;
		img.requestLayout();
		img.setScaleX((float) container.getWidth() / width);
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
	}

	public void animate() {
		int w = img.getWidth(), h = img.getHeight();
		LinearGradient lg = new LinearGradient(0,0,0,20,new int[]{0xff93d9f9,0xff80d8ff,0xff57bbf8},new float[]{0,0.3f,1}, Shader.TileMode.CLAMP);
		lg = new LinearGradient(0, h/3f, 0, h, 0xff93d9f9, 0xff0088ff, Shader.TileMode.CLAMP);
		gradientPaint.setDither(true);
		gradientPaint.setShader(lg);
		canvas.drawRect(0, 0, w, h, gradientPaint);
		paint.setColor(0xff00dd00);
		strokePaint.setColor(0xff007700);
		canvas.drawRect(-10,0.87f*h,w+10,h+10,paint);
		canvas.drawRect(-10,0.87f*h,w+10,h+10,strokePaint);

		for(int o=0;o<styles.size();o++){
			Styl style = styles.get(o);
			style.draw(canvas,paint,w,h);
			style.update();
		}
		player.draw(canvas, paint, w, h, foods);
		for(int i=0;i<foods.size();i++){
			Food food = foods.get(i);
			food.draw(canvas,paint,w,h);
			food.update();
			food.foodCollisions(foods,player);
		}
		paint.setTextSize(40);
		paint.setColor(0xffffffff);
		//paint.setTextAlign(Paint.Align.CENTER);
		paint.setShadowLayer(3, 0, 0, 0xff000000);
		canvas.drawText("score:"+Glob.score,0,40,paint);
		paint.setShadowLayer(0, 0, 0, 0xff000000);
		img.setImageBitmap(bitmap);
		cloudX+=cloudVx;
		if(cloudX>1 && cloudVx>0){cloudVx*=-1;}
		else if(cloudVx<0 && cloudVx<0){cloudVx+=-1;}
		new CountDownTimer(20, 20) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				animate();
			}
		}.start();
	}
	public void startTimer(int intervalTime){
		new CountDownTimer(intervalTime,intervalTime) {
			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				foods.add(new Food());
				startTimer(Glob.intervalTime);
			}
		}.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {

		float x = e.getX();
		//float y = e.getY();

		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				player.move(x / img.getWidth());
		}

		return true;
	}
}