package gu.china.com.gwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vctextview.Hot;
import vctextview.VerticalScrollTextView;

public class MainActivity extends AppCompatActivity {

    private VerticalScrollTextView vst;
    private ArrayList<Hot> newList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newList = new ArrayList<>();
        Hot hot = new Hot();
        hot.setId(String.valueOf(0));
        hot.setContent("新年快乐！");

        Hot hot1 = new Hot();
        hot1.setId(String.valueOf(1));
        hot1.setContent("英雄联盟LOL最新活动");

        Hot hot2 = new Hot();
        hot2.setId(String.valueOf(2));
        hot2.setContent("CF老兵回归！");
        vst = (VerticalScrollTextView) findViewById(R.id.vst);
        newList.add(hot);
        newList.add(hot1);
        newList.add(hot2);

        List<String> stringList = new ArrayList<>();
        for(int i = 0;i<newList.size();i++){
            stringList.add(newList.get(i).getContent());
        }
        vst.setList(stringList);
        vst.setItemOnClickListener(new VerticalScrollTextView.ItemOnClickListener() {
            @Override
            public void onClick(int position, String content) {
                Log.i("ailee","position="+position+",  Content="+newList.get(position).getContent());
            }
        });
    }
}
