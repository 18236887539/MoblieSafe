package com.zyh.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zyh.mobilesafe.services.AddressService;
import com.zyh.mobilesafe.services.CallSmsSafeService;
import com.zyh.mobilesafe.ui.SettingClickView;
import com.zyh.mobilesafe.ui.SettingItemView;
import com.zyh.mobilesafe.utils.ServiceUtils;

public class SettingActivity extends Activity {

	private SettingItemView siv_update;// ���ÿ����Զ�����
	private SettingItemView siv_show_address;// ������ʾ���������
	private SettingItemView siv_callsms_safe;// ���ú���������
	private SettingClickView scv_address_style;// ���ù�������������ʽ
	private Intent showAddress;
	private SharedPreferences sp;
	private Intent callSmsSafeIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		siv_show_address = (SettingItemView) findViewById(R.id.siv_show_address);
		siv_callsms_safe = (SettingItemView) findViewById(R.id.siv_callsms_safe);
		scv_address_style = (SettingClickView) findViewById(R.id.scv_address_style);
		showAddress = new Intent(this, AddressService.class);
		boolean isRunningService = ServiceUtils.isServiceRunning(this, "com.zyh.mobilesafe.services.AddressService");
		if (isRunningService) {
			siv_show_address.setChecked(true);
		} else {
			siv_show_address.setChecked(false);
		}
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean autoUpdate = sp.getBoolean("autoUpdate", true);
		if (autoUpdate) {
			// �Զ���������
			siv_update.setChecked(true);
		} else {
			// �Զ������ر�
			siv_update.setChecked(false);
		}

		// ���ÿ����Զ�����
		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				// �ж��Ƿ���ѡ��
				// �Ѿ����Զ�������
				if (siv_update.isChecked()) {
					siv_update.setChecked(false);
					editor.putBoolean("autoUpdate", false);

				} else {
					// û�д��Զ�����
					siv_update.setChecked(true);
					editor.putBoolean("autoUpdate", true);
				}
				editor.commit();
			}
		});
		// ���ÿ����Զ�����
		siv_show_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �ж��Ƿ���ѡ��
				if (siv_show_address.isChecked()) {
					siv_show_address.setChecked(false);
					stopService(showAddress);
				} else {
					// û�д��Զ�����
					siv_show_address.setChecked(true);
					startService(showAddress);
				}
			}
		});

		// ������������ʽ
		final String[] items = { "��͸��", "������", "��ʿ��", "������", "ƻ����" };
		scv_address_style.setTitle("��������ʾ����");
		final int which = sp.getInt("which", 0);
		scv_address_style.setDesc(items[which]);
		scv_address_style.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ̸һ���Ի���
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("��������ʾ����");
				builder.setSingleChoiceItems(items, which, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ����ѡ�����
						sp.edit().putInt("which", which).commit();
						scv_address_style.setDesc(items[which]);
						// ȡ���Ի���
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("ȡ��", null);
				builder.show();
			}
		});
		// ���ú���������
		callSmsSafeIntent = new Intent(this, CallSmsSafeService.class);
		siv_callsms_safe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_callsms_safe.isChecked()) {
					siv_callsms_safe.setChecked(false);
					stopService(callSmsSafeIntent);
				} else {
					siv_callsms_safe.setChecked(true);
					startService(callSmsSafeIntent);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		siv_show_address.setChecked(ServiceUtils.isServiceRunning(this, "com.zyh.mobilesafe.services.AddressService"));
		siv_callsms_safe.setChecked(ServiceUtils.isServiceRunning(this, "com.zyh.mobilesafe.services.CallSmsSafeService"));
	}
}
