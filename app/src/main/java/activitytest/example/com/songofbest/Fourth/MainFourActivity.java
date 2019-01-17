package activitytest.example.com.songofbest.Fourth;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import activitytest.example.com.songofbest.R;

@SuppressLint("Registered")
public class MainFourActivity extends AppCompatActivity implements View.OnClickListener,NewMessage
		,SendData {

	private Button btn_start,btn_stop,btn_sendPlay,btn_search,btn_left,btn_right,btn_liti,btn_music;
	private AudioUtil mAudioUtil;
	private String rawPath;
	private AudioTrackUtil mAudioTrack;
	private boolean played,isshowlist;
	private BluetoothUtil mBUtil;
	private ListView mListView;
	private MyAdapter myAdapter;
	private List<BluetoothDevice> devices = new ArrayList<>();
	private String address;
	private Visualizer mVisualizer;
	private Equalizer mEqualizer; // ������
	private LinearLayout mLayout;
	BaseVisualizerView mBaseVisualizerView;
	private TextView mStatusTextView;
	// ����ϵͳ���ص���������
	private BassBoost mBass;
	// ����ϵͳ��Ԥ������������
	private PresetReverb mPresetReverb;
	private List<Short> reverbNames = new ArrayList<>();
	private List<String> reverbVals = new ArrayList<>();
	private List<SeekBar> equalizeSeekbars = new ArrayList<>();
	private List<MusicBean> music_num = new ArrayList<>();
	private Music_list musicAdapter;
	private ListView musicListView;
	private MediaPlayer mediaPlayer;
	private int maxMusicVolume,maxRingVolume,currentMusicVolue,currentRingMusic;
	private AudioManager audioManager;
	private SeekBar record_seek,music_seek;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(br);
		mVisualizer.release();
		mEqualizer.release();
		mVisualizer = null;
		mEqualizer = null;
		mBass.release();
		mBass = null;
		mPresetReverb.release();
		mPresetReverb = null;
		if(mAudioUtil.isRecord()){
			mAudioUtil.stop();
		}
		if(mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.activity_main_four);

		initView();
		init();
		initListener();
//		if(!requestPremission()){
//
//		}
	}
  //歌曲列表
	private void initListener() {
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
		btn_music.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isshowlist) {
					musicListView.setVisibility(View.VISIBLE);
					isshowlist = true;
				}else{
					isshowlist = false;
					musicListView.setVisibility(View.GONE);
				}
			}
		});
		musicListView.setAdapter(musicAdapter);
		musicAdapter.setPlayListener(new Music_list.PlayListener() {
			@Override
			public void play(int position) {
				switch (position){
					case 0:
						playBefore();
						mediaPlayer = MediaPlayer.create(MainFourActivity.this, R.raw.beiduofen);
						mediaPlayer.start();
						music_num.get(1).setIsplay(false);
						music_num.get(2).setIsplay(false);
						music_num.get(3).setIsplay(false);
						music_num.get(4).setIsplay(false);
						musicAdapter.notifyDataSetChanged();
						break;
					case 1:
						playBefore();
						mediaPlayer = MediaPlayer.create(MainFourActivity.this, R.raw.meiguihua);
						mediaPlayer.start();
						music_num.get(0).setIsplay(false);
						music_num.get(2).setIsplay(false);
						music_num.get(3).setIsplay(false);
						music_num.get(4).setIsplay(false);
						musicAdapter.notifyDataSetChanged();
						break;
					case 2:
						playBefore();
						mediaPlayer = MediaPlayer.create(MainFourActivity.this, R.raw.niruochengfeng);
						mediaPlayer.start();
						music_num.get(0).setIsplay(false);
						music_num.get(1).setIsplay(false);
						music_num.get(3).setIsplay(false);
						music_num.get(4).setIsplay(false);
						musicAdapter.notifyDataSetChanged();
						break;
					case 3:
						playBefore();
						mediaPlayer = MediaPlayer.create(MainFourActivity.this, R.raw.xingzuoshushang);
						mediaPlayer.start();
						music_num.get(1).setIsplay(false);
						music_num.get(2).setIsplay(false);
						music_num.get(0).setIsplay(false);
						music_num.get(4).setIsplay(false);
						musicAdapter.notifyDataSetChanged();
						break;
					case 4:
						playBefore();
						mediaPlayer = MediaPlayer.create(MainFourActivity.this, R.raw.k);
						mediaPlayer.start();
						music_num.get(1).setIsplay(false);
						music_num.get(2).setIsplay(false);
						music_num.get(0).setIsplay(false);
						music_num.get(3).setIsplay(false);
						musicAdapter.notifyDataSetChanged();
						break;
				}
				setupVisualizerFxAndUi();
			}

			@Override
			public void stop(int position) {
				mediaPlayer.pause();
			}
		});

		maxMusicVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		music_seek.setMax(maxMusicVolume);
		record_seek.setMax(maxRingVolume);
		currentMusicVolue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		currentRingMusic = audioManager.getStreamVolume(AudioManager.STREAM_RING);
		music_seek.setProgress(currentMusicVolue);
		record_seek.setProgress(currentRingMusic);
		//音乐音量
		music_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
				currentMusicVolue = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		//录音音量
		record_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				audioManager.setStreamVolume(AudioManager.STREAM_RING,progress,0);
				currentRingMusic = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
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
						Toast.makeText(MainFourActivity.this,R.string.searched,Toast.LENGTH_SHORT).show();
						break;
					case BluetoothDevice.ACTION_FOUND:
						devices.add((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
						myAdapter.notifyDataSetChanged();
						break;
					case BluetoothUtil.CONNECTED_FAIL1:
						Toast.makeText(MainFourActivity.this,R.string.fail_connect,Toast.LENGTH_SHORT).show();
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
						Toast.makeText(MainFourActivity.this,R.string.start_connect,Toast.LENGTH_SHORT).show();
						break;
				}
			}
		}
	};

	private int maxpro;

	private void init(){
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mBUtil = BluetoothUtil.getInstance(this);
		mBUtil.getAdapter();
		if(!mBUtil.isopen()){
			mBUtil.openbluetooth();
		}
		mBUtil.startServer();
		mBUtil.setNewMessage(this);
		String[] arrs = getResources().getStringArray(R.array.music_list);
		for(int i = 0;i<arrs.length;i++){
			MusicBean musicBean = new MusicBean(i,false,arrs[i]);
			music_num.add(musicBean);
		}
		musicAdapter = new Music_list(music_num,this);
		mAudioUtil = AudioUtil.getInstance();
		mAudioTrack = AudioTrackUtil.getInstance();
		mAudioUtil.setmSendData(this);

		regerst();

		setupEqualizeFxAndUi();//均衡器

		setupPresetReverb();//音场选择

		setupBassBoost();//重低音设置
	}


	/**
	 * 音场设置
	 */
	private void setupPresetReverb()
	{
		// ��MediaPlayer��AudioSessionId����PresetReverb
		// �൱������PresetReverb������Ƹ�MediaPlayer
		mPresetReverb = new PresetReverb(0,
				mAudioUtil.getId());
		// ��������Ԥ����������
		mPresetReverb.setEnabled(true);
		TextView prTitle = new TextView(this);
		prTitle.setText(R.string.pross);
		mLayout.addView(prTitle);
		// ��ȡϵͳ֧�ֵ�����Ԥ������
		for (short i = 0; i < 7; i++)
		{
			reverbNames.add(i);
			reverbVals.add(mEqualizer.getPresetName(i));
		}
		// ʹ��Spinner��Ϊ����ѡ�񹤾�
		Spinner sp = new Spinner(this);
		sp.setAdapter(new ArrayAdapter<String>(MainFourActivity.this,
				android.R.layout.simple_spinner_item, reverbVals));
		// ΪSpinner���б���ѡ���¼����ü�����
		sp.setOnItemSelectedListener(new Spinner
				.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0
					, View arg1, final int arg2, long arg3)
			{
				// �趨����
				mPresetReverb.setPreset(PresetReverb.PRESET_LARGEHALL);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < equalizeSeekbars.size(); i++) {
							int a = 10 * (i+1+arg2);
							equalizeSeekbars.get(i).setProgress(maxpro / a);
						}
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		mLayout.addView(sp);
	}

	/**
	 * ͨ��mMediaPlayer���ص�AudioSessionId����һ�����ȼ�Ϊ0���������� ����ͨ��Ƶ��������Ӧ��UI�Ͷ�Ӧ���¼�
	 */
	/**均衡器*/
	private void setupEqualizeFxAndUi()
	{
		mEqualizer = new Equalizer(0, mAudioUtil.getId());
//		mEqualizer = new Equalizer(0, 0);
		mEqualizer.setEnabled(true);// ���þ�����

		// ͨ���������õ���֧�ֵ�Ƶ������
		short bands = mEqualizer.getNumberOfBands();

		// getBandLevelRange ��һ�����飬����һ��Ƶ�׵ȼ����飬
		// ��һ���±�Ϊ��͵��޶ȷ�Χ
		// �ڶ����±�Ϊ��������,����ȡ��
		final short minEqualizer = mEqualizer.getBandLevelRange()[0];
		final short maxEqualizer = mEqualizer.getBandLevelRange()[1];
		maxpro = maxEqualizer-minEqualizer;
		LinearLayout junhenqi = new LinearLayout(this);
		junhenqi.setOrientation(LinearLayout.HORIZONTAL);
		mLayout.addView(junhenqi);
		for (short i = 0; i < bands; i++)
		{
			final short band = i;
			LinearLayout row = new LinearLayout(this);
			row.setOrientation(LinearLayout.VERTICAL);
			row.setMinimumHeight(400);
			TextView freqTextView = new TextView(this);
			freqTextView.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));

