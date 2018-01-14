package ai.cuddle.livy.service;

import ai.cuddle.livy.entity.CacheDetails;
import com.cloudera.livy.Job;
import com.cloudera.livy.JobContext;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;

/**
 * Created by suman.das on 12/29/17.
 */
public class CacheJob implements Job<String> {

    private  SparkSession sparkSession;

    private CacheDetails cacheDetails;

    private static Logger logger = Logger.getLogger(CacheJob.class);

    public CacheJob(){

    }

    public CacheJob(CacheDetails cacheDetails){
        this.cacheDetails=cacheDetails;
    }
    @Override
    public String call(JobContext jobContext) throws Exception {
        sparkSession = jobContext.sparkSession();
        return executeJob();
    }

    private String executeJob(){
        String response = "Nothing Done!";
        if(cacheDetails!= null && cacheDetails.getTableName()!=null){
            if(cacheDetails.isClearCache()){
                clearCache();
                response="Cache Cleared";
            }else{
                buildCache(cacheDetails.getTableName());
                response="Caching Done";
            }
        }
        return response;
    }

    private void buildCache(String tableName){
        Dataset<Row> sqlDF = sparkSession.sql("SELECT tdrimssales,stcimssales,itemquantity,brand FROM " + tableName +" where the_year=2017");
        //sqlDF.coalesce(3);
        sqlDF.persist(StorageLevel.MEMORY_AND_DISK());
        sqlDF.createOrReplaceTempView("imssales_parque");
        String cacheTable ="cache table imssales_parque" ;
        sparkSession.sql(cacheTable);

        logger.info("Caching done for " + tableName);


    }

    private void clearCache(){
        sparkSession.catalog().clearCache();
    }
}
