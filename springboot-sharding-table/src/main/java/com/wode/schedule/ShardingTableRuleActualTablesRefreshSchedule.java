package com.wode.schedule;

import com.google.common.collect.Maps;
import com.wode.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:水平分表，动态分表刷新定时任务
 * @author: 许京乐
 * @date: 2020/2/29 23:47
 */
@Component
@EnableScheduling
@Slf4j
@Order(9999)//需要依赖字典配置，必须在字典启动后启动
public class ShardingTableRuleActualTablesRefreshSchedule implements InitializingBean {

    @Resource
    private DataSource dataSource;

    public ShardingTableRuleActualTablesRefreshSchedule() {
    }

    @Resource
    private JdbcTemplate jdbcTemplate;



    /**
     * 秒 分 时 日期 月份 星期 年（可选，留空）
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @Scheduled(cron = "0 50 23 L * ?")
     */
    public void actualTablesRefresh() throws NoSuchFieldException, IllegalAccessException {
        ShardingDataSource dataSource = (ShardingDataSource) this.dataSource;
        ShardingRule shardingRule = dataSource.getShardingContext().getShardingRule();
        Collection<TableRule> tableRules = shardingRule.getTableRules();

        for (TableRule tableRule : tableRules) {
            List<DataNode> dataNodes = tableRule.getActualDataNodes();
            String addTableName = "user_202108";
            DataNode dataNode = new DataNode("master", addTableName);
            dataNodes.add(dataNode);

            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
            actualDataNodesField.setAccessible(true);
            actualDataNodesField.set(tableRule, dataNodes);


            Collection<String> actualTableNames = tableRule.getActualTableNames("master");
            actualTableNames.add(addTableName);
            Field actualTables = TableRule.class.getDeclaredField("actualTables");
            actualTables.setAccessible(true);
            actualTables.set(tableRule, actualTableNames);

            Map<DataNode, Integer> dataNodeIndexMapTmp = Maps.newHashMap();
            int index = 0;
            for(Iterator var5 = actualTableNames.iterator(); var5.hasNext(); ++index) {
                String each = (String)var5.next();
                DataNode dataNodeTmp = new DataNode(each, "master");
                dataNodeIndexMapTmp.put(dataNodeTmp, index);
            }

            Field dataNodeIndexMap = TableRule.class.getDeclaredField("dataNodeIndexMap");
            dataNodeIndexMap.setAccessible(true);
            dataNodeIndexMap.set(tableRule, dataNodeIndexMapTmp);
        }

        System.out.println(tableRules);



//        if (dynamicTables.getNames() == null || dynamicTables.getNames().length == 0) {
//            log.warn("dynamic.table.names为空");
//            return;
//        }
//        for (int i = 0; i < dynamicTables.getNames().length; i++) {
//            TableRule tableRule = null;
//            try {
//                tableRule = dataSource.getShardingContext().getShardingRule().getTableRule(dynamicTables.getNames()[i]);
//                System.out.println(tableRule);
//            } catch (ShardingConfigurationException e) {
//                log.error("逻辑表：{},不存在配置！", dynamicTables.getNames()[i]);
//            }
//            List<DataNode> dataNodes = tableRule.getActualDataNodes();
//
//            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
//            actualDataNodesField.setAccessible(true);
//
//            // ！！！！！！!！默认水平分表开始时间是2019-12月，每个月新建一张新表！！！！！
//            LocalDateTime localDateTime = LocalDateTime.of(2019, 12, 1, 0, 0, new Random().nextInt(59));
//            LocalDateTime now = LocalDateTime.now();
//
//            String dataSourceName = dataNodes.get(0).getDataSourceName();
//            String logicTableName = tableRule.getLogicTable();
//            StringBuilder stringBuilder = new StringBuilder(10).append(dataSourceName).append(".").append(logicTableName);
//            final int length = stringBuilder.length();
//            List<DataNode> newDataNodes = new ArrayList<>();
//            while (true) {
//                stringBuilder.setLength(length);
//                stringBuilder.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMM")));
//                DataNode dataNode = new DataNode(stringBuilder.toString());
//                newDataNodes.add(dataNode);
//                localDateTime = localDateTime.plusMonths(1L);
//                if (localDateTime.isAfter(now)) {
//                    break;
//                }
//            }
//            actualDataNodesField.set(tableRule, newDataNodes);
//        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        actualTablesRefresh();
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");

    /**
     * 每月最后1天执行该定时任务
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void createTable() throws NoSuchFieldException, IllegalAccessException {
        Date now = DateUtils.getNow();
        Date tomorrow = DateUtils.dateToSubDays(now, 2);
        String table = "user_" + dateFormat.format(tomorrow);
        String querySql = "select * from " + table;
        try {
            jdbcTemplate.execute(querySql);
            return;
        } catch (DataAccessException e) {
            //todo 建表只允许1台主机建表，在这建立redis锁
            //开始建表逻辑
            String createSql = "CREATE TABLE " + table +
                    "(\n" +
                    "    id            bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',\n" +
                    "    city          varchar(20) not null,\n" +
                    "    name          varchar(20) not null,\n" +
                    "    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，录入时间',\n" +
                    "    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
                    "    PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            jdbcTemplate.execute(createSql);
            //刷新
            this.actualTablesRefresh();
        }


    }

}
