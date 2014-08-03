package com.zyh.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.zyh.mobilesafe.R;
import com.zyh.mobilesafe.services.GPSService;

public class SMSReceiver extends BroadcastReceiver {

	private static final String tag = "SMSReceiver";
	// �豸������
	private DevicePolicyManager dpm;

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String safenumber = sp.getString("safenumber", "");
		dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

		// ���ն���
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object b : objs) {
			// �����ĳһ������
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) b);
			// ������
			String sender = sms.getOriginatingAddress();
			String body = sms.getMessageBody();

			Log.v(tag, sender);
			Log.v(tag, body);
			if (sender.contains(safenumber) || safenumber.contains(sender)) {
				if (body.contains("#*location*#")) {
					abortBroadcast();
					Log.i(tag, "�õ��ֻ�GPS");
					context.startService(new Intent(context, GPSService.class));
					SharedPreferences sp2 = context.getSharedPreferences("config", Context.MODE_PRIVATE);
					String lastlocation = sp2.getString("lastlocation", null);
					if (TextUtils.isEmpty(lastlocation)) {
						SmsManager.getDefault().sendTextMessage(sender, null, "getting lastlocation....", null, null);
					} else {
						System.out.println(lastlocation);
						SmsManager.getDefault().sendTextMessage(sender, null, lastlocation, null, null);
					}
				} else if (body.contains("#*alarm*#")) {
					abortBroadcast();
					Log.i(tag, "���ű�������");
					MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
					player.setVolume(1.0f, 1.0f);// ����
					player.setLooping(true);// ѭ������
					player.start();
				} else if (body.contains("#*wipedata*#")) {
					abortBroadcast();
					Log.i(tag, "Զ���������");
					// ���sdcard����
					dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
					// �ָ���������
					dpm.wipeData(0);
				} else if (body.contains("#*lockscreen*#")) {
					abortBroadcast();
					Log.i(tag, "Զ������");
					dpm.lockNow();
					// dpm.resetPassword("", 0);// ������������
				}
			}
		}
	}

}
