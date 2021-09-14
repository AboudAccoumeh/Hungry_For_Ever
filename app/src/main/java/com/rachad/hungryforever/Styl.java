package com.rachad.hungryforever;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Styl {
	float x;
	float y;
	float width;
	float height;
	float vx;
	float vy;
	Bitmap img;
	public Styl(float x,float y,float width,float height,float vx, float vy,int frameX,int frameY){
		this.x =x;
		this.y =y;
		this.width = width;
		this.height = height;
		this.vx = vx;
		this.vy = vy;
		img = Bitmap.createBitmap(Glob.bitmap, frameX*Glob.spriteWidth, frameY*Glob.spriteHeight, Glob.spriteWidth, Glob.spriteHeight);
	}
	public void draw(Canvas canvas, Paint paint,int w, int h){
		float wi = width*w;
		float he = height*h;
		if(wi!=0&&he!=0){
			canvas.drawBitmap(Bitmap.createScaledBitmap(this.img,(int)wi,(int)he,false),x*w,y*h,paint);
		}
	}
	public void update(){
		x += vx;
		y += vy;
		if((x<0 && vx<0) || (x>1-width && vx>0)){
			vx*= -1;
		}
	}
}
