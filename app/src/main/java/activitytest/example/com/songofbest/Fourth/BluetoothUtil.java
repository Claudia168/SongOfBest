package activitytest.example.com.songofbest.Fourth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by CCCG-Ronin on 2018/3/22.
 */

public class BluetoothUtil {            //蓝牙工具类

    private static final int START_CONNECT = 0;//开始连接
    private static final int CONNECTED_SUCESSFUL = 1;//连接成功
    private static final int CONNECTED_FAIL = 2;//连接失败
    private static final int SERVICE_SUCESSFUL = 3;//有客户端连接上
    private static final int NEW_MESSAGE = 4;//有新消息传来

    public static final String START_CONNECT1 = "START_CONNECT";
    public static final String ACTION_RECEIVED_NEW_MSG = "ACTION_RECEIVED_NEW_MSG";
    public static final String CONNECTED_SUCESSFUL1 = "CONNECTED_SUCESSFUL";
    public static final String CONNECTED_FAIL1 = "CONNECTED_FAIL1";//连接失败
    public static final String SERVICE_SUCESSFUL1 = "SERVICE_SUCESSFUL1";//有客户端连接上

    private BluetoothAdapter adapter;
    private static BluetoothUtil bluetoothUtil;
    private Context context;
    private Map<String,BluetoothSocket> socketMap;
    private String uuid = "00000000-0000-1000-8000-00805F9B34FB";
    private BluetoothServerSocket serverSocket;
    private ConectThread conectThread;
    private ServiceThread serviceThread;
    private Map<String,ReadMessageThread> readMap;
    public static boolean isconnect;
    private NewMessage mNewMessage;

    private BluetoothUtil(Context context){
        this.context = context;
        socketMap = new HashMap<>();
        readMap = new HashMap<>();
    }

    public void setNewMessage(NewMessage newMessage){
        this.mNewMessage = newMessage;
    }

