package com.rachad.hungryforever;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class Player {
	float x = 0.5f;
	float y = 0.85f;
	float r = 0.12f;
	int color = 0xffffff00;
	public void draw(Canvas canvas, Paint paint, int w, int h, ArrayList<Food> foods){
		addHeight(foods);
		float[] angles = nearestFoodAngle(foods);
		paint.setColor(color);
		canvas.drawCircle(x*w, y*h, r*w,paint);
		paint.setColor(0xffffffff);
		paint.setShadowLayer(2, 0, 0, 0xff000000);
		if(angles.length == 0 ) {
			canvas.drawCircle(x * w - r * w / 2.7f, y * h - r * w / 2f, r * w / 4, paint);
			canvas.drawCircle(x * w + r * w / 2.7f, y * h - r * w / 2f, r * w / 4, paint);
		}else{
			canvas.drawCircle(x * w - r * w / 2.7f + (float)Math.cos(angles[0])*r*w/16, y * h - r * w / 2f + (float)Math.sin(angles[0])*r*w/16, r * w / 4, paint);
			canvas.drawCircle(x * w + r * w / 2.7f + (float)Math.cos(angles[1])*r*w/16, y * h - r * w / 2f + (float)Math.sin(angles[1])*r*w/16, r * w / 4, paint);
		}
		paint.setShadowLayer(0, 0, 0, 0xff000000);
		paint.setColor(0xff000000);
		if(angles.length ==0) {
			canvas.drawCircle(x * w - r * w / 2.7f, y * h - r * w / 2f, r * w / 10, paint);
			canvas.drawCircle(x * w + r * w / 2.7f, y * h - r * w / 2f, r * w / 10, paint);
		}else{
			canvas.drawCircle(x * w - r * w / 2.7f + (float)Math.cos(angles[0])*r*w/8, y * h - r * w / 2f + (float)Math.sin(angles[0])*r*w/8, r * w / 10, paint);
			canvas.drawCircle(x * w + r * w / 2.7f + (float)Math.cos(angles[1])*r*w/8, y * h - r * w / 2f + (float)Math.sin(angles[1])*r*w/8, r * w / 10, paint);
		}
	//	canvas.drawRoundRect(x-r/3.5f,y+r/5f,x+r/3.5f,y+r/3f,10,10,paint);
		float addh = addHeight(foods);
		addh = addh == -1?0:addh;
		paint.setColor(0xffff0000);
		RectF rectf= new RectF(x*w-r*w/2.4f, y*h+r*w/5.5f - addh*h/2 , x*w+r*w/2.4f, y*h+r*w/2.8f + addh*h/2);
		canvas.drawRoundRect(rectf,1000,1000, paint);
		paint.setColor(0xffaa0000);
		RectF rectf2= new RectF(x*w-r*w/3.2f, y*h+r*w/3.6f - addh*h/2 , x*w+r*w/3.2f, y*h+r*w/4f + addh*h/2);
		canvas.drawRoundRect(rectf2,1000,1000, paint);
	}
	public void move(float _x){
		if(_x-r<0){_x=r;}
		if(_x+r>1){_x=1-r;}
		x = _x;
	}
	public float[] nearestFoodAngle(ArrayList<Food> foods){
		if(foods.size() == 0)return new float[]{};
		Food nf = foods.get(0);
		float dx = nf.x+nf.width/2 - x;
		float dy = (nf.y+nf.height/2 - y)*Glob.aspectRatio;

		float angle1 = (float) Math.atan2(dy-r*Glob.aspectRatio/2, dx-r/2.7);
		float angle2 = (float) Math.atan2(dy-r*Glob.aspectRatio/2, dx+r/2.7);
		return new float[]{angle1,angle2};
	}
	public float addHeight(ArrayList<Food> foods){
		if(foods.size() == 0 || foods.get(0).frameY == 1) return  -1;
		Food nf = foods.get(0);
		float dx = nf.x+nf.width/2 - x;
		float dy = (nf.y+nf.height/2 - y)*Glob.aspectRatio;
		float prc = 1f - (float) Math.abs(Math.sqrt(dx*dx+dy*dy*Glob.aspectRatio*Glob.aspectRatio)) / 2.5f;
		return prc * r/5.5f;
	}
}
