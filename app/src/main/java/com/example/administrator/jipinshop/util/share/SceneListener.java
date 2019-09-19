package com.example.administrator.jipinshop.util.share;

import android.app.Activity;
import android.util.Log;

import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.mob.moblink.RestoreSceneListener;
import com.mob.moblink.Scene;

/**
 * @author 莫小婷
 * @create 2019/9/17
 * @Describe
 */
public class SceneListener implements RestoreSceneListener {

//    public static final Map<String, Class<? extends Activity>> PATH_MAP_LOCAL = new HashMap<>();

//    static {
//        PATH_MAP_LOCAL.put("/goods", ShoppingDetailActivity.class);
//        PATH_MAP_LOCAL.put(NOVEL_PATH, NovelReadActivity.class);
//        PATH_MAP_LOCAL.put(GAME_PATH, GameActivity.class);
//        PATH_MAP_LOCAL.put(MATCH_PATH, FriendActivity.class);
//        PATH_MAP_LOCAL.put(WAKEUP_PATH, SplashActivity.class);
//        PATH_MAP_LOCAL.put(LOCAL_INVITE_PATH, LocalInviteActivity.class);
//        PATH_MAP_LOCAL.put(SHARE_INVITE_PATH, ShareInviteActivity.class);
//    }

    @Override
    public Class<? extends Activity> willRestoreScene(Scene scene) {
//        String path = scene.getPath();
//        Map<String, Class<? extends Activity>> pathMap = PATH_MAP_LOCAL;
//        if (pathMap.keySet().contains(path)) {
//            return pathMap.get(path);
//        }
        return MainActivity.class;
    }

    @Override
    public void completeRestore(Scene scene) {

    }

    @Override
    public void notFoundScene(Scene scene) {
        Log.d("SceneListener", "没有找到");
    }

}
