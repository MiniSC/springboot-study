import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author dongjj
 * @createtime 2018.1.8
 * Zookeeper JAVA API
 */
public class ZookeeperTest  {
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper  = new ZooKeeper("127.0.0.1:2181", 100, null);
        String path = "/demo1";
        /**
         * 创建节点的方法，create
         * 参数1：路径
         * 参数2：数据data
         * 参数3：访问控制权限 OPEN_ACL_UNSAFE 开放的访问权限，不安全--
         *        这里给出的访问权限还有CREATOR_ALL_ACL创建者所有权限   READ_ACL_UNSAFE读权限
         *
         */
        zooKeeper.create(path,"21".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //创建一个节点,模式是PERSISTENT
        System.out.println("创建节点" + path + ",数据为：" + new String(zooKeeper.getData(path, null, null)));
        //修改节点数据
        zooKeeper.setData(path, "2".getBytes(), -1);
        System.out.println("修改节点" + path + ",数据为：" + new String(zooKeeper.getData(path, null, null)));
        //删除一个节点
        System.out.println(zooKeeper.exists(path, null));
        zooKeeper.delete(path, -1);
        //节点是否存在
        System.out.println(zooKeeper.exists(path, null));

    }
}
