package com.yaheen.pdaapp.rfid;

import com.magicrf.uhfreaderlib.readerInterface.DevicePowerInterface;

public class SerialPort implements DevicePowerInterface {

  public void uhfPowerOn()
  {
	psampoweron();
  }

  public void uhfPowerOff()
  {
    psampoweroff();
  }

  static
  {
    System.loadLibrary("devapi");
    System.loadLibrary("uhf");
  }
  
  public native void psampoweron();
  
  public native void psampoweroff();
}
