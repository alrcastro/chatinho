package com.quickblox.sample.chat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Toast;

import com.quickblox.module.chat.QBChatService;
import com.quickblox.sample.chat.R;
import com.quickblox.sample.chat.ui.fragments.RoomsFragment;
import com.quickblox.sample.chat.ui.fragments.UsersFragment;
import com.viewpagerindicator.TabPageIndicator;

import org.jivesoftware.smack.ConnectionListener;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends FragmentActivity  {

    private static final int AUTHENTICATION_REQUEST = 1;
    private static final int POSITION_USER = 0;
    private static final int POSITION_ROOM = 1;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private Action lastAction;
    private ConnectionListener connectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

      //  final ActionBar actionBar = getSupportActionBar();
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        List<Fragment> tabs = new ArrayList<Fragment>();
        tabs.add(UsersFragment.getInstance());
        tabs.add(RoomsFragment.getInstance());

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabs);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
 
        
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
     
    }
    /*
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int position = tab.getPosition();
        QBUser qbUser = ((App) getApplication()).getQbUser();
        if (qbUser != null) {
            viewPager.setCurrentItem(position);
        } else if (position == POSITION_ROOM) {
            lastAction = Action.ROOM_LIST;
            showAuthenticateDialog();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
   */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (lastAction) {
                case CHAT:
                    ((UsersFragment) sectionsPagerAdapter.getItem(POSITION_USER)).startChat();
                    break;
                case ROOM_LIST:
                    viewPager.setCurrentItem(POSITION_ROOM);
                    break;
            }
            connectionListener = new ChatConnectionListener();
            QBChatService.getInstance().addConnectionListener(connectionListener);
            ((RoomsFragment) sectionsPagerAdapter.getItem(POSITION_ROOM)).loadRooms();
        } else {
            showUsersFragment();
        }
    }

    private void showUsersFragment() {
        viewPager.setCurrentItem(POSITION_USER);
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    public void showAuthenticateDialog() {
     /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Authorize first");
        builder.setItems(new String[]{"Login", "Register"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(MainChatActivity.this, LoginActivity.class);
                        startActivityForResult(intent, AUTHENTICATION_REQUEST);
                        break;
                    case 1:
                        intent = new Intent(MainChatActivity.this, RegistrationActivity.class);
                        startActivityForResult(intent, AUTHENTICATION_REQUEST);
                        break;
                }
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
               // showUsersFragment();
            }
        });
        builder.show();
        */
    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainChatActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static enum Action {CHAT, ROOM_LIST}

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case POSITION_USER:
                    return "Users";
                case POSITION_ROOM:
                    return "Rooms";
            }
            return null;
        }
    }

    private class ChatConnectionListener implements ConnectionListener {

        @Override
        public void connectionClosed() {
            showToast("connectionClosed");
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            showToast("connectionClosed on error" + e.getLocalizedMessage());
        }

        @Override
        public void reconnectingIn(int i) {

        }

        @Override
        public void reconnectionSuccessful() {

        }

        @Override
        public void reconnectionFailed(Exception e) {

        }
    }
}
