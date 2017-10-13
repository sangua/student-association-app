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
 * SQLiteOpenHelper�����ݿ⸨���࣬������������������ݿ⡣
 * �������ݿ�
 */
public class DBHelper extends SQLiteOpenHelper  //���ø��๹����
{
	public static final String TAG = "DBHelper";// ���ݿ�����
	
	private static final String DB_NAME = "orgcenter.db";// ���ű�
	
	private static final String Organization_Table = "organization";// ���ŷ����
	
	private static final String Orgtype_Table = "orgtype";// ��Ա��
	
	private static final String Member_Table = "member";// ��Ա���Ŷ��ձ�
	
	private static final String Memandorg_Table = "memandorg";// ���Ż��
	
	private static final String Action_Table = "actions";// ��Ա�����ձ�
	
	private static final String Memandaction_Table = "memandaction";
	private static final String ShowAction_Table = "showaction";
	private static final int DB_VERSION = 1;
	private static DBHelper mInstance;
	public static SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public DBHelper(Context context)   //�����ݿ��״δ���ʱִ�и÷�����һ�㽫������ȳ�ʼ���������ڸ÷�����ִ��.
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
	
	@Override               //��дonCreate����
	public void onCreate(SQLiteDatabase db)
	{
		try
		{
			initTables(db);//��ʼ���������
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log_i("��¼�����ɹ�");
	}
	
	/**
	 * ��ʼ���������
	 * 
	 * 
	 * 
	 */
	private synchronized void initTables(SQLiteDatabase db) throws Exception
	{
		// execSQL����ִ��SQL���
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
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '����');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '����');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '̨��');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '����');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( 'Ӣ��');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '��ȭ��');");
		db.execSQL("INSERT INTO " + Orgtype_Table + "(tname) VALUES ( '�����');");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
										+ ShowAction_Table
										+ " (  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  [aid] INTEGER(1) NOT NULL,  [cont] VARCHAR(200),	  [img] VARCHAR(100) NOT NULL,  [time] DATETIME NOT NULL);");
	}
	
	/**
	 * ��ȡ���õ�����
	 * 
	 * @return
	 */
	public synchronized List<Type> getOrgTypeList()
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();//create or open a  database,���ݿ���ʱֻ�ܵ���һ��ֻ���Ķ���
		                                                    //writeable ����Ե���һ���ɶ���д�Ķ���
		Cursor c = null;
		List<Type> list = new ArrayList<Type>();//�½�һ����̬����
		try
		{                            //query�������������ݿ�Ĳ��� û�н���ɸѡ������
			c = db.query(Orgtype_Table, null, null, null, null, null, null);//�����α�����ݱ���в�ѯ
			while (c.moveToNext())//���α겻ͣ����һ���ƶ��������������飬���������ArrayList��
			{
				Type type = new Type();
				type.id = c.getInt(c.getColumnIndex("id"));//ͨ���α��������õ���Ա��id��λ�ã�����getint������ȡid
				type.name = c.getString(c.getColumnIndex("tname"));//ͨ���α��������õ���Ա�İ������͵�λ�ã�
				list.add(type);//���type��                                                                      ����getsrting������ȡ��Ա�İ�������
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
	 * ���������ŷ�
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
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("omoney", money); // ĳЩ�����,
			                             // ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.update(Organization_Table, values, "id=?", new String[] { id + "" });
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ע������
	 * 
	 * @return
	 */
	public synchronized boolean insertOrg(String name, int type, String info, String phone, String pwd)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("oname", name); // ĳЩ�����,
			values.put("otype", type);// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			values.put("oinfo", info);
			values.put("ophone", phone);
			values.put("opwd", pwd);
			values.put("otime", Format.format(new Date()));
			db.insert(Organization_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ע���Ա
	 * 
	 * @return
	 */
	public synchronized boolean insertMember(String name, String user, String pwd, String hobbys)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("mname", name); // ĳЩ�����,
			values.put("muser", user);
			values.put("mpwd", pwd);
			values.put("hobby", hobbys);
			// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.insert(Member_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ע��
	 * 
	 * @return
	 */
	public synchronized boolean insertAction(String name, String time, String info, int money, int size, int oid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("aname", name); // ĳЩ�����,
			values.put("astart", time);
			values.put("ainfo", info);
			values.put("amoney", money);
			values.put("size", size);
			values.put("oid", oid);
			// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.insert(Action_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ��ӻ�ɹ�չʾ
	 * 
	 * @return
	 */
	public synchronized boolean insertShowAction(int aid, String text, String path)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("aid", aid); // ĳЩ�����,
			values.put("cont", text);
			values.put("img", path);
			values.put("time", Format.format(new Date()));
			// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.insert(ShowAction_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ������ŵĵ�¼��У�����ŵ��û���������
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
			// ���Ȳ�ѯ���URL����Ӧ��ID
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
	 * ��ȡ���µĻ
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
			// ���Ȳ�ѯ���URL����Ӧ��ID
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
	 * ��ȡ���µĻ�ɹ�
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
			// ���Ȳ�ѯ���URL����Ӧ��ID
			c = db.rawQuery(sql, new String[] { ndate });//execsql����ִ��sql���ԣ�������û�з���ֵ����Ҫʵ�ֲ�ѯ
			while (c != null && c.moveToNext())          //��Ҫʹ��rawQuery������
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
	 * ��ѯ���ŵĻ
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
			// ���Ȳ�ѯ���URL����Ӧ��ID
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
	 * ��ѯ��Ա�μӵĹ̶����ŵĻ
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
	 * ���ݻ�ԱiD��ѯ���μӵ�����
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
	 * ���ݻ�ԱiD�Ƽ�����
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
	 * ��Ա��������
	 * 
	 * @return
	 */
	public synchronized boolean AddOrg(int mid, int oid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("mid", mid); // ĳЩ�����,
			values.put("oid", oid);
			// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.insert(Memandorg_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ��Ա����
	 * 
	 * @return
	 */
	public synchronized boolean AddAction(int mid, int aid)
	{
		SQLiteDatabase db = null;
		try
		{
			db = mInstance.getWritableDatabase(); // ��ȡ���ݿ�����(��д��)
			ContentValues values = new ContentValues(); // ������Map������,
														// ����String,�����������,
														// ֵ��Object,������Ҫ���������
			values.put("mid", mid); // ĳЩ�����,
			values.put("aid", aid);
			// ���û�в��������ֵ�Ļ� Ҫ����ĳ������ db.insert("Cards", "name", values);
			db.insert(Memandaction_Table, null, values); // �ڶ����������д���е�һ����������,
			// �����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
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
	 * ����Ա�ĵ�¼,У���û��������롣
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	public synchronized int checkMemberLogin(String user, String pwd)
	{
		SQLiteDatabase db = mInstance.getReadableDatabase();//(ֻ��)
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
	 * ��������id��ѯ����
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
