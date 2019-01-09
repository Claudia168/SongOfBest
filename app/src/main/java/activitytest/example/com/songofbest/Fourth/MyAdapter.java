package activitytest.example.com.songofbest.Fourth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activitytest.example.com.songofbest.R;

public class MyAdapter extends BaseAdapter {

    private List<BluetoothDevice> mList;
    private Context mContext;
    private boolean isconnect;

    public MyAdapter(List<BluetoothDevice> list,Context context){
        this.mList = list;
        this.mContext = context;
    }

    public void setIsconnect(boolean isconnect) {
        this.isconnect = isconnect;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        TextView name = view.findViewById(R.id.name);
        TextView statu = view.findViewById(R.id.statu);
        BluetoothDevice device = mList.get(position);
        name.setText(device.getName());
        if(isconnect)
            statu.setText("已连接");
        else
            statu.setText("未连接");
        return view;
    }
}
