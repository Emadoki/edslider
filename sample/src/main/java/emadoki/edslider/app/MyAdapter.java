package emadoki.edslider.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emadoki.edslider.Align;
import com.emadoki.edslider.EdSliderBuilder;
import com.emadoki.edslider.EdSliderManager;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>
{
    private Context context;
    private EdSliderManager manager;
    private ArrayList<String> list;

    public MyAdapter(Context context, EdSliderManager manager, ArrayList<String> list)
    {
        this.context = context;
        this.manager = manager;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_holder, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position)
    {
        holder.button.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        public Button button;

        public MyHolder(View v)
        {
            super(v);
            button = (Button) v.findViewById(R.id.button);
            button.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view)
        {
            new EdSliderBuilder(context)
                    .set(manager)
                    .on(view)
                    .setAlignment(Align.LEFT, Align.TOP)
                    .addIcon(R.drawable.ic_android)
                    .addIcon(R.drawable.ic_heart)
                    .addIcon(R.drawable.ic_camera)
                    .build().show();
            return false;
        }
    }
}
