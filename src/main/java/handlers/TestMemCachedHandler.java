package handlers;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class TestMemCachedHandler implements CachedHandler {

    public static final String CACHE = "AuthenticationCache";
    private static final MemCachedClient client;

    static {
        String[] servers = {"localhost:11211"};
        SockIOPool pool = SockIOPool.getInstance(CACHE);
        pool.setMinConn(2);
        pool.setMaxConn(20);
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(30);
        pool.setMaintSleep(90);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        client = new MemCachedClient(CACHE);
    }

    @Override
    public boolean flushAll() {
        return client.flushAll();
    }
}
