package yk.layout_inspector;

import com.android.ddmlib.Client;
import com.android.ddmlib.HandleViewDebug;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ViewDumpHandler extends HandleViewDebug.ViewDumpHandler {
    public ViewDumpHandler() {
        super(HandleViewDebug.CHUNK_VULW);
    }

    public ViewDumpHandler(int chunkVurt) {
        super(chunkVurt);
    }

    public static byte[] dumpViewHierarchy(Client client, String viewRoot, long timeout) {
        ViewDumpHandler handler = new ViewDumpHandler(HandleViewDebug.CHUNK_VURT);
        try {
            HandleViewDebug.dumpViewHierarchy(client, viewRoot, false,
                    true, (HandleViewDebug.ViewDumpHandler) handler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] var10;
        var10 = handler.getData(timeout, TimeUnit.SECONDS);
        return var10;
    }

    private final AtomicReference mData = new AtomicReference();

    protected void handleViewDebugResult(@NotNull ByteBuffer data) {
        byte[] b = new byte[data.remaining()];
        data.get(b);
        this.mData.set(b);
        System.out.println(new String((byte[]) mData.get()));
    }

    @Nullable
    public final byte[] getData(long timeout, @NotNull TimeUnit unit) {
        this.waitForResult(timeout, unit);
        return (byte[]) this.mData.get();
    }
}