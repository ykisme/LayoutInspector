package yk.layout_inspector;

import com.android.ddmlib.Client;
import com.android.ddmlib.HandleViewDebug;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class ListViewRootsHandler extends HandleViewDebug.ViewDumpHandler {
    private final CopyOnWriteArrayList myViewRoots = new CopyOnWriteArrayList();

    protected void handleViewDebugResult(@NotNull ByteBuffer data) {
        int nWindows = data.getInt();
        int var3 = 0;

        for (int var4 = nWindows; var3 < var4; ++var3) {
            int len = data.getInt();
            this.myViewRoots.add(getString(data, len));
        }

    }

    @NotNull
    public final List<String> getWindows(@NotNull Client c, long timeout, @NotNull TimeUnit unit) throws IOException {
        HandleViewDebug.listViewRoots(c, (HandleViewDebug.ViewDumpHandler) this);
        this.waitForResult(timeout, unit);
        ArrayList<String> windows = new ArrayList<String>();
        Iterator var7 = this.myViewRoots.iterator();

        while (var7.hasNext()) {
            String root = (String) var7.next();
            windows.add(root);
        }

        return windows;
    }

    public ListViewRootsHandler() {
        super(HandleViewDebug.CHUNK_VULW);
    }
}