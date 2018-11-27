package org.simon.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 23:05
 * @Description:TODO
 */
public class JsqlParserTest {

    public static void main(String[] args) {
        String selectSql = "SELECT user_name,age,email FROM t_user WHERE user_id > ?" + " group by age order by user_name";
        try {
            parseSQL(selectSql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public static void parseSQL(String sql) throws JSQLParserException {
        //CCJSqlParserUtil 是JsqlParser 中比较重要的工具类，它会解析SQL 语句并返回Statement 对
//象， Statement 对象可用于导航描述SQL 语句的结构
        Statement statement = CCJSqlParserUtil.parse(sql);
        System.out.println('\n' + sql);
        if (statement instanceof Select) { // 检测被解析的SQL 语句是否为select 语句
            Select select = (Select) statement;
            parseSelect(select);

        } else if (statement instanceof Update) {//检测被解析的SQL 语句是否为update 语句
            Update update = (Update) statement;
            //parseUpdate(update) ;
        } else if (statement instanceof Insert) { //检测被解析的SQL 语句是否为update 语句
            Insert insert = (Insert) statement;
            //parseInsert (insert) ;
        } else if (statement instanceof Delete) { //检测被解析的SQL 语句是否为delete 语句
            Delete delete = (Delete) statement;
            //parseDelete(delete);
        }
    }


    public static void parseSelect(Select select) {
//获取select 语句查询的列
        System.out.println("\n 列名: ");
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectitems = plain.getSelectItems();
        if (selectitems != null) { //输出列名
            for (int i = 0; i < selectitems.size(); i++) {
                SelectItem selectItem = selectitems.get(i);
                System.out.print(selectItem.toString() + "  ");
            }
        }
//解析Select 语句中的表名
        System.out.print("\n表名: ");
        TablesNamesFinder tablesNarnesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNarnesFinder.getTableList(select);
        for (String tableName : tableList) {
            System.out.println(tableName);
        }
//解析SQL 语句中Where 部分
        Expression whereExpression = plain.getWhere();
        System.out.println("\nWhere 部分：" + whereExpression.toString());


        System.out.println("\ngroup by部分的列名: ");
        List<Expression> groupByColumnReferences = plain.getGroupByColumnReferences();
        if (groupByColumnReferences != null) {
            for (int i = 0; i < groupByColumnReferences.size(); i++) {
                System.out.println(groupByColumnReferences.get(i).toString() + "  ");
            }
        }
        System.out.println("\norder by部分的列名: ");
        List<OrderByElement> orderByElements = plain.getOrderByElements();
        if (orderByElements != null) {
            for (int i = 0; i < orderByElements.size(); i++) {
                System.out.println(orderByElements.get(i).toString() + "  ");
            }
        }
    }


    public String getPageSql(String sql, int offset, int limit) {
        sql = sql.trim();
//记录当前select t吾句是否包含” for update ，， 子句，该子句会对数据行加锁
        boolean hasForUpdate = false;
        String forUpdatePart = "for update";
        if (sql.toLowerCase().endsWith(forUpdatePart)) {
//将当前SQL t吾句的” for update ”片段删除
            sql = sql.substring(0, sql.length() - forUpdatePart.length());
            hasForUpdate = true;
        }
        StringBuffer result = new StringBuffer(sql.length() + 100);
        result.append(sql).append(" limit ");
        //根据offset值拼接支持分页的SQL语句
        if (offset > 0) {
            result.append(offset).append(",").append(limit);
        } else {
            result.append(limit);
        }
        if (hasForUpdate) {
            result.append(" for update");

        }
        return result.toString();
    }
}
