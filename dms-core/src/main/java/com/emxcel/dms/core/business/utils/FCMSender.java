package com.emxcel.dms.core.business.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gcm.server.Sender;

/**
 * @author ADMIN.
 */
public class FCMSender extends Sender {

  /**
   * @param key.
   */
  public FCMSender(String key) {
    super(key);
  }

  /* (non-Javadoc)
   * @see com.google.android.gcm.server.Sender#getConnection(java.lang.String)
   */
  @Override
  protected HttpURLConnection getConnection(String url) throws IOException {
    String fcmUrl = "https://fcm.googleapis.com/fcm/send";
    return (HttpURLConnection) new URL(fcmUrl).openConnection();
  }
}