package com.toillion.southernstartech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity {

    // Recycler View is the list of work items
    private RecyclerView workTypesRV;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.Adapter mAdapter;

    // TODO this is where you need in add the work items you need in the list. You can add as many as you need
    private String[] data = new String[]{"Work Type", "Another work item", "One More Item Here"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the RV to one in res/layout/activity_main.xml
        // Doing this will allow you to programmatically populate it and set up the settings
        workTypesRV = findViewById(R.id.workTypesRV);
        // The following is to set up the RV
        workTypesRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        workTypesRV.setLayoutManager(layoutManager);
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        workTypesRV.addItemDecoration(itemDecoration);
        mAdapter = new WorkTypesAdapter(data); //Pass all the data we want to use in the list here
        workTypesRV.setAdapter(mAdapter);



        // Set up what happens when you click on one of the items in the list
        ItemClickSupport.addTo(workTypesRV).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        TextView t = (TextView) v;
                        String name = t.getText().toString();
                        // set up to load the PDF View class
                        Intent intent = new Intent(getApplicationContext(), PdfView.class);
                        // Pass the name of the work type which we will use load the correct pdf
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                }
        );
    }
}
