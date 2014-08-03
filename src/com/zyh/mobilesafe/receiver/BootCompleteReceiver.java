package com.zyh.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		// ��ȡ֮ǰ�����sim
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String saveSim = sp.getString("sim", "");
		// ��ǰ��sim����Ϣ
		String realSim = tm.getSimSerialNumber();
		// �Ƚ�
		if (saveSim.equals(realSim)) {
			// sim��û�б��

		} else {
			// sim���Ѿ��������һ�����Ÿ���ȫ����
			String safenumber = sp.getString("safenumber", "");
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(safenumber, null, "sim card change!", null, null);

		}

	}
}
