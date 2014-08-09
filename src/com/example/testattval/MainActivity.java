package com.example.testattval;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.printer);
		
		button = (Button)findViewById(R.id.buttonCallService);
		
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				KSoapClient soap =new KSoapClient(getBaseContext(), "100", tv);
				soap.execute();
			}
		});

		bean b = new bean();
		b.doubler = 1.0;
		b.integer = 2;
		b.stringer = "3";
		showFields(b);

	}

	public void showFields(Object o) {
		Class<?> class_ = o.getClass();

		for (Field field : class_.getDeclaredFields()) {
			// you can also use .toGenericString() instead of .getName(). This
			// will
			// give you the type information as well.
			//Class<?> c = o.getClass();

			try {
				String name = field.getName();
				field.setAccessible(true);
				String value = field.get(o).toString();

				StringBuilder sb = new StringBuilder("Field");
				sb.append(" " + name);
				sb.append(": ");
				sb.append(value);

				sb.append("\n");
				tv.append(sb);

			} catch (IllegalAccessException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
