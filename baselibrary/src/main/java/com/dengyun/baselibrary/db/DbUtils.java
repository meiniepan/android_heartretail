package com.dengyun.baselibrary.db;

import android.content.ContentValues;
import android.database.Cursor;

import org.litepal.FluentQuery;
import org.litepal.LitePal;

import java.util.Collection;
import java.util.List;

/**
 * author : JunDao
 * date   : 2019/8/5 15:43
 * desc   :对数据库CRUD方法工具
 */
public class DbUtils {
    /**
     * 保存数据
     *
     * @param data 如果数据是新记录，则在数据库中创建，否则现有记录得到更新。
     * @param <T>
     * @return 如果模型保存成功，返回true。任何异常发生，返回false。
     *
     * <pre>
     * 	Person person = new Person();
     * 	person.setName("Tom");
     * 	person.setAge(22);
     * 	DbUtils.save(person);
     * </pre>
     */
    public static <T extends DbBaseBean> boolean save(T data) {
        if (data == null) return false;
        return data.save();
    }

    /**
     * 保存集合
     *
     * @param collection
     * @param <T>
     */
    public static <T extends DbBaseBean> void saveAll(Collection<T> collection) {
        LitePal.saveAll(collection);
    }

    /**
     * 如果条件数据不存在，则保存模型;如果条件数据存在，则更新匹配的模型
     *
     * <pre>
     *  Person person = new Person();
     *  person.setName("Tom");
     *  person.setAge(22);
     *  DbUtils.saveOrUpdate(person,"name = ?", "Tom");
     *  </pre>
     * If person table doesn't have a name with Tom, a new record gets created in the database,
     * otherwise all records which names are Tom will be updated.
     *
     * @param data
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T extends DbBaseBean> boolean saveOrUpdate(T data, String... conditions) {
        return data.saveOrUpdate(conditions);
    }


    /**
     * 更新数据
     * <p>
     * ContentValues values = new ContentValues();
     * values.put("title","test1");//title 列名
     * DbUtils.update(News.class,values,1);//1 是更新列的id
     *
     * @param modelClass
     * @param values
     * @param id
     * @return
     */
    public static int update(Class<?> modelClass, ContentValues values, long id) {
        return LitePal.update(modelClass, values, id);
    }

    /**
     * 更新数据
     * <p>
     * ContentValues values = new ContentValues();
     * values.put("title","test2");
     * DbUtils.updateAll(News.class, values, "title = ? and commentcount > ?", "test1", "0")
     *
     * @param modelClass
     * @param values
     * @param conditions
     * @return
     */
    public static int updateAll(Class<?> modelClass, ContentValues values, String... conditions) {
        return LitePal.updateAll(modelClass, values, conditions);
    }

    /**
     * News news = new News();
     * news.setName("lijun");
     * news.setAge(18);
     * DbUtils.update(news,1);
     *
     * @param data
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends DbBaseBean> int update(T data, long id) {
        return data.update(id);
    }

    /**
     * News updateNews = new News();
     * updateNews.setTitle("test1");
     * updateNews.updateAll("title = ? and commentcount > ?", "test0", "0");
     *
     * @param data
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T extends DbBaseBean> int updateAll(T data, String... conditions) {
        return data.updateAll(conditions);
    }


    /**
     * 恢复默认值
     *
     * @param data       模型数据
     * @param fieldName  回复默认值的字段
     * @param conditions 筛选条件，不传则为fieldName字段全部恢复默认值
     * @param <T>
     */
    public static <T extends DbBaseBean> void setToDefault(T data, String fieldName, String... conditions) {
        data.setToDefault(fieldName);
        data.updateAll(conditions);

    }


    public static int getId(DbBaseBean data) {
        if (data == null) return -1;
        if (data.isSaved()) {
            return data.getId();
        } else {
            return -1;
        }
    }

