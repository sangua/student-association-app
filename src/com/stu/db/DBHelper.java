package com.stu.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stu.bean.Action;
import com.stu.bean.ActionResult;
import com.stu.bean.Organization;
import com.stu.bean.Type;

/*
 * SQLiteOpenHelper：数据库辅助类，用来帮助创建或打开数据库。
 * 社团数据库
 */
public class DBHelper extends SQLiteOpenHelper  //调用父类构造器
{
	public static final String TAG = "DBHelper";// 数据库名称
	
	private static final String DB_NAME = "orgcenter.db";// 社团表
	
	private static final String Organization_Table = "organization";// 社团分类表
	
	private static final String Orgtype_Table = "orgtype";// 会员表
	
	private static final String Member_Table = "member";// 会员社团对照表
	
	private static final String Memandorg_Table = "memandorg";// 社团活动表
	
	private static final String Action_Table = "actions";// 会员与活动对照表
	
	private static final String Memandaction_Table = "memandaction";
	private static final String ShowAction_Table = "showaction";
	private static final int DB_VERSION = 1;
	private static DBHelper mInstance;
	public static SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public DBHelper(Context context)   //当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	
	public synchronized static DBHelper getInstance(Context context)
	{
		if (mInstance == null)//DBHelper mInstance
		{
			mInstance = new DBHelper(context);
		}
		return mInstance;
	}
	
