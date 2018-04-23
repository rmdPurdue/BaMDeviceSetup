import com.illposed.osc.*;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.*;

public class main extends Application {
    private Stage stage;
    private View view;
    private Model model;
    private InetAddress broadcast = null;
    private OSCPortIn receiver;
    private Task getNetworkDevices;
    private String localIp;
    private byte[] localIpBytes;
    private ArrayList<Network> networks = new ArrayList<>();
    private ArrayList<Device> devices = new ArrayList<>();
    private Task<InetAddress> getBroadcastAddress;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        view = new View();
        this.stage = stage;

        this.stage.setTitle("Device Setup");
        this.stage.sizeToScene();
        this.stage.setScene(view.scene);
        this.stage.setResizable(false);
        this.stage.show();

        this.stage.setOnCloseRequest(event -> closeProgram());

        OSCBundle testBundle = new OSCBundle();
        OSCMessage updateIP = new OSCMessage("/hub/setup/ip");
        updateIP.addArgument(10);
        updateIP.addArgument(101);
        updateIP.addArgument(1);
        updateIP.addArgument(11);

        OSCMessage updatePort = new OSCMessage("/hub/setup/port");
        updatePort.addArgument(9000);

        OSCMessage updateName = new OSCMessage("/hub/setup/name");
        updateName.addArgument("torso1");

        OSCMessage updateInput0 = new OSCMessage("/hub/setup/input0");
        updateInput0.addArgument(0);
        updateInput0.addArgument(1024);
        updateInput0.addArgument(50);

        testBundle.addPacket(updateName);
        testBundle.addPacket(updateIP);
        testBundle.addPacket(updatePort);
        testBundle.addPacket(updateInput0);

