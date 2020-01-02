# LayoutInspector
  把Android Studio LayoutInspector功能单独提取出来做成一个JAR旨在解决错误：There was a timeout error capturing the layout data from the device.The device may be too slow, the captured view may be too complex, or the view may contain animations.Please retry with a simplified view and ensure the device is responsive.
 
  这个错误是当Window的布局太复杂时，程序超时所导致的，比如SystemUI的statusWindow布局就非常复杂，极易发生这个错误。因为Android Studio的源码默认是指定了20s的延时，所以发生了超时的问题，本JAR通过修改超时时间来规避这个问题。

# 使用方法
编译生成Jar之后可以使用如下命令
java -jar ~/bin/LayoutInspector.jar ~/bin/android-sdk/platform-tools/adb com.android.systemui
第一个参数是jar的路径，第二个参数是adb工具的路径（我这里是ubuntu，windows下应该是xxx/adb.exe），第三个参数是包名。

请注意执行命令时不可有其他adb正在连接设备，建议关闭AndroidStudio，命令执行成功后会列出对应包当前所有的Window，输入序号就会开始获取布局文件。生成的li文件跟Android Studio生成的li文件相同，使用Android Studio就能打开。
