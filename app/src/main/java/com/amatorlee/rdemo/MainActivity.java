package com.amatorlee.rdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        for (int i = 0; i < 30; i++) {
            mDatas.add("这是第" + i+ "个item");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter(this, mDatas);
        adapter.setListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(MainActivity.this, pos + "点击了", Toast.LENGTH_SHORT).show();
            }
        });
        View view = getLayoutInflater().inflate(R.layout.layout_headview, null);
        adapter.addFootView(view);
        adapter.addHeadView(view);
        recyclerView.setAdapter(adapter);

    }
}
