import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;

public class main {

    public static void main(String[] args) throws IOException {
        OSCBundle bundle = new OSCBundle();
        OSCMessage message = new OSCMessage();
        message.setAddress("/torso1/command");
        message.addArgument(10);
        message.addArgument((float)10.1);
        message.addArgument("set_address");

        bundle.addPacket(message);

        OSCPortOut sender = new OSCPortOut(InetAddress.getByName("10.101.1.11"), 8001);
        sender.send(message);
        System.out.println(message.getAddress() + " : " + message.getArguments());
        sender.close();

        System.out.println("Hello world");
    }
}
