package com.greetsweet.plant.constantclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.greetsweet.plant.PhotoApp;


public class storeSharedPref {
	public static final String MyPREFERENCES = "GreetingApp";
	public static Context _ctx;

	public storeSharedPref(Context ctx) {
		_ctx = ctx;

	}

	public void storeSharedValue(String key, String value)

	{
		SharedPreferences sharedpreferences = PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(key, value);
		editor.commit();

	}
	public void storeSharedValueInt(String key, int value)

	{
		SharedPreferences sharedpreferences = PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();

	}
	public void storeSharedValuetypeface(String key, Typeface value)

	{
		SharedPreferences sharedpreferences = PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();

	}
	public void storeSharedValueuri(String key, Bitmap value)

	{
		SharedPreferences sharedpreferences = PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();

	}
	public void resetValues()
	{

	SharedPreferences sharedpreferences =  PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.clear().commit();
		editor.commit();

		
	}

	public void storeSharedValue(String key, Boolean value)

	{
		SharedPreferences sharedpreferences =  PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();

	}

	public String getSharedValue(String key) {

		SharedPreferences sharedpreferences =  PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);

		// sharedpreferences.getString(key, "0");

		return sharedpreferences.getString(key, "NA");
	}
	public String getSharedValueInt(int key) {

		SharedPreferences sharedpreferences =  PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);

		// sharedpreferences.getString(key, "0");

		return sharedpreferences.getString(String.valueOf(key), "NA");
	}


	public String getSharedValue(String key, String _default) {

		SharedPreferences sharedpreferences =  PhotoApp.getInstance().getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);

		// sharedpreferences.getString(key, "0");

		return sharedpreferences.getString(key, _default);
	}


}
