package com.rachad.hungryforever;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Glob {
	public static float aspectRatio = 1.65f;
	public static int score = 0;
	public static Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.ctx.getResources(), R.drawable.img);
	public static int spriteWidth = 300;
	public static int spriteHeight = 300;
	public static int intervalTime = 1000;
	public static int minIntervalTime = 300;
	public static float vely = 0.01f;
	public static float maxVely = 0.03f;
	public static int randomness = 10;
	public static int minRandomness = 4;
	public static int eatenFood = 0;

	public static float randomFloat(float min,float max){
		return min + (float)Math.random() * (max - min);
	}
}
