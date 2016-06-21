package com.he.config;


import com.lq.ren.chat.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class HeDialog extends ProgressDialog {
	 Context context;
	public HeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;    
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.he_alert_dialog);
	}

	
}
