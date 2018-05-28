package com.papercutlabs.jobstar.network;

import com.android.volley.VolleyError;

public interface ServerResponseStringCallback {

	public void onSuccess(String resultJsonObject);

	/**
	 * If there occurs any error while communicating with server
	 * 
	 * @param error
	 */
	public void ErrorMsg(VolleyError error);
}
