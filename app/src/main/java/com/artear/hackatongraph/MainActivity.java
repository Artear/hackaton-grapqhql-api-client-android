package com.artear.hackatongraph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.artear.hackatongraph.model.Block;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.graph_list);

        Block[] blocks = new Block[5];
        for (int i = 0; i < 5; i++) {
            blocks[i] = new Block(i+1,"Nombre:" + i);
        }
        list.setAdapter(new GraphAdapter(this, R.layout.block, blocks));
    }
}
