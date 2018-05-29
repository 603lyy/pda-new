package com.yaheen.pdaapp.rfid;

public class UhfReaderDevice {
	
  private static UhfReaderDevice readerDevice;
  private static SerialPort devPower;
  
  public static UhfReaderDevice getInstance()
  {
    if (devPower == null)
    {
      try
      {
        devPower = new SerialPort();
      }
      catch (Exception e)
      {
        return null;
      }
     // devPower.psampoweron();
    }
    
    if (readerDevice == null) {
    	readerDevice = new UhfReaderDevice();
    }
    
    return readerDevice;
  }

  public void powerOn()
  {
    //devPower.psampoweron();
  }
  
  public void powerOff()
  {
	if (devPower != null) {
		//devPower.psampoweroff();
		devPower = null;
	}
  }
}
