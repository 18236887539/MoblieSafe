package com.zyh.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyh.mobilesafe.R;

public class SettingItemView extends RelativeLayout {

	private CheckBox cb_status;
	private TextView tv_title;
	private TextView tv_desc;

	private String desc_on;
	private String desc_off;

	/**
	 * ��ʼ�������ļ�
	 * 
	 * @param context
	 */
	private void initView(Context context) {

		// ��һ�������ļ� view ���ص�SettingItemView��
		View.inflate(context, R.layout.settin_item_view, this);
		cb_status = (CheckBox) findViewById(R.id.cb_status);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_desc = (TextView) findViewById(R.id.tv_desc);

	}

	/**
	 * У����Ͽؼ��Ƿ�ѡ��
	 */

	public boolean isChecked() {
		return cb_status.isChecked();
	}

	/**
	 * ������Ͽؼ���״̬
	 */

	public void setChecked(boolean checked) {
		if (checked) {
			setDesc(desc_on);
		} else {
			setDesc(desc_off);
		}
		cb_status.setChecked(checked);
	}

	/**
	 * ���� ��Ͽؼ���������Ϣ
	 */

	public void setDesc(String text) {
		tv_desc.setText(text);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * �����ļ�ʹ��ʱ����
	 * 
	 * @param context
	 * @param attrs
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);

		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyh.mobilesafe", "title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyh.mobilesafe", "desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyh.mobilesafe", "desc_off");
		tv_title.setText(title);
		setDesc(desc_off);
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

}