//			freqTextView.setGravity(Gravity.CENTER_HORIZONTAL);


			freqTextView.setText((mEqualizer.getCenterFreq(band) / 1000) + "HZ");
			row.addView(freqTextView);

			TextView minDbTextView = new TextView(this);
			minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));

			minDbTextView.setText((minEqualizer / 100) + " dB");

//			TextView maxDbTextView = new TextView(this);
//			maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(
//					ViewGroup.LayoutParams.WRAP_CONTENT,
//					ViewGroup.LayoutParams.WRAP_CONTENT));
//			maxDbTextView.setText((maxEqualizer / 100) + " dB");

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			layoutParams.weight = 1;

			verSeekbar sb = new verSeekbar(this);

			sb.setLayoutParams(layoutParams);
			sb.setMax(maxpro);
			sb.setProgress(mEqualizer.getBandLevel(band));

			sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
			{

				@Override
				public void onStopTrackingTouch(SeekBar seekBar)
				{
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar)
				{
				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser)
				{
					// TODO Auto-generated method stub
					mEqualizer.setBandLevel(band,
							(short) (progress + minEqualizer));
				}
			});

			row.addView(sb);
			row.addView(minDbTextView);
//			row.addView(maxDbTextView);
			equalizeSeekbars.add(sb);
			junhenqi.addView(row);
		}

	}

	/**重低音设置*/
	private void setupBassBoost()
	{
		// ��MediaPlayer��AudioSessionId����BassBoost
		// �൱������BassBoost������Ƹ�MediaPlayer
		mBass = new BassBoost(0, mAudioUtil.getId());
		// ���������ص���Ч��
		mBass.setEnabled(true);
		// ʹ��SeekBar��Ϊ�ص����ĵ�������
		SeekBar bar = findViewById(R.id.wie_seek);
		// �ص����ķ�ΧΪ0��1000
		bar.setMax(1000);
		bar.setProgress(0);
		// ΪSeekBar���϶��¼������¼�������
		bar.setOnSeekBarChangeListener(new SeekBar
				.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar
					, int progress, boolean fromUser)
			{
				// �����ص�����ǿ��
				mBass.setStrength((short) progress);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
		});
	}
	/**
	 * ����һ��VisualizerView����ʹ��ƵƵ�׵Ĳ����ܹ���ӳ�� VisualizerView��
	 */
	private void setupVisualizerFxAndUi()
	{
		mVisualizer = new Visualizer(mediaPlayer.getAudioSessionId());
		// �����ڱ�����2��λ��
		mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

		// ���������α�ʾ�����Ҳ�����
		mBaseVisualizerView.setVisualizer(mVisualizer);
		mVisualizer.setEnabled(true);
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
		btn_left = findViewById(R.id.btn_left);
		btn_liti = findViewById(R.id.btn_liti);
		btn_right = findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
		btn_liti.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		mListView = findViewById(R.id.lsitview);
		btn_music = findViewById(R.id.btn_music);
		musicListView = findViewById(R.id.music_listview);
		mLayout = findViewById(R.id.linear);
		mBaseVisualizerView = findViewById(R.id.mybase);
		record_seek = findViewById(R.id.recoord_seek);
		music_seek = findViewById(R.id.music_seek);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if(requestCode==1){
			for(int i = 0;i<grantResults.length;i++){
				if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
					finish();
					return;
				}
			}
			init();
			initListener();
		}
	}

	private void playBefore(){
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			mediaPlayer.reset();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
  /**权限设置*/
//	private boolean requestPremission(){
//		List<String> listPers = new ArrayList<>();
//		String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//				Manifest.permission.WRITE_EXTERNAL_STORAGE,
//				Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS};
//		for(int i = 0;i<4;i++){
//			if(ContextCompat.checkSelfPermission(this,permissions[i])!=PackageManager.PERMISSION_GRANTED){
//				listPers.add(permissions[i]);
//			}
//		}
//		if(!listPers.isEmpty()){
//			String[] ps = listPers.toArray(new String[0]);
//			ActivityCompat.requestPermissions(this, ps,1);
//			return true;
//		}
//		return false;
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.search:
				if(!mBUtil.isScan()){
					mBUtil.scanDevice();
				}
				mListView.setVisibility(View.VISIBLE);
				break;
			case R.id.stop:
				if(played){
					mAudioTrack.stop();
					played = false;
				}
				if(mAudioUtil.isRecord()) {
					Toast.makeText(MainFourActivity.this,R.string.recorded,Toast.LENGTH_SHORT).show();
					mAudioUtil.stop();
				}
				break;
			case R.id.start:
				if(mAudioUtil.isRecord())
					Toast.makeText(MainFourActivity.this,R.string.recording,Toast.LENGTH_SHORT).show();
				else {
					Toast.makeText(MainFourActivity.this,R.string.start_record,Toast.LENGTH_SHORT).show();
					mAudioUtil.startRecord();
					//rawPath = mAudioUtil.getPath();
				}
				break;
			case R.id.blue_play:
				if(address==null || address.isEmpty()){
					Toast.makeText(MainFourActivity.this,R.string.no_connect,Toast.LENGTH_SHORT).show();
				}else {
					if (mAudioUtil.isRecord())
						Toast.makeText(MainFourActivity.this, R.string.recording, Toast.LENGTH_SHORT).show();
					else {
						Toast.makeText(MainFourActivity.this, R.string.start_record_send, Toast.LENGTH_SHORT).show();
						mAudioUtil.startsendRecord();
					}
				}
				break;
			case R.id.btn_left:
				mAudioUtil.playVolu(1);
				break;
			case R.id.btn_liti:
				mAudioUtil.playVolu(3);
				break;
			case R.id.btn_right:
				mAudioUtil.playVolu(2);
				break;
			default:
				break;
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