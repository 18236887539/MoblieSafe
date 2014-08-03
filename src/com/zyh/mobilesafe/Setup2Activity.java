package com.zyh.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.zyh.mobilesafe.ui.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {

	private SettingItemView siv_setup2_sim;
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		siv_setup2_sim = (SettingItemView) findViewById(R.id.siv_setup2_sim);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String sim = sp.getString("sim", null);
		if (TextUtils.isEmpty(sim)) {
			// û�а�sim
			siv_setup2_sim.setChecked(false);
		} else {
			// ����sim
			siv_setup2_sim.setChecked(true);
		}

		siv_setup2_sim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ����sim�������к�
				String sim = tm.getSimSerialNumber();
				Editor editor = sp.edit();
				if (siv_setup2_sim.isChecked()) {
					siv_setup2_sim.setChecked(false);
					editor.putString("sim", null);
				} else {
					siv_setup2_sim.setChecked(true);
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		});
	}

	@Override
	public void showNext() {
		// ȡ��sim���Ƿ����
		String sim = sp.getString("sim", null);
		if (TextUtils.isEmpty(sim)) {
			// û�а�
			Toast.makeText(this, "����û�а�sim��", Toast.LENGTH_SHORT).show();
			return;
		}
		startActivity(new Intent(this, Setup3Activity.class));
		this.finish();
		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_left_out);
	}

	@Override
	public void showPre() {
		startActivity(new Intent(this, Setup1Activity.class));
		this.finish();
		overridePendingTransition(R.anim.base_slide_left_in, R.anim.base_slide_right_out);
	}

}