        List<OSCPacket> messages = testBundle.getPackets();
        Iterator bundleIterator = messages.iterator();
        Device tempDevice = new Device();
        while(bundleIterator.hasNext()) {
            OSCMessage message = (OSCMessage) bundleIterator.next();
            switch(message.getAddress()) {
                case "/hub/setup/name":
                    tempDevice.setName((String)message.getArguments().get(0));
                    break;
                case "/hub/setup/ip":
                    List args = message.getArguments();
                    byte[] address = new byte[args.size()];
                    for(int i = 0; i < args.size(); i++) {
                        address[i] = (byte)((int)args.get(i) & 0xFF);
                    }
                    tempDevice.setDeviceIpAddress(InetAddress.getByAddress(address));
                    break;
                case "/hub/setup/port":
                    tempDevice.setDevicePortNumber((int)message.getArguments().get(0));
                    break;
                case "/hub/setup/input0":
                    message.getArguments();
                    tempDevice.addInput(0,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
                case "/hub/setup/input1":
                    message.getArguments();
                    tempDevice.addInput(1,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
                case "/hub/setup/input2":
                    message.getArguments();
                    tempDevice.addInput(2,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
                case "/hub/setup/input3":
                    message.getArguments();
                    tempDevice.addInput(3,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
                case "/hub/setup/input4":
                    message.getArguments();
                    tempDevice.addInput(4,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
                case "/hub/setup/input5":
                    message.getArguments();
                    tempDevice.addInput(5,
                            (int)message.getArguments().get(0),
                            (int) message.getArguments().get(1),
                            (int)message.getArguments().get(2));
                    break;
            }
        }
        devices.add(tempDevice);

//        System.out.println("Number of Devices: " + devices.size());

//        System.out.println(tempDevice.getName());
//        System.out.println(tempDevice.getDeviceIpAddress().toString());
//        System.out.println(tempDevice.getDevicePortNumber());
//        for(int i = 0; i < tempDevice.getInputs().size(); i++) {
//            System.out.println(tempDevice.getInputs().get(i).getLowRange());
//            System.out.println(tempDevice.getInputs().get(i).getHighRange());
//            System.out.println(tempDevice.getInputs().get(i).getFilterWeight());
//        }

        Enumeration<NetworkInterface> iterNetwork;
        Enumeration<InetAddress> iterAddress;
        NetworkInterface network;
        InetAddress address;

        iterNetwork = NetworkInterface.getNetworkInterfaces();
        while(iterNetwork.hasMoreElements()) {
            network = iterNetwork.nextElement();
            if(!network.isUp()) {
                continue;
            }
            if(network.isLoopback()) {
                continue;
            }

            iterAddress = network.getInetAddresses();
            System.out.println(network.getName());

            // TODO: generate an arraylist of local addresses, and allow use to select appropriate one
            // (Maybe we can collect network names?)

            while(iterAddress.hasMoreElements()) {
                address = iterAddress.nextElement();
                System.out.println(address.toString());

                if (address.isSiteLocalAddress()) {
                    System.out.println("Is local.");
                    System.out.println(network.getDisplayName());
                    networks.add(new Network(network.getName(), InetAddress.getByAddress(address.getAddress()), network));
//                    localIp = address.getHostAddress();
//                    localIpBytes = address.getAddress();
                }
            }
        }
/*
        getBroadcastAddress = new Task<InetAddress>() {
            @Override
            protected InetAddress call() throws Exception {
                Enumeration<NetworkInterface> interfaces =
                        NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isLoopback())
                        continue;    // Don't want to broadcast to the loopback interface
                    for (InterfaceAddress interfaceAddress :
                            networkInterface.getInterfaceAddresses()) {
                        if (interfaceAddress.getBroadcast() != null) {
                            System.out.println(interfaceAddress.getBroadcast());
                            return interfaceAddress.getBroadcast();
                        }
                    }
                }
                return null;
            }
        };

        getBroadcastAddress.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                t -> {
                    main.this.broadcast = getBroadcastAddress.getValue();
                    System.out.println("Handler call: " + broadcast);
                    new Thread(getNetworkDevices).start();
                });
*/
        getNetworkDevices = new Task() {
            @Override
            protected Void call() throws IOException {
                OSCBundle bundle = new OSCBundle();
                OSCMessage areYouThere = new OSCMessage();
                areYouThere.setAddress("/hello");
                for(byte b : localIpBytes) {
                    areYouThere.addArgument((int)b);
                }
                bundle.addPacket(areYouThere);
                OSCPortOut sender = new OSCPortOut(InetAddress.getByName("255.255.255.255"), 9000);
                sender.send(bundle);
                sender.close();
                System.out.println("Sent.");

                receiver = new OSCPortIn(8000);

                OSCListener listener = (time, message) -> {

//                    if(message.getAddress() == "/hub/setup/name") updateName();
//                    if(message.getAddress() == "/hub/setup/ip") updateIP();
//                    if(message.getAddress() == "hub/setup/port") updatePort();
//                    if(message.get)

                    System.out.println("Incoming: " + message.getAddress() + " " + message.getArguments());
                };
                String THIS_ADDRESS = "/hub/setup/*";
                receiver.addListener(THIS_ADDRESS, listener);
                receiver.startListening();

                return null;
            }
        };

//        new Thread(getBroadcastAddress).start();
//        new Thread(getNetworkDevices).start();
        hookupEvents();


        System.out.println("Hello world");
    }

    private void closeProgram() {
        System.out.println("Closing.");
        stage.close();
    }

    private void hookupEvents() {
        final ObservableList<String> networkNames = FXCollections.observableArrayList();
        final InetAddress[] broadcastAddress = new InetAddress[1];
        for(Network net : networks) {
            networkNames.add(net.getLocalAddress().toString());
        }
        view.networkSelect.setItems(networkNames);

        view.findDevicesButton.setOnAction(e -> {
            view.searchingLabel.setVisible(true);
            view.progressBar.setVisible(true);
            System.out.println("Find Devices!");
            Network selectedNetwork = new Network();
            if(view.networkSelect.getSelectionModel().getSelectedItem() != null) {
                for (Network net : networks) {
                    if (net.getLocalAddress().toString().equals(view.networkSelect.getSelectionModel().getSelectedItem())) {
                        selectedNetwork = net;
                    }
                }
            } else {
                return;
            }

            for (InterfaceAddress interfaceAddress :
                    selectedNetwork.getNetworkInterface().getInterfaceAddresses()) {
                if (interfaceAddress.getBroadcast() != null) {
                    System.out.println(interfaceAddress.getBroadcast());
                    broadcastAddress[0] = interfaceAddress.getBroadcast();
                }
            }

            Network finalSelectedNetwork = selectedNetwork;
            NetworkOperationTask getNetworkDevices = new NetworkOperationTask(finalSelectedNetwork) {
                @Override
                protected Void call() throws IOException {
                    OSCBundle bundle = new OSCBundle();
                    OSCMessage areYouThere = new OSCMessage();
                    areYouThere.setAddress("/hello");
                    for(byte b : finalSelectedNetwork.getLocalAddress().getAddress()) {
                        areYouThere.addArgument((int)b);
                    }

                    int timeout=250;
                    for (int i=1;i<255;i++){
                        byte[] localHost = finalSelectedNetwork.getLocalAddress().getAddress();
                        String host=localHost[0] + "." + localHost[1] + "." + localHost[2] + "." + i;
                        if (InetAddress.getByName(host).isReachable(timeout)){
                            System.out.println(host + " is reachable");
                        } else {
                            System.out.println(host + " is unreachable");
                        }
                        updateProgress(i, 254);
                        updateMessage(String.valueOf(100*i/254) + "% complete");
                    }
                    System.out.println("done.");

                    /*
                    bundle.addPacket(areYouThere);
                    OSCPortOut sender = new OSCPortOut(InetAddress.getByName("10.101.1.255"), 9000);
                    sender.send(bundle);
                    sender.close();
                    System.out.println("Sent.");
*/
                    receiver = new OSCPortIn(8000);

                    OSCListener listener = (time, message) -> {

//                    if(message.getAddress() == "/hub/setup/name") updateName();
//                    if(message.getAddress() == "/hub/setup/ip") updateIP();
//                    if(message.getAddress() == "hub/setup/port") updatePort();
//                    if(message.get)

                        System.out.println("Incoming: " + message.getAddress() + " " + message.getArguments());
                    };
                    String THIS_ADDRESS = "/hub/setup/*";
                    receiver.addListener(THIS_ADDRESS, listener);
                    receiver.startListening();

                    return null;
                }
            };

            view.progressBar.progressProperty().bind(getNetworkDevices.progressProperty());
            view.progressLabel.textProperty().bind(getNetworkDevices.messageProperty());

            new Thread(getNetworkDevices).start();
            System.out.println();

        });
    }

    private void updateName() {

    }

    public abstract class NetworkOperationTask<V> extends Task<V> {
        protected Network network;

        public NetworkOperationTask(Network network) {
            this.network = network;
        }
    }
}


