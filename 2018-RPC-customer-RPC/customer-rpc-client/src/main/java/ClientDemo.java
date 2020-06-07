import com.darian.RpcClientProxy;
import com.darian.service.HelloService;

public class ClientDemo {

    public static void main(String[] args) {
        RpcClientProxy rpccClientProxy = new RpcClientProxy();

        HelloService helloService = rpccClientProxy.clientProxy
                (HelloService.class, "localhost", 8888);

        System.out.println(helloService.sayHello("Darian"));
    }
}