    /**
     * 通过id删除数据库中的记录。记录引用的其他表中的数据也将被删除。
     *
     * @param modelClass 按类删除哪个表。
     * @param id         删除哪条记录。
     * @return 受影响的行数。包括级联删除行。
     */
    public static int delete(Class<?> modelClass, long id) {
        return LitePal.delete(modelClass, id);
    }

    /**
     * 删除所有记录，如果它们匹配提供的一组条件，则给出详细信息。该方法构造一个SQL DELETE语句并将其发送到数据库。
     *
     * <pre>
     * DbUtils.deleteAll(Person.class, "name = ? and age = ?", "Tom", "14");
     * </pre>
     * <p>
     * This means that all the records which name is Tom and age is 14 will be
     * removed.<br>
     *
     * @param modelClass Which table to delete from by class.
     * @param conditions A string array representing the WHERE part of an SQL
     *                   statement. First parameter is the WHERE clause to apply when
     *                   deleting. The way of specifying place holders is to insert one
     *                   or more question marks in the SQL. The first question mark is
     *                   replaced by the second element of the array, the next question
     *                   mark by the third, and so on. Passing empty string will update
     *                   all rows.
     * @return The number of rows affected.
     */
    public static int deleteAll(Class<?> modelClass, String... conditions) {
        return LitePal.deleteAll(modelClass, conditions);
    }

    /**
     * 删除数据库中的记录。记录必须已经保存。记录引用的其他表中的数据也将被删除。
     *
     * <pre>
     * Person person;
     * ....
     * DbUtils.delete(person);
     * </pre>
     *
     * @return 受影响的行数。包括级联删除行。
     * 如果记录没有被持久化则返回 -1
     */
    public static <T extends DbBaseBean> int delete(T data) {
        if (data.isSaved()) {
            return data.delete();
        }
        return -1;
    }


    /**
     * 查询第一个
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> T findFirst(Class<T> modelClass) {
        return LitePal.findFirst(modelClass);
    }

    /**
     * 查询最后一个
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> T findLast(Class<T> modelClass) {
        return LitePal.findLast(modelClass);
    }

    /**
     * 通过id数组查找多个记录。注意：考虑到效率，默认情况下不会加载关联的模型
     *
     * <pre>
     * List<Person> people = DbUtils.findAll(Person.class, 1, 2, 3);
     *
     * long[] bookIds = { 10, 18 };
     * List<Book> books = DbUtils.findAll(Book.class, bookIds);
     * </pre>
     * <p>
     * Of course you can find all records by passing nothing to the ids parameter.
     *
     * <pre>
     * List<Book> allBooks = DbUtils.findAll(Book.class);
     * </pre>
     * <p>
     * Note that the associated models won't be loaded by default considering the efficiency
     *
     * @param modelClass Which table to query and the object type to return as a list.
     * @param ids        Which records to query. Or do not pass it to find all records.
     * @return An object list with found data from database, or an empty list.
     */
    public static <T> List<T> findAll(Class<T> modelClass, long... ids) {
        return LitePal.findAll(modelClass, ids);
    }

    public static Cursor findBySQL(String... sql) {
        return LitePal.findBySQL(sql);
    }

    /**
     * ------------------按条件连缀查询------------------------
     */


    //查询news表，条件为commentcount >0 ，只要 title content列的数据 ，降序 ，limit(10) 前10条 ，offset(10)偏移量10 即11-20条数据
//    List<News> newsList3 = LitePal.select("title", "content")
//            .where("commentcount > ?", "0")
//            .order("publishdate desc").limit(10).offset(10)
//            .find(News.class);
    public static FluentQuery select(String... columns) {
        return LitePal.select(columns);
    }

    public static FluentQuery where(String... conditions) {
        return LitePal.where(conditions);
    }

    public static FluentQuery order(String column) {
        return LitePal.order(column);
    }

    public static FluentQuery limit(int value) {
        return LitePal.limit(value);
    }

    public static FluentQuery offset(int value) {
        return LitePal.offset(value);
    }

}
