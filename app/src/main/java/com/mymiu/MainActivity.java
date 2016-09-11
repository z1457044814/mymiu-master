package com.mymiu;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mymiu.fragment.FoundFragment;
import com.mymiu.fragment.FriendFragment;
import com.mymiu.fragment.MainFragment;
import com.mymiu.handler.FloatbtnAnimHandler;
import com.mymiu.model.PlayerAction;
import com.mymiu.model.PlayerBuff;
import com.mymiu.myview.CircleImageView;
import com.mymiu.myview.mpager.AdvancedPagerSlidingTabStrip;
import com.mymiu.service.MPlayerService;
import com.mymiu.utils.ImageBlur;
import com.mymiu.utils.ViewAnim;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView logo;


    private MainFragment mainFragment;
    private FriendFragment friendFragment;
    private FoundFragment foundFragment;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private AdvancedPagerSlidingTabStrip mPagerTab;
    private ViewPager mViewPager;

    private int index = 0;//循环类型
    private static final int VIEW_FIRST = 0;
    private static final int VIEW_SECOND = 1;
    private static final int VIEW_THIRD = 2;

    private static final int VIEW_SIZE = 3;

    private CircleImageView floatMenu;
    private CircleImageView pause;
    //音乐进度条
    private ProgressBar progressBar;

    private MPlayerService.PlayerBinder playerBinder;
    private MusicApp musicApp;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //application对象
        musicApp = (MusicApp) getApplication();
        //playerBinder对象
        playerBinder = musicApp.getPlayerBinder();
        mediaPlayer=playerBinder.getMediaPlayer();
        mPagerTab = (AdvancedPagerSlidingTabStrip) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar=(ProgressBar)findViewById(R.id.main_music_progress);
       // initProgressBar();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        logo = (ImageView) findViewById(R.id.logo);
        //创建动画
        ViewAnim.createAnim(logo);
        //设置背景墙
        //图片名 模糊度 需要设置背景的控件
        ImageBlur imageBlur = new ImageBlur();
        imageBlur.imageFactory(getApplicationContext(), "backg.png", 10, navigationView);
        //设置nav的头部布局 并获取布局的view 设置其中控件
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        CircleImageView header = (CircleImageView) view.findViewById(R.id.imageView);
        TextView username = (TextView) view.findViewById(R.id.username);
        header.setImageResource(R.drawable.default_head);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1, null);
            }
        });
        navigationView.setBottom(2);
        init();
        createFloatMenu();
        Intent it = new Intent(this, MPlayerService.class);
        startService(it);
        registerReceiver();
    }

    private void registerReceiver(){
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(PlayerAction.INTENT_ACTION_MAXTIME_NOWMUSIC);
        filter.addAction(PlayerAction.INTENT_ACTION_NOWTIME);
        registerReceiver(new PlayerBroadcastReceiver(),filter);
    }

    /**
     * 创建悬浮按钮
     */
    private void createFloatMenu() {
        View view = LayoutInflater.from(this).inflate(R.layout.sub_float_view, null);
        final CircleImageView loop = (CircleImageView) view.findViewById(R.id.loop);
        CircleImageView last = (CircleImageView) view.findViewById(R.id.last);
        pause = (CircleImageView) view.findViewById(R.id.pause);
        CircleImageView next = (CircleImageView) view.findViewById(R.id.next);
        CircleImageView menu = (CircleImageView) view.findViewById(R.id.menu);
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        floatMenu = (CircleImageView) findViewById(R.id.float_menu);
        floatMenu.setAnimation(operatingAnim);
        operatingAnim.start();
        SubActionButton.Builder subBuilder = new SubActionButton.Builder(this);
        FloatingActionMenu circleMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(view)
                .attachTo(floatMenu)
                .setRadius(getResources().getDimensionPixelOffset(R.dimen.float_menu_btn_radius))
                .setAnimationHandler(new FloatbtnAnimHandler())
                .build();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                   playerBinder.pause();
                    pause.setImageResource(R.drawable.playing_button);
                } else {
                   playerBinder.resume();
                    pause.setImageResource(R.drawable.pause_button);
                }
            }
        });
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index > 2) {
                    index = 0;
                }
                if (index == 0) {
                    loop.setImageResource(R.drawable.repeat);
                    playerBinder.setState(PlayerBuff.STATE_REPEAT);
                    Toast.makeText(MainActivity.this,"列表循环",Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    loop.setImageResource(R.drawable.single);
                    playerBinder.setState(PlayerBuff.STATE_SINGLE);
                    Toast.makeText(MainActivity.this,"单曲循环",Toast.LENGTH_SHORT).show();
                } else if (index == 2) {
                    playerBinder.setState(PlayerBuff.STATE_SHUFFL);
                    loop.setImageResource(R.drawable.shuffl);
                    Toast.makeText(MainActivity.this,"随机播放",Toast.LENGTH_SHORT).show();
                }
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playerBinder.last();
                pause.setImageResource(R.drawable.pause_button);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playerBinder.next();
                pause.setImageResource(R.drawable.pause_button);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
           // super.onBackPressed();
        }

    }

    @Override
    protected void onRestart() {
        if (mediaPlayer.isPlaying()) {
            pause.setImageResource(R.drawable.pause_button);
        } else {
            pause.setImageResource(R.drawable.playing_button);
        }
        super.onRestart();

    }

    @Override
    protected void onResume() {
        registerReceiver();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        musicApp.unbindMainService();
        musicApp.stopMainService();
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_users) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_message) {
            Intent intent = new Intent(this, NavSubActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", "message");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_download) {
            Intent intent = new Intent(this, NavSubActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", "download");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(this, NavSubActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", "favorite");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, NavSubActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", "set");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        overridePendingTransition(R.anim.from_right, R.anim.out_left);
        return true;
    }

    private void init() {
        mViewPager.setOffscreenPageLimit(VIEW_SIZE);
        mViewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
        mPagerTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(VIEW_FIRST);
        mPagerTab.showDot(VIEW_SECOND);
    }


    //处理返回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MainFragmentAdapter extends FragmentStatePagerAdapter implements AdvancedPagerSlidingTabStrip.ViewTabProvider {

        public MainFragmentAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        if (null == mainFragment)
                            mainFragment = new MainFragment();
                        mainFragment.setToolbar(toolbar);
                        //mainFragment.setLayout(mPagerTab);
                        return mainFragment;

                    case VIEW_SECOND:
                        if (null == friendFragment)
                            friendFragment = new FriendFragment();
                        return friendFragment;

                    case VIEW_THIRD:
                        if (null == foundFragment)
                            foundFragment = new FoundFragment();
                        return foundFragment;

                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public View onSelectIconView(int position, View view, ViewGroup parent) {
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(60, 60));
                view = imageView;
            }
            imageView = (ImageView) view;
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        imageView.setImageResource(R.drawable.mymusic_p);
                        break;
                    case VIEW_SECOND:
                        imageView.setImageResource(R.drawable.yuefriend_p);
                        break;
                    case VIEW_THIRD:
                        imageView.setImageResource(R.drawable.found_p);
                        break;
                    default:
                        break;
                }
            }
            return imageView;
        }

        @Override
        public View onIconView(int position, View view, ViewGroup parent) {
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(60, 60));
                view = imageView;
            }
            imageView = (ImageView) view;
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        imageView.setImageResource(R.drawable.mymusic_n);
                        break;
                    case VIEW_SECOND:
                        imageView.setImageResource(R.drawable.yuefriend_n);
                        break;
                    case VIEW_THIRD:
                        imageView.setImageResource(R.drawable.found_n);
                        break;
                    default:
                        break;
                }
            }
            return imageView;
        }

        @Override
        public String getPageIconText(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        return getResources().getString(R.string.my);
                    case VIEW_SECOND:
                        return getResources().getString(R.string.friend);
                    case VIEW_THIRD:
                        return getResources().getString(R.string.found);
                    default:
                        break;
                }
            }
            return "";
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }
    }

    public class PlayerBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(PlayerAction.INTENT_ACTION_MAXTIME_NOWMUSIC)){
                Bundle bundle =intent.getExtras();
                int max=bundle.getInt("max");
                String name=bundle.getString("name");
                progressBar.setMax(max);
           //    Log.i("println", name);
             //   Log.i("println", "max" + max);
            }
            if(intent.getAction().equals(PlayerAction.INTENT_ACTION_NOWTIME)){
                progressBar.setProgress(intent.getIntExtra("nowtime", 0));
              //  Log.i("println", "now" + intent.getIntExtra("nowtime", 0));
            }
        }
    }

}

