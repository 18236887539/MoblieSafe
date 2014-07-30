package com.zyh.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private GridView gridView;
	private SharedPreferences sp;
	private static final String[] names = { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���", "����ͳ��", "�ֻ�ɱ��", "��������", "�߼�����", "��������" };
	private static final int[] ids = { R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app, R.drawable.taskmanager,
			R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);

		gridView = (GridView) findViewById(R.id.gv_home);
		gridView.setAdapter(new Myadpater());
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:// �����ֻ�����
						showLostDialog();
						break;
					case 8:// ������������
						startActivity(new Intent(HomeActivity.this, SettingActivity.class));
						break;

					default:
						break;
				}
			}
		});
	}

	protected void showLostDialog() {
		// �ж��Ƿ����ù�����
		if (isSetupPwd()) {
			// �Ѿ����ù����룬��������Ի���
			showEnterDialog();
		} else {
			// û�����ù����룬������������Ի���
			showSetupPwdDialog();
		}
	}

	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;

	/**
	 * ��������Ի���
	 */
	private void showSetupPwdDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_setup_password, null);
		builder.setView(view);
		dialog = builder.show();
	}

	/**
	 * ��������Ի���
	 */
	private void showEnterDialog() {
		// TODO Auto-generated method stub

	}

	/**
	 * �ж��Ƿ����ù�����
	 * 
	 * @return
	 */
	protected boolean isSetupPwd() {
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
	}

	class Myadpater extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.list_home_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_home_item);
			TextView textView = (TextView) view.findViewById(R.id.tv_home_item);
			textView.setText(names[position]);
			imageView.setImageResource(ids[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
