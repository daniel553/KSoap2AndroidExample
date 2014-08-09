package com.example.testattval;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class KSoapClient extends AsyncTask<String, Void, Void> {

	Context context;
	TextView tv;

	private final String NAMESPACE = "http://www.w3schools.com/webservices/";
	private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	private final String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
	private final String METHOD_NAME = "CelsiusToFahrenheit";
	
	private static String celcius;
	private static String fahren;

	public KSoapClient(Context context, String celcius, TextView tv) {
		this.context = context;
		this.celcius = celcius;
		this.tv = tv;
	}

	@Override
	protected Void doInBackground(String... params) {
		getFahrenheit(this.celcius);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		tv.setText(fahren);
	}

	public void getFahrenheit(String celsius) {
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		// Property which holds input parameters
		PropertyInfo celsiusPI = new PropertyInfo();
		// Set Name
		celsiusPI.setName("Celsius");
		// Set Value
		celsiusPI.setValue(celsius);
		// Set dataType
		celsiusPI.setType(double.class);
		// Add the property to request object
		request.addProperty(celsiusPI);
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			fahren = response.toString();
			
			String requestDump = androidHttpTransport.requestDump;
			String responseDump = androidHttpTransport.responseDump;

			Log.i("", "Request: " + requestDump);
			Log.i("", "Response: " + responseDump);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
