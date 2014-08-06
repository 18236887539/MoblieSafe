package com.zyh.mobilesafe;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zyh.mobilesafe.receiver.MyAdmin;

public class Setup4Activity extends BaseSetupActivity {

	private SharedPreferences sp;
	private CheckBox cb_proteting;
	private DevicePolicyManager dpm;
	ComponentName mDeviceAdminSample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(Setup4Activity.this, MyAdmin.class);
		cb_proteting = (CheckBox) findViewById(R.id.cb_proteting);

		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting && dpm.isAdminActive(mDeviceAdminSample)) {
			// �ֻ������Ѿ�����
			cb_proteting.setText("�ֻ������Ѿ�����");
			cb_proteting.setChecked(true);
		} else {
			// �ֻ���û����
			cb_proteting.setText("�ֻ�������û�п���");
			cb_proteting.setChecked(false);
		}
		cb_proteting.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("ischeck" + isChecked);
				// ����ѡ���״̬
				Editor editor = sp.edit();
				if (isChecked) {
					// �鿴�Ƿ񼤻����豸������
					if (!dpm.isAdminActive(mDeviceAdminSample)) {
						openAdmin();
						return;
					}
					cb_proteting.setText("�ֻ������Ѿ�����");
					editor.putBoolean("protecting", true);
				} else {
					cb_proteting.setText("�ֻ�������û�п���");
					editor.putBoolean("protecting", false);
				}
				editor.commit();
			}
		});
	}

	@Override
	public void showNext() {
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		startActivity(new Intent(this, LostFindActivity.class));
		this.finish();
	}

	@Override
	public void showPre() {
		startActivity(new Intent(this, Setup3Activity.class));
		overridePendingTransition(R.anim.base_slide_left_in, R.anim.base_slide_right_out);
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("-------------onstart");
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting && dpm.isAdminActive(mDeviceAdminSample)) {
			// �ֻ������Ѿ�����
			cb_proteting.setText("�ֻ������Ѿ�����");
			cb_proteting.setChecked(true);
		} else {
			// �ֻ���û����
			cb_proteting.setText("�ֻ�������û�п���");
			cb_proteting.setChecked(false);
		}
	}

	/**
	 * �ô���ȥ��������Ա
	 * 
	 * @param view
	 */
	private void openAdmin() {
		// ����һ��Intent
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// ��Ҫ����˭
		ComponentName mDeviceAdminSample = new ComponentName(this, MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
		// Ȱ˵�û���������ԱȨ��
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "���ǰ�ȫ��ʿ���豸������");
		startActivity(intent);
	}

}
