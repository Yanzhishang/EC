package com.yq.yzs.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public class DatabaseManager {
    private DaoSession daoSession = null;
    private UserProfileDao dao = null;

    public DatabaseManager() {
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }
    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }
    private void initDao(Context context) {
        ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        dao = daoSession.getUserProfileDao();
    }

    public UserProfileDao getDao() {
        return dao;
    }

}
