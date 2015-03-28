package com.taobao.android.gamecenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taobao.android.gamecenter.download.GcAppDownLoadService;

public class GameReceiver extends BroadcastReceiver {
	public GameReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		System.out.println("GameReceiver.onReceive()"+intent.getStringExtra("msg"));
	    Intent intent2 = new Intent(context, GcAppDownLoadService.class);
	    context.startService(intent2);
	}
}
