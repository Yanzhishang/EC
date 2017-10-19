package com.yq.yzs.latte.ec.sign;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yq.yzs.latte.core.app.AccountManager;
import com.yq.yzs.latte.core.app.Latte;
import com.yq.yzs.latte.ec.database.DatabaseManager;
import com.yq.yzs.latte.ec.database.UserProfile;
import com.yq.yzs.latte.ec.database.UserProfileDao;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public class SignHandler {
    // 登录
    public static void onSignIn(String name, ISignListener signListener) {
        //从数据库中查询，拿到唯一的用户名
        UserProfile userProfile = DatabaseManager.getInstance().getDao().queryBuilder().where(UserProfileDao.Properties.Name.eq(name)).unique();
        if (userProfile == null) {
            Toast.makeText(Latte.getApplicationContext(), "用户不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        // 找到已经注册过的用户
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();


    }

    // 注册
    public static void onSignUp(String response, ISignListener signListener) {
        JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        long userId = profileJson.getLong("userId");
        String name = profileJson.getString("name");
        String avatar = profileJson.getString("avatar");
        String gender = profileJson.getString("gender");
        String address = profileJson.getString("address");
        // 把数据写入数据库中
        UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(profile);

        // 注册成功后进行回调
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();

    }
}
