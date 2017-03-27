package com.emxcel.dms.core.business.utils;

import java.util.Random;

/**
 * @author ADMIN.
 */
public class OTPData {

  /**
   * @return int.
   */
  public static int generateOtp() {
    Random r = new Random(System.currentTimeMillis());
    return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
  }
}