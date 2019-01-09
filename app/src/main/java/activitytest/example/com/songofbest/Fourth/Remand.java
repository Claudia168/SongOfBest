package activitytest.example.com.songofbest.Fourth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import activitytest.example.com.songofbest.R;

public class Remand extends AppCompatActivity implements View.OnClickListener,NewMessage
        ,SendData {

    private Button btn_start,btn_stop,btn_sendPlay,btn_search;
    private AudioUtil mAudioUtil;
    private String rawPath;
    private AudioTrackUtil mAudioTrack;
    private boolean played;
    private BluetoothUtil mBUtil;
    private ListView mListView;
    private MyAdapter myAdapter;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_four);
        requestPremission();
        mAudioUtil = AudioUtil.getInstance();
        mAudioTrack = AudioTrackUtil.getInstance();
        mAudioUtil.setmSendData(this);
        initView();
        init();
        regerst();
    }

    private void regerst() {
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothUtil.CONNECTED_FAIL1);
        filter.addAction(BluetoothUtil.CONNECTED_SUCESSFUL1);
        filter.addAction(BluetoothUtil.SERVICE_SUCESSFUL1);
        filter.addAction(BluetoothUtil.START_CONNECT1);
        registerReceiver(br,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(br);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action!=null){
                switch (action){
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        devices.clear();
                        myAdapter.notifyDataSetChanged();
                        break;
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                        Toast.makeText(Remand.this,"搜索结束",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothDevice.ACTION_FOUND:
                        devices.add((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                        myAdapter.notifyDataSetChanged();
                        break;
                    case BluetoothUtil.CONNECTED_FAIL1:
                        Toast.makeText(Remand.this,"连接失败",Toast.LENGTH_SHORT).show();
                        myAdapter.setIsconnect(false);
                        break;
                    case BluetoothUtil.CONNECTED_SUCESSFUL1:
                        address = intent.getStringExtra("address");
                        myAdapter.setIsconnect(true);
                        break;
                    case BluetoothUtil.SERVICE_SUCESSFUL1:
                        BluetoothDevice device = intent.getParcelableExtra("device");
                        devices.add(device);
                        myAdapter.setIsconnect(true);
                        break;
                    case BluetoothUtil.START_CONNECT1:
                        Toast.makeText(Remand.this,"开始连接",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    private void init(){
        mBUtil = BluetoothUtil.getInstance(this);
        mBUtil.getAdapter();
        if(!mBUtil.isopen()){
            mBUtil.openbluetooth();
        }
        mBUtil.startServer();
        mBUtil.setNewMessage(this);
    }

    private void initView(){
        btn_sendPlay = findViewById(R.id.blue_play);
        btn_sendPlay.setOnClickListener(this);
        btn_search = findViewById(R.id.search);
        btn_search.setOnClickListener(this);
        btn_start = findViewById(R.id.start);
        btn_start.setOnClickListener(this);
        btn_stop = findViewById(R.id.stop);
        btn_stop.setOnClickListener(this);
        mListView = findViewById(R.id.lsitview);
        myAdapter = new MyAdapter(devices,this);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = devices.get(position);
                if(device.getBondState() == BluetoothDevice.BOND_NONE){
                    mBUtil.bonddevice(device);
                }else if(device.getBondState() == BluetoothDevice.BOND_BONDED){
                    mBUtil.cannect(device.getAddress());
                }
            }
        });
    }

    private void requestPremission(){
        List<String> listPers = new ArrayList<>();
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        for(int i = 0;i<3;i++){
            if(ContextCompat.checkSelfPermission(this,permissions[i])!=PackageManager.PERMISSION_GRANTED){
                listPers.add(permissions[i]);
            }
        }
        if(!listPers.isEmpty()){
            String[] ps = listPers.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, ps,1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                if(!mBUtil.isScan()){
                    mBUtil.scanDevice();
                }
                /*
                if(rawPath!=null && !rawPath.isEmpty() && !played) {
                    played = true;
                    mAudioTrack.play(rawPath);
                }
                */
                break;
            case R.id.stop:
                if(played){
                    mAudioTrack.stop();
                    played = false;
                }
                if(mAudioUtil.isRecord()) {
                    Toast.makeText(Remand.this,"结束录音",Toast.LENGTH_SHORT).show();
                    mAudioUtil.stop();
                }
                break;
            case R.id.start:
                if(mAudioUtil.isRecord())
                    Toast.makeText(Remand.this,"正在录音中...",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(Remand.this,"开始录音",Toast.LENGTH_SHORT).show();
                    mAudioUtil.startRecord();
                    //rawPath = mAudioUtil.getPath();
                }
                break;
            case R.id.blue_play:
                if(address==null || address.isEmpty()){
                    Toast.makeText(Remand.this,"请先连接设备",Toast.LENGTH_SHORT).show();
                }else {
                    if (mAudioUtil.isRecord())
                        Toast.makeText(Remand.this, "正在录音中...", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(Remand.this, "开始录音发送", Toast.LENGTH_SHORT).show();
                        mAudioUtil.startsendRecord();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAudioUtil.isRecord()){
            mAudioUtil.stop();
        }
    }

    @Override
    public void getMessage(byte[] datas) {
        if(!played){
            played = true;
        }
        mAudioTrack.play(datas);
    }

    @Override
    public void sendDatas(byte[] bytes) {
        if(address!=null){
            mBUtil.sendMessage(bytes,address);
        }
    }
}
