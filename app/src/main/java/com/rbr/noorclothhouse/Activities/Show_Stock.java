package com.rbr.noorclothhouse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rbr.noorclothhouse.R;
import com.rbr.noorclothhouse.helper.Recycler_Adapter;
import com.rbr.noorclothhouse.helper.Stock;

import java.util.ArrayList;
import java.util.List;

public class Show_Stock extends AppCompatActivity {

    RecyclerView rv;
    List<Stock> stocklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__stock);

        //FindviewbyId
        findview();

        //Creating list of stocks
        stocklist = new ArrayList<>();
        stocklist.add(new Stock("1","rakesh","https://www.google.com/search?q=images&rlz=1C1CHBF_enIN863IN863&sxsrf=ALeKk03MNzHOfqIigAMFt-gKBrwcY1f_yw:1593856851074&tbm=isch&source=iu&ictx=1&fir=PDxUM2uh-Nz6cM%252CLlgDpz1LoiuznM%252C_&vet=1&usg=AI4_-kQCcjzkqo7IesobXHjm6gM8REv3pA&sa=X&ved=2ahUKEwiZgLm7q7PqAhUp63MBHdFfCJYQ9QEwAHoECAgQMQ#imgrc=PDxUM2uh-Nz6cM",
                "200","4"));

        stocklist.add(new Stock("2","Baskar","P",
                "500","4"));

        rv.setHasFixedSize(true);
        LinearLayoutManager layout=new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(new LinearLayoutManager(this));

        //creating recyclerview adapter
        Recycler_Adapter adapter = new Recycler_Adapter(getApplicationContext(), stocklist);

        //setting adapter to recyclerview
        rv.setAdapter(adapter);
    }

    private void findview() {
        rv=findViewById(R.id.show_stock_recycler);
    }
}
