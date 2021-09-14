package com.rachad.hungryforever;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Food {
	float width = 0.1f;
	float height = 0.1f/1.65f;
	float x = Glob.randomFloat(0,1-width);
	float y = -height;
	float vx = 0;
	float vy = Glob.vely;
	float  ax=0;
	float ay=0;
	float angle = 0;
	float va = 1.8f;
	Bitmap img;
	int frameX;
	int frameY;
	public Food(){
		if(Math.floor(Glob.randomFloat(0,2)) == 0){
			va*=-1;
		}
		frameX = (int)Math.floor(Glob.randomFloat(0,7));
		frameY = (int)Math.floor(Glob.randomFloat(0,Glob.randomness))==0?1:0;
		this.img = Bitmap.createBitmap(Glob.bitmap, frameX*Glob.spriteWidth, frameY*Glob.spriteHeight, Glob.spriteWidth, Glob.spriteHeight);



	}
	public void update(){
		vx+=ax;
		vy+=ay;
		x+=vx;
		y+=vy;
		angle += va;
	}
	public void draw(Canvas canvas, Paint paint, int w, int h){
		paint.setColor(0xffffffff);
		canvas.save();
		canvas.rotate(angle,x*w+width*w/2,y*h+height*h/2);
		float wi = width*w;
		float he = height*h;
		if(wi==0||he==0){
			canvas.drawRect(x*w,y*h,x*w+width*w,y*h+height*h,paint);

		}
		else{
			canvas.drawBitmap(Bitmap.createScaledBitmap(this.img,(int)wi,(int)he,false),x*w,y*h,paint);

		}

		canvas.restore();
	}
	public void foodCollisions(ArrayList<Food> foods,Player player){
		float[] mouth = {player.x- player.r/2.4f,player.y+ player.r/5.5f, player.x+ player.r/2.4f,player.y+ player.r/2.8f};
		mouth[0] += player.r/10;
		mouth[2] -= player.r/10;
		for (int i = 0; i < foods.size(); i++) {
			Food food = foods.get(i);
			if(food.y>1){
				foods.remove(i);
				Glob.score--;
				/*lost food*/
			}
			if(mouth[0] < food.x+food.width && mouth[2] > food.x && mouth[1] < food.y+food.height && mouth[3] > food.y){
				foods.remove(i);
				Glob.score++;
				Glob.eatenFood++;
				if(Glob.minIntervalTime<Glob.intervalTime){Glob.intervalTime--;}
				if(Glob.minRandomness<Glob.randomness && Glob.eatenFood%20 == 0){Glob.randomness--;}
				if(Glob.maxVely>Glob.maxVely){Glob.intervalTime+=0.0005f;}
			}
		}
	}
}
