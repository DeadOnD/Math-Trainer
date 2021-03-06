package com.thilo.android.util;

import org.apache.http.protocol.HTTP;

import com.thilo.android.pmtd.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AboutDialog {
	private Context ctx;
	private String app_name;
	private String app_version;
	private String android_version;
	private String screen_size;
	private String screen_dpi;

	public AboutDialog(Context ctx) {
		this.ctx = ctx;
		try
		{
			app_name = ctx.getPackageName();
		    app_version = ctx.getPackageManager().getPackageInfo(app_name, 0).versionName;
		}
		catch (NameNotFoundException e)
		{
		    app_version = "NOT FOUND";
		}
		android_version = android.os.Build.VERSION.RELEASE + "(" + android.os.Build.VERSION.CODENAME + ")";
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);;
		screen_size = dm.widthPixels + "x" + dm.heightPixels;
		screen_dpi  = dm.xdpi + "x" + dm.ydpi + " (" + dm.density + "/" + dm.densityDpi + ")";
	}
	
	public void show() {
		new AlertDialog.Builder(ctx)
		.setTitle(R.string.app_name)
		.setIcon(ctx.getResources().getDrawable(R.drawable.icon))
		.setMessage(ctx.getString(R.string.app_name) + " " + app_version + "\n"
				+ ctx.getString(R.string.about_text))
		.setPositiveButton(android.R.string.ok,null)
		.show();
	}

	private void sendEmailWithInfo(String subject, String text) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		// The intent does not have a URI, so declare the "text/plain" MIME type
		emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"android@lavar.de"}); // recipients
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, app_name + "/" + subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, text + "\n\n"
				+ "App version:           " + app_version + "\n"
				+ "Android version:       " + android_version + "\n"
				+ "Screen  WxH:           " + screen_size + "\n"
				+ "Density WxH (fac/dpi): " + screen_dpi);
		// NOT NEEDED: emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
		// You can also attach multiple items by passing an ArrayList of Uris
		
		// Create and start the chooser
		ctx.startActivity( // start with chooser made of intent and title
				Intent.createChooser(emailIntent, ctx.getString(R.string.about_chooser))
				);	
	}
}