	@Override               //重写onCreate方法
	public void onCreate(SQLiteDatabase db)
	{
		try
		{
			initTables(db);//初始化表的数据
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log_i("记录表创建成功");
	}
	
	/**
	 * 初始化表的数据
	 * 
	 * 
	 * 
	 */
	private synchronized void initTables(SQLiteDatabase db) throws Exception
	{
		// execSQL用于执行SQL语句
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Action_Table
										+ " (  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [aname] VARCHAR(20) NOT NULL,  [astart] DATETIME NOT NULL,  [ainfo] VARCHAR(100) NOT NULL, "
										+ " [amoney] INTEGER NOT NULL DEFAULT 0,  [state] INTEGER(3) NOT NULL DEFAULT 0,  [oid] INTEGER NOT NULL, [size] INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Memandaction_Table
										+ " (  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [aid] INTEGER NOT NULL,  [mid] INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Memandorg_Table
										+ "(  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [mid] INTEGER(10) NOT NULL,  [oid] INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Member_Table
										+ " (  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [mname] VARCHAR(20) NOT NULL,  [muser] VARCHAR(32) NOT NULL,  [mpwd] VARCHAR(32) NOT NULL,  [hobby] VARCHAR(20) NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Organization_Table
										+ "(  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [oname] VARCHAR(20) NOT NULL,  [otype] INTEGER NOT NULL,  [otime] DATETIME NOT NULL,  [oinfo] VARCHAR(100) NOT NULL, "
										+ " [omoney] INTEGER DEFAULT 0,  [opwd] VARCHAR(32) NOT NULL,  [ophone] VARCHAR(20) NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ Orgtype_Table
										+ "(  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [tname] VARCHAR(20) NOT NULL);");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '篮球');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '吉他');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '台球');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '武术');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '英语');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '跆拳道');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '计算机');");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ ShowAction_Table
										+ " (  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [aid] INTEGER(1) NOT NULL,  [cont] VARCHAR(200),	  [img] VARCHAR(100) NOT NULL,  [time] DATETIME NOT NULL);");
	}
	
	/**
	 * 获取爱好的类型
	 * 
	 * @return
	 */
	public synchronized List<Type> getOrgTypeList()
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();//create or open a  database,数据库满时只能调出一个只读的对象
		                                                    //writeable 则可以调出一个可读可写的对象。
		Cursor c = null;
		List<Type> list = new ArrayList<Type>();//新建一个动态数组
		try
		{                            //query方法来进行数据库的查找 没有进行筛选和排序
			c = db.query(Orgtype_Table, null, null, null, null, null, null);//利用游标对数据表进行查询
			while (c.moveToNext())//用游标不停向下一行移动来遍历整个数组，将结果存入ArrayList中
			{
				Type type = new Type();
				type.id = c.getInt(c.getColumnIndex("id"));//通过游标列索引得到会员的id的位置，再由getint函数获取id
				type.name = c.getString(c.getColumnIndex("tname"));//通过游标列索引得到会员的爱好类型的位置，
				list.add(type);//添加type列                                                                      再由getsrting函数获取会员的爱好类型
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 更新社团团费
	 * 
	 * @param id
	 * @param money
	 * @return
	 */
	public synchronized boolean updateOrgMoney(int id, int money)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("omoney", money); // 某些情况下,
			                             // 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.update(Organization_Table, values, "id=?", new String[] { id + "" });
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 注册社团
	 * 
	 * @return
	 */
	public synchronized boolean insertOrg(String name, int type, String info, String phone, String pwd)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("oname", name); // 某些情况下,
			values.put("otype", type);// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			values.put("oinfo", info);
			values.put("ophone", phone);
			values.put("opwd", pwd);
			values.put("otime", Format.format(new Date()));
			db.insert(Organization_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 注册会员
	 * 
	 * @return
	 */
	public synchronized boolean insertMember(String name, String user, String pwd, String hobbys)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("mname", name); // 某些情况下,
			values.put("muser", user);
			values.put("mpwd", pwd);
			values.put("hobby", hobbys);
			// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.insert(Member_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 注册活动
	 * 
	 * @return
	 */
	public synchronized boolean insertAction(String name, String time, String info, int money, int size, int oid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("aname", name); // 某些情况下,
			values.put("astart", time);
			values.put("ainfo", info);
			values.put("amoney", money);
			values.put("size", size);
			values.put("oid", oid);
			// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.insert(Action_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 添加活动成功展示
	 * 
	 * @return
	 */
	public synchronized boolean insertShowAction(int aid, String text, String path)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("aid", aid); // 某些情况下,
			values.put("cont", text);
			values.put("img", path);
			values.put("time", Format.format(new Date()));
			// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.insert(ShowAction_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 检查社团的登录，校验社团的用户名和密码
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	public synchronized int checkOrgLogin(String user, String pwd)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		try
		{
			// 首先查询这个URL所对应的ID
			c = db.query(Organization_Table, null, "ophone=?  and opwd=?", new String[] { user, pwd }, null, null, null, null);
			if (c != null && c.moveToNext())
			{
				return c.getInt(c.getColumnIndex("id"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return -1;
	}
	
	/**
	 * 获取最新的活动
	 * 
	 * @return
	 */
	public synchronized List<Action> getNewActions()
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<Action> list = new ArrayList<Action>();
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String ndate = dateFormat.format(new Date());
			String sql = "select " + Action_Table + ".*," + Organization_Table + ".oname from "
											+ Action_Table + "," + Organization_Table
											+ " where astart>? and " + Organization_Table + ".id="
											+ Action_Table + ".oid";
			// 首先查询这个URL所对应的ID
			c = db.rawQuery(sql, new String[] { ndate });
			while (c != null && c.moveToNext())
			{
				Action action = new Action();
				action.id = c.getInt(c.getColumnIndex("id"));
				action.name = c.getString(c.getColumnIndex("aname"));
				action.time = c.getString(c.getColumnIndex("astart"));
				action.info = c.getString(c.getColumnIndex("ainfo"));
				action.money = c.getInt(c.getColumnIndex("amoney"));
				action.size = c.getInt(c.getColumnIndex("size"));
				action.state = c.getInt(c.getColumnIndex("state"));
				action.org = c.getString(c.getColumnIndex("oname"));
				list.add(action);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 获取最新的活动成果
	 * 
	 * @return
	 */
	public synchronized List<ActionResult> getNewActionResults()
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<ActionResult> list = new ArrayList<ActionResult>();
		try
		{
			String ndate = Format.format(new Date());
			String sql = "select " + Action_Table + ".*," + ShowAction_Table + ".* from "
											+ Action_Table + "," + ShowAction_Table
											+ " where time<? and " + ShowAction_Table + ".aid="
											+ Action_Table + ".id order by time desc";
			// 首先查询这个URL所对应的ID
			c = db.rawQuery(sql, new String[] { ndate });//execsql可以执行sql语言，但是它没有返回值，想要实现查询
			while (c != null && c.moveToNext())          //就要使用rawQuery方法。
			{
				ActionResult action = new ActionResult();
				action.id = c.getInt(c.getColumnIndex("id"));
				action.aname = c.getString(c.getColumnIndex("aname"));
				action.time = c.getString(c.getColumnIndex("astart"));
				action.text = c.getString(c.getColumnIndex("cont"));
				action.img = c.getString(c.getColumnIndex("img"));
				list.add(action);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 查询社团的活动
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<Action> getActionsById(int id)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<Action> list = new ArrayList<Action>();
		try
		{
			// 首先查询这个URL所对应的ID
			c = db.query(Action_Table, null, "oid=?", new String[] { id + "" }, null, null, null, null);
			while (c != null && c.moveToNext())
			{
				Action action = new Action();
				action.id = c.getInt(c.getColumnIndex("id"));
				action.name = c.getString(c.getColumnIndex("aname"));
				action.time = c.getString(c.getColumnIndex("astart"));
				action.info = c.getString(c.getColumnIndex("ainfo"));
				action.money = c.getInt(c.getColumnIndex("amoney"));
				action.size = c.getInt(c.getColumnIndex("size"));
				action.state = c.getInt(c.getColumnIndex("state"));
				list.add(action);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 查询会员参加的固定社团的活动
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<Action> getActionsByOidandMid(int oid, int mid)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<Action> list = new ArrayList<Action>();
		try
		{
			String sql = "select " + Action_Table + ".* from " + Action_Table + ","
											+ Memandaction_Table + "," + Organization_Table
											+ " where     " + Memandaction_Table + ".mid=? and "
											+ Memandaction_Table + ".aid=" + Action_Table
											+ ".id and " + Organization_Table + ".id="
											+ Action_Table + ".oid and " + Action_Table + ".oid=? "
											+ "group by " + Action_Table + ".id  ";
			c = db.rawQuery(sql, new String[] { "" + mid, "" + oid });
			while (c != null && c.moveToNext())
			{
				Action action = new Action();
				action.id = c.getInt(c.getColumnIndex("id"));
				action.name = c.getString(c.getColumnIndex("aname"));
				action.time = c.getString(c.getColumnIndex("astart"));
				action.info = c.getString(c.getColumnIndex("ainfo"));
				action.money = c.getInt(c.getColumnIndex("amoney"));
				action.size = c.getInt(c.getColumnIndex("size"));
				action.state = c.getInt(c.getColumnIndex("state"));
				list.add(action);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 根据会员iD查询所参加的社团
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<Organization> getOrganizationsByUid(int id)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<Organization> list = new ArrayList<Organization>();
		try
		{
			String sql = "select " + Organization_Table + ".* from " + Organization_Table + ","
											+ Memandorg_Table + "  where " + Memandorg_Table
											+ ".oid=" + Organization_Table + ".id  and "
											+ Memandorg_Table + ".mid=?";
			c = db.rawQuery(sql, new String[] { id + "" });
			while (c != null && c.moveToNext())
			{
				Organization organization = new Organization();
				organization.id = c.getInt(c.getColumnIndex("id"));
				organization.name = c.getString(c.getColumnIndex("oname"));
				organization.time = c.getString(c.getColumnIndex("otime"));
				organization.info = c.getString(c.getColumnIndex("oinfo"));
				organization.type = c.getInt(c.getColumnIndex("otype"));
				list.add(organization);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 根据会员iD推荐社团
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<Organization> getFavOrgsByUid(int id)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		List<Organization> list = new ArrayList<Organization>();
		try
		{
			c = db.query(Member_Table, null, "id=?", new String[] { id + "" }, null, null, null, null);
			String hobby = "";
			if (c != null && c.moveToNext())
			{
				hobby = c.getString(c.getColumnIndex("hobby"));
			}
			String[] hobbys = hobby.split(",");
			StringBuffer sb = new StringBuffer();
			sb.append("select * from " + Organization_Table + "  where ");
			List<String> list2 = new ArrayList<String>();
			for (int i = 0; i < hobbys.length; i++)
			{
				sb.append(" otype like ? or");
				list2.add("%" + hobbys[i] + "%");
			}
			sb.delete(sb.length() - 2, sb.length());
			String[] selectionArgs = new String[list2.size()];
			list2.toArray(selectionArgs);
			c = db.rawQuery(sb.toString(), selectionArgs);
			while (c != null && c.moveToNext())
			{
				Organization organization = new Organization();
				organization.id = c.getInt(c.getColumnIndex("id"));
				organization.name = c.getString(c.getColumnIndex("oname"));
				organization.time = c.getString(c.getColumnIndex("otime"));
				organization.info = c.getString(c.getColumnIndex("oinfo"));
				organization.type = c.getInt(c.getColumnIndex("otype"));
				list.add(organization);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return list;
	}
	
	/**
	 * 会员加入社团
	 * 
	 * @return
	 */
	public synchronized boolean AddOrg(int mid, int oid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("mid", mid); // 某些情况下,
			values.put("oid", oid);
			// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.insert(Memandorg_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 会员加入活动
	 * 
	 * @return
	 */
	public synchronized boolean AddAction(int mid, int aid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // 获取数据库连接(可写的)
			ContentValues values = new ContentValues(); // 类似于Map的容器,
														// 键是String,用来存放列名,
														// 值是Object,用来存要插入的数据
			values.put("mid", mid); // 某些情况下,
			values.put("aid", aid);
			// 如果没有参数插入空值的话 要加上某个列名 db.insert("Cards", "name", values);
			db.insert(Memandaction_Table, null, values); // 第二个参数随便写表中的一个列名即可,
			// 用来在想插入一条除了主键全部为空的记录时使用
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (db != null)
				db.close();
		}
		return true;
	}
	
	/**
	 * 检查会员的登录,校验用户名与密码。
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	public synchronized int checkMemberLogin(String user, String pwd)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();//(只读)
		Cursor c = null;
		try
		{
			c = db.query(Member_Table, null, "muser=?  and mpwd=?", new String[] { user, pwd }, null, null, null, null);
			if (c != null && c.moveToNext())
			{
				return c.getInt(c.getColumnIndex("id"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return -1;
	}
	
	/**
	 * 根据社团id查询社团
	 * 
	 * @param id
	 * @return
	 */
	public synchronized Organization getOrgById(int id)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();
		Cursor c = null;
		try
		{
			c = db.query(Organization_Table, null, "id=? ", new String[] { id + "" }, null, null, null, null);
			if (c != null && c.moveToNext())
			{
				Organization organization = new Organization();
				organization.id = c.getInt(c.getColumnIndex("id"));
				organization.name = c.getString(c.getColumnIndex("oname"));
				organization.info = c.getString(c.getColumnIndex("oinfo"));
				organization.type = c.getInt(c.getColumnIndex("otype"));
				organization.time = c.getString(c.getColumnIndex("otime"));
				return organization;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
				c.close();
			if (db != null)
				db.close();
		}
		return null;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
	}
	
	public void log_e(String e)
	{
		Log.e(TAG, e);
	}
	
	public void log_i(String s)
	{
		Log.i(TAG, s);
	}
}