    public static BluetoothUtil getInstance(Context context){           //获取实例
        if(bluetoothUtil==null){
            bluetoothUtil = new BluetoothUtil(context);
        }
        return bluetoothUtil;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public BluetoothAdapter getAdapter(){
        if(adapter==null) {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            adapter = manager.getAdapter();
        }
        return adapter;
    }

    public BluetoothDevice getDevice(String address){   //通过蓝牙地址获取设备对象
        return adapter.getRemoteDevice(address);
    }

    public boolean isopen(){          //蓝牙是否开启
        return adapter.isEnabled();
    }

    public void openbluetooth(){          //打开蓝牙并且设置永久可被检测
        if(!isopen()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            context.startActivity(intent);
        }
    }

    public void alertName(String newName){
        adapter.setName(newName);
    }

    public void scanDevice(){
        if(adapter==null){
           bluetoothUtil.getAdapter();
        }
        adapter.startDiscovery();
    }

    public List<BluetoothDevice> getBondDevices(){            //获取匹配过的设备
        Set<BluetoothDevice> set = adapter.getBondedDevices();
        List<BluetoothDevice> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    public boolean isBond(BluetoothDevice device){            //是否匹配
        if(device.getBondState()==BluetoothDevice.BOND_NONE){
            return false;
        }else{
            return true;
        }
    }

    public boolean isScan(){                        //是否在搜索
        return adapter.isDiscovering();
    }

    public void stopScan(){                         //停止搜索
        adapter.cancelDiscovery();
    }

    public void bonddevice(BluetoothDevice bldevice){
        Method method = null;
        try {
            method = BluetoothDevice.class.getMethod("createBond");
            method.invoke(bldevice);
        } catch (Exception e) {}
    }

    public void removebonddevice(BluetoothDevice bldevice){
        Method method = null;
        try {
            method = BluetoothDevice.class.getMethod("removeBond");
            method.invoke(bldevice);
        } catch (Exception e) {}
    }

    private class ConectThread extends Thread{
        private String address;

        public ConectThread(String address){
            this.address = address;
        }

        @Override
        public void run() {
            BluetoothDevice device = adapter.getRemoteDevice(address);
            try {
                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
                Message msg = new Message();
                msg.what = START_CONNECT;
                msg.obj = device.getName();
                handler.sendMessage(msg);
                socket.connect();
                socketMap.put(address,socket);
                Message msg1 = new Message();
                msg1.what = CONNECTED_SUCESSFUL;
                msg1.obj = address;
                handler.sendMessage(msg1);
                ReadMessageThread mreadThread = new ReadMessageThread(address);
                readMap.put(address,mreadThread);
                mreadThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                socketMap.remove(address);
                String error = e.getMessage();
                Message msg2 = new Message();
                msg2.what = CONNECTED_FAIL;
                msg2.obj = error;
                handler.sendMessage(msg2);
            }
        }
    }

    private class ServiceThread extends Thread{

        @Override
        public void run() {
            try {
                if (adapter != null) {
                    serverSocket = adapter.listenUsingRfcommWithServiceRecord("阿标", UUID.fromString(uuid));
                    while (true) {
                        BluetoothSocket socket = serverSocket.accept();
                        socketMap.put(socket.getRemoteDevice().getAddress(), socket);
                        Message msg = new Message();
                        BluetoothDevice device = socket.getRemoteDevice();
                        msg.obj = device;
                        msg.what = SERVICE_SUCESSFUL;
                        handler.sendMessage(msg);
                        ReadMessageThread readMessageThread = new ReadMessageThread(socket.getRemoteDevice().getAddress());
                        readMap.put(socket.getRemoteDevice().getAddress(), readMessageThread);
                        readMessageThread.start();
                    }
                }
                } catch(IOException e){
                    e.printStackTrace();
                }

        }
    }

    private class ReadMessageThread extends Thread{

        private String address;

        public ReadMessageThread(String address){
            this.address = address;
        }

        @Override
        public void run() {
            InputStream is = null;
            BluetoothSocket socket = socketMap.get(address);
            try {
                is = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true){
                try {
                    ObjectInputStream objStream = new ObjectInputStream(is);
                    byte[] messageInfo = (byte[])objStream.readObject();
                    if(mNewMessage!=null){
                        mNewMessage.getMessage(messageInfo);
                    }
                    /*
                    Message msg = new Message();
                    msg.what = NEW_MESSAGE;
                    msg.obj = messageInfo;
                    handler.sendMessage(msg);
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                    closeConnection(address);
                    break;
                }

            }
        }
    }

    public void sendMessage(byte[] messageInfo,String address){
            BluetoothSocket socket = socketMap.get(address);
            if(socket==null){
                isconnect=false;
            }else{
                try {
                    isconnect = true;
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream objStream = new ObjectOutputStream(os);
                    objStream.writeObject(messageInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                    closeConnection(address);
                }
            }
    }

    public void cannect(String address){
        if(!socketMap.containsKey(address)){
            if(conectThread!=null&&conectThread.isAlive()){
                return;
            }
            conectThread = new ConectThread(address);
            conectThread.start();
        }else{
            Intent intent = new Intent();
            intent.setAction(CONNECTED_SUCESSFUL1);
            context.sendBroadcast(intent);
        }
    }

    public void startServer(){
        if(serviceThread==null){
            serviceThread = new ServiceThread();
            serviceThread.start();
        }
    }

    @SuppressLint("HandlerLeak")
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case START_CONNECT:
                    String name1 = (String)msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra("name1",name1);
                    intent.setAction(START_CONNECT1);
                    context.sendBroadcast(intent);
                    break;
                case CONNECTED_SUCESSFUL:
                    Intent intent2 = new Intent();
                    String address = (String) msg.obj;
                    intent2.putExtra("address",address);
                    intent2.setAction(CONNECTED_SUCESSFUL1);
                    context.sendBroadcast(intent2);
                    break;
                case CONNECTED_FAIL:
                    String error =(String) msg.obj;
                    Intent intent3 = new Intent();
                    intent3.putExtra("error",error);
                    intent3.setAction(CONNECTED_FAIL1);
                    context.sendBroadcast(intent3);
                    break;
                case SERVICE_SUCESSFUL:
                    BluetoothDevice device =(BluetoothDevice) msg.obj;
                    Intent intent4 = new Intent();
                    intent4.putExtra("device",device);
                    intent4.setAction(SERVICE_SUCESSFUL1);
                    context.sendBroadcast(intent4);
                    break;
                case NEW_MESSAGE:
                    if(msg.obj instanceof byte[]){
                        byte[] info = (byte[]) msg.obj;
                        Intent intent1 = new Intent();
                        intent1.putExtra("newMessage",info);
                        intent1.setAction(ACTION_RECEIVED_NEW_MSG);
                        context.sendOrderedBroadcast(intent1,null);
                    }
            }
        }
    };

    private void stopServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(serviceThread!=null){
                    serviceThread.interrupt();
                    serviceThread = null;
                }
                Set<String> keySet = socketMap.keySet();
                for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
                    String remoteDeviceAddress = iterator.next();
                    BluetoothSocket socket = socketMap.get(remoteDeviceAddress);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socketMap.remove(remoteDeviceAddress);
                    }
                    ReadMessageThread mreadThread = readMap.get(remoteDeviceAddress);
                    if (mreadThread != null) {
                        mreadThread.interrupt();
                        readMap.remove(remoteDeviceAddress);
                    }
                }
                if (serverSocket != null) {
                    try {
                        serverSocket.close();/* 关闭服务器 */
                        serverSocket = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void shutdownClient() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (conectThread!= null) {
                        conectThread.interrupt();
                        conectThread = null;
                    }
                    Set<String> keySet = socketMap.keySet();
                    for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
                        String remoteDeviceAddress = iterator.next();
                        BluetoothSocket socket = socketMap.get(remoteDeviceAddress);
                        if (socket != null) {
                            socket.close();
                            socketMap.remove(remoteDeviceAddress);
                        }
                        ReadMessageThread mreadThread = readMap.get(remoteDeviceAddress);
                        if (mreadThread != null) {
                            mreadThread.interrupt();
                            readMap.remove(remoteDeviceAddress);
                        }
                    }
                }catch (Exception e){
                    Log.d("shutdownCLient", e.getMessage());
                }
            }
        }.start();
    }

    private void closeConnection(String remoteDeviceAddress) {
        socketMap.remove(remoteDeviceAddress);
        readMap.remove(remoteDeviceAddress);
    }

    public void destory(){
        stopServer();
        shutdownClient();
    }
}
