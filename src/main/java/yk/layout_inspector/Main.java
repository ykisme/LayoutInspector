package yk.layout_inspector;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.Client;
import com.android.ddmlib.IDevice;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String ADB_PATH =
            "C:\\Users\\yk\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";

    public static void exitWithError() {
        System.exit(-1);
    }

    public static IDevice getDevice() {
        AndroidDebugBridge.init(true);
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge(ADB_PATH, false);
        if (!waitForDevice(bridge, 5)) {
            System.out.println("no devices connected.");
            return null;
        }
        IDevice[] devices = bridge.getDevices();
        if (devices == null || devices.length != 1) {
            throw new IllegalStateException("no device or no only one device");
        }
        return devices[0];
    }

    public static void main(String[] args) {
        IDevice device = getDevice();
        System.out.println(device.getAvdName());
        Client client = device.getClient("com.android.systemui");
        if (client == null) {
            System.out.println("client == null");
            exitWithError();
            return;
        }
        ListViewRootsHandler listViewRootsHandler = new ListViewRootsHandler();
        try {
            List<String> windows = listViewRootsHandler.getWindows(client, 20, TimeUnit.SECONDS);
            if (windows.size() == 0) {
                System.out.println("window is null or none");
                exitWithError();
                return;
            }
            for (int i = 0; i < windows.size(); i++) {
                System.out.println(i + ":" + windows.get(i));
            }
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            if (i < 0 || i > windows.size() - 1) {
                System.out.println("select error");
                exitWithError();
                return;
            }
            String window = windows.get(i);
            byte[] bytes = ViewDumpHandler.dumpViewHierarchy(client, window, 60);
            if (bytes != null) {
                IOUtil.saveBytes("view.li", bytes);
                System.out.println("save ok");
            } else {
                System.out.println("dump view error");
                exitWithError();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean waitForDevice(AndroidDebugBridge adb, int timeSeconds) {
        int i = 0;
        while (!adb.isConnected() && i++ < timeSeconds) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait for device");
        }
        return adb.isConnected();
    }

}
