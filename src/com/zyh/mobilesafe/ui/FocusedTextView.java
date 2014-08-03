package com.zyh.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class FocusedTextView extends TextView {

	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FocusedTextView(Context context) {
		super(context);
	}

	@Override
	@ExportedProperty(category = "focus")
	/**
	 * ��ʵ��û�л�ý��㣬��ֻ����ƭϵͳ��
	 */
	public boolean isFocused() {
		return true;
	}

}
