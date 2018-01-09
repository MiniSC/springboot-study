import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;


public class createClientExample {

    private static final String PATH = "/model1";
    private static final String ZK_ADDRESS = "192.168.108.65:2181";
    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();

        CuratorFramework client = null;
        try{
            client = createSimple(server.getConnectString());
            client.start();
            System.out.println("zk client start successfully!");
            /**
             * 如果需要创建父类
             */
            //client.create().creatingParentsIfNeeded().forPath(PATH, "test".getBytes());
            /**
             * 安静的关闭客户端
             */
            System.out.println(new String(client.getData().forPath(PATH)));
            CloseableUtils.closeQuietly(client);

           // client = createWithOpthions(server.getConnectString(),new ExponentialBackoffRetry(1000,3),1000,1000);
           // client.start();


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            CloseableUtils.closeQuietly(client);
           // CloseableUtils.closeQuietly(server);
        }
    }

    public static CuratorFramework createSimple(String connectionString){

        //first retry will wait 1 second second will wait 2
        //third will wait up to 4seconds
        /**
         * 两个参数，一个是基础睡眠时间，第二个是最大尝试次数
         * 第一次尝试会等待1秒，第二次2秒，第三次4秒
         * 遵循指数反演
         */
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000,3);

        //The simplest way to get a CuratorFramework instance
        //only required arguments are the connectionString and retryPolicy
        /**
         * 创建一个最基础的CuratorFramework的实例
         * 传入链接字符串和重试策略即可
         */
        return CuratorFrameworkFactory.newClient(connectionString,retryPolicy);
    }

    public static CuratorFramework createWithOpthions(String connectionString, RetryPolicy retryPolicy,int connectionTimeoutMs,int sessionTimeoutMs){

        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc details
        /**
         * 细粒度什么的，这里需要看一下建造者模式和
         * Effective java的静态建造者什么的，第二节的内容
         */
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                //etc.
                .build();
        /**
         * 使用了静态建造者模式来创建实例
         */
    }

}
