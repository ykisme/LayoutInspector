# LayoutInspector
  把Android Studio LayoutInspector功能单独提取出来做成一个JAR旨在解决错误：There was a timeout error capturing the layout data from the device.The device may be too slow, the captured view may be too complex, or the view may contain animations.Please retry with a simplified view and ensure the device is responsive.
 
  这个错误是当Window的布局太复杂时，程序超时所导致的，比如SystemUI的statusWindow布局就非常复杂，极易发生这个错误。因为Android Studio的源码默认是指定了20s的延时，所以发生了超时的问题，本JAR通过修改超时时间来规避这个问题。
