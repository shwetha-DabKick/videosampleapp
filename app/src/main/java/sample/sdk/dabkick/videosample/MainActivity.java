package sample.sdk.dabkick.videosample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dabkick.videosdk.publicsettings.DabKick;
import com.dabkick.videosdk.publicsettings.DabKickVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_videos)
    RecyclerView recyclerView;

    private ArrayList<String> videoCategories = new ArrayList<>(Arrays.asList("Favorite Videos"));
    private Map<String, ArrayList<DabKickVideo>> videoHolder = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        videoHolder.put(videoCategories.get(0), Util.getDailyMailVideos());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(this, Util.getDailyMailVideos(),
                new VideoRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DabKickVideo videoInfo) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        WatchVideoFragment frag = WatchVideoFragment.newInstance(videoInfo);
                        fragmentTransaction.add(R.id.frag_container, frag, WatchVideoFragment.TAG);
                        fragmentTransaction.commit();
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_none:
                DabKick.setMediaProvider(Util.createDabKickProvider(null));
                DabKick.watchWithFriends(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        WatchVideoFragment watchVideoFragment = (WatchVideoFragment) fragmentManager.findFragmentByTag(WatchVideoFragment.TAG);
        if (watchVideoFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(watchVideoFragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }

    }
}
