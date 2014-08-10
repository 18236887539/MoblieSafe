package com.zyh.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyh.mobilesafe.utils.MD5Utils;

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
					case 1:// ����ͨѶ��ʿ
						startActivity(new Intent(HomeActivity.this, CallSmsSafeActivity.class));
						break;
					case 2:// ����Ӧ�ù���
						startActivity(new Intent(HomeActivity.this, AppManagerActivity.class));
						break;
					case 3:// ������̹���
						startActivity(new Intent(HomeActivity.this, TaskManagerActivity.class));
						break;
					case 7:// ����߼�����
						startActivity(new Intent(HomeActivity.this, AtoolsActivity.class));
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
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.bt_ok);
		cancel = (Button) view.findViewById(R.id.bt_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ���Ի���
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String password_confirm = et_setup_confirm.getText().toString().trim();
				if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				// �ж��Ƿ�һ��
				if (password.equals(password_confirm)) {
					// һ�£�ȡ���Ի��򣬱������룬�����ֻ���������
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.getMd5String(password));
					editor.commit();
					dialog.dismiss();

					startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
				} else {
					et_setup_confirm.setText("");
					Toast.makeText(HomeActivity.this, "�������벻һ��", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	/**
	 * ��������Ի���
	 */
	private void showEnterDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_enter_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.bt_ok);
		cancel = (Button) view.findViewById(R.id.bt_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ���Ի���
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				String savepassword = sp.getString("password", "");
				// �ж��Ƿ�һ��
				if (savepassword.equals(MD5Utils.getMd5String(password))) {
					// һ�£�ȡ���Ի��� �������ֻ���������
					dialog.dismiss();
					startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
				} else {
					et_setup_pwd.setText("");
					Toast.makeText(HomeActivity.this, "�������", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
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

	/**
	 * GridView ��������
	 * 
	 * @author zyh
	 * 
	 */
	class Myadpater extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_home_item);
			TextView textView = (TextView) view.findViewById(R.id.tv_home_item);
			textView.setText(names[position]);
			imageView.setImageResource(ids[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
}
