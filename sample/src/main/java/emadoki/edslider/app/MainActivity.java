package emadoki.edslider.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.emadoki.edslider.Align;
import com.emadoki.edslider.EdSliderBuilder;
import com.emadoki.edslider.EdSliderManager;
import com.emadoki.edslider.OnSliderSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private EdSliderManager manager;

    private RecyclerView recyclerView;
    private ArrayList<String> list;

    private LinearLayout layoutTest;
    private AppCompatRadioButton radioLeft, radioRight, radioCenter, radioTop, radioBottom;
    private Button button;
    private Align alignHorizontal, alignVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = new EdSliderManager(new SelectedListener());
        list = new ArrayList<String>();

        for (int i = 0; i < 6; i ++)
        {
            list.add("Button " + i);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(
                new MyAdapter(MainActivity.this, manager, list));

        layoutTest = (LinearLayout) findViewById(R.id.layoutTest);
        radioLeft = (AppCompatRadioButton) findViewById(R.id.radioLeft);
        radioRight = (AppCompatRadioButton) findViewById(R.id.radioRight);
        radioCenter = (AppCompatRadioButton) findViewById(R.id.radioCenter);
        radioTop = (AppCompatRadioButton) findViewById(R.id.radioTop);
        radioBottom = (AppCompatRadioButton) findViewById(R.id.radioBottom);
        button = (Button) findViewById(R.id.button);

        CheckChangedListener radioChangedListener = new CheckChangedListener();
        radioLeft.setOnCheckedChangeListener(radioChangedListener);
        radioRight.setOnCheckedChangeListener(radioChangedListener);
        radioCenter.setOnCheckedChangeListener(radioChangedListener);
        radioTop.setOnCheckedChangeListener(radioChangedListener);
        radioBottom.setOnCheckedChangeListener(radioChangedListener);

        button.setOnLongClickListener(new LongClickListener());
        alignHorizontal = Align.LEFT;
        alignVertical = Align.TOP;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        if (manager.dispatched(event))
            return false;

        return super.dispatchTouchEvent(event);
    }

    private class SelectedListener implements OnSliderSelectedListener
    {
        @Override
        public void OnSelected(int index)
        {
            Toast.makeText(MainActivity.this, "selected: " + String.valueOf(index), Toast.LENGTH_SHORT).show();
        }
    }

    private class CheckChangedListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean bool)
        {
            switch (view.getId()){
                case R.id.radioLeft:
                    radioLeft.setChecked(bool);
                    if (bool)
                        alignHorizontal = Align.LEFT;
                    break;
                case R.id.radioRight:
                    radioRight.setChecked(bool);
                    if (bool)
                        alignHorizontal = Align.RIGHT;
                    break;
                case R.id.radioCenter:
                    radioCenter.setChecked(bool);
                    if (bool)
                        alignHorizontal = Align.CENTER;
                    break;
                case R.id.radioTop:
                    radioTop.setChecked(bool);
                    if (bool)
                        alignVertical = Align.TOP;
                    break;
                case R.id.radioBottom:
                    radioBottom.setChecked(bool);
                    if (bool)
                        alignVertical = Align.BOTTOM;
                    break;
                default:
                    break;
            }
        }
    }

    private class LongClickListener implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View view)
        {
            new EdSliderBuilder(MainActivity.this)
                    .set(manager)
                    .on(view)
                    .setAlignment(alignHorizontal, alignVertical)
                    .addIcon(R.drawable.ic_android)
                    .addIcon(R.drawable.ic_heart)
                    .addIcon(R.drawable.ic_camera)
                    .build()
                    .show();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.changeview:
                if (recyclerView.getVisibility() == View.VISIBLE)
                {
                    recyclerView.setVisibility(View.GONE);
                    layoutTest.setVisibility(View.VISIBLE);
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    layoutTest.setVisibility(View.GONE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
