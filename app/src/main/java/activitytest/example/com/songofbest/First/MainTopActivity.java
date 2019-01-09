package activitytest.example.com.songofbest.First;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import activitytest.example.com.songofbest.R;

public class MainTopActivity extends AppCompatActivity implements MyViewPager.OnViewPagerTouchListener,ViewPager.OnPageChangeListener {

   private static final String TAG="MainTopActivity";
   private MyViewPager mLoopPager;
   private LoopAdapter mLoopAdapter;
   private static List<Integer> sPics = new ArrayList<>();
   static {
   sPics.add(R.mipmap.ic_top3);
   sPics.add(R.mipmap.ic_top2);
   sPics.add(R.mipmap.ic_top1);
   //sPics.add(R.mipmap.ic_top3);
}
private Handler mHandler;
   private boolean mIsTouch=false;
   private LinearLayout mPointContatiner;

   @Override
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main_top);
       initView();
       mHandler =new Handler();
   }
   @Override
    public void onAttachedToWindow(){
       super.onAttachedToWindow();
       mHandler.post(mLooperTask);
   }
   @Override
    public void onDetachedFromWindow(){
       super.onDetachedFromWindow();
       Log.d(TAG,"onDetachedFromWindow");
       mHandler.removeCallbacks(mLooperTask);
   }
   private Runnable mLooperTask=new Runnable() {
       @Override
       public void run() {
           if (!mIsTouch){
               int currentItem = mLoopPager.getCurrentItem();
               mLoopPager.setCurrentItem(++currentItem,true);
           }
           mHandler.postDelayed(this,3000);
       }
   };
   private void initView(){
       mLoopPager = (MyViewPager)this.findViewById(R.id.viewPager);
       mLoopAdapter=new LoopAdapter();
       mLoopAdapter.setData(sPics);
       mLoopPager.setAdapter(mLoopAdapter);
       mLoopPager.setOnViewPagerTouchListener(this);
       mLoopPager.addOnPageChangeListener(this);
       mPointContatiner=(LinearLayout) this.findViewById(R.id.lineLayout_dot);
       insertPoint();
       mLoopPager.setCurrentItem(mLoopAdapter.getDataRealSize()*100,false);
   }
   private void insertPoint(){
       for (int i=0;i<sPics.size();i++){
           View point = new View(this);
           LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35,35);
           point.setBackground(getResources().getDrawable(R.drawable.ic_dot_normal));
           layoutParams.leftMargin=5;
           point.setLayoutParams(layoutParams);
           mPointContatiner.addView(point);
       }
   }
   public void onPagerTouch(boolean isTouch){
       mIsTouch=isTouch;
   }
   public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){

   }
   public void onPageSelected(int position){
       int realPosition;
       if (mLoopAdapter.getDataRealSize()!=0){
           realPosition = position%mLoopAdapter.getDataRealSize();
       }else {
           realPosition=0;
       }
       setSelectPoint(realPosition);
   }
   private void setSelectPoint(int realPosition){
       for(int i=0;i<mPointContatiner.getChildCount();i++){
           View point = mPointContatiner.getChildAt(i);
           if (i!=realPosition){
               point.setBackgroundResource(R.drawable.ic_dot_normal);
           }else {
               point.setBackgroundResource(R.drawable.ic_dot_focused);
           }
       }
   }
   public void onPageScrollStateChanged(int state){

   }
    }


