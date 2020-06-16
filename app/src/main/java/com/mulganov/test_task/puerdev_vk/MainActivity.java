package com.mulganov.test_task.puerdev_vk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mulganov.test_task.puerdev_vk.java.BoxAdapter;
import com.mulganov.test_task.puerdev_vk.kt.vk.VKUser;
import com.mulganov.test_task.puerdev_vk.kt.vk.friends.VKFriendsRequest;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiExecutionException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<VKScope> a = new ArrayList<>();

        a.add(VKScope.WALL);
        a.add(VKScope.PHOTOS);

        VK.initialize(this);

        activity = this;

        if (!VK.isLoggedIn())
            VK.login(this, a);
        else
            getFriends();

    }

    public void getFriends(){
        VK.execute(
                new VKFriendsRequest()
                ,
                new VKApiCallback<List<? extends VKUser>>() {
                    @Override
                    public void success(List<? extends VKUser> vkUsers) {
                        System.out.println("success");
                        final BoxAdapter adapter = new BoxAdapter(getApplicationContext(), (List<VKUser>) vkUsers);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

                                recyclerView.setHasFixedSize(true);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);

                                recyclerView.setAdapter(adapter);
                            }
                        });

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void fail(@NotNull final VKApiExecutionException e) {
                        System.out.println("fail");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(), "fail: " + e.getErrorMsg(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        VKAuthCallback callback = new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                getFriends();
            }

            @Override
            public void onLoginFailed(int i) {

            }
        };


        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
