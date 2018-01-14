package ai.cuddle.livy.service;

import com.cloudera.livy.Job;
import com.cloudera.livy.JobContext;
import com.cloudera.livy.shaded.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suman.das on 12/28/17.
 */
public class LivyHiveJob implements Job<List<Map<String,Object>>> {

    private String brand;

    private static Logger logger = Logger.getLogger(LivyHiveJob.class);

    private ObjectMapper mapper = new ObjectMapper();

    public LivyHiveJob(String brand){
        this.brand=brand;
    }

    public LivyHiveJob(){

    }

    private SparkSession sparkSession;

    @Override
    public List<Map<String, Object>> call(JobContext jobContext) throws Exception {
        sparkSession = jobContext.sparkSession();
        return executeJob();
    }

    private List<Map<String,Object>> executeJob(){
        if(brand!=null){
            return brandSales(brand);
        }else{
            return salesOfAllBrands();
        }
    }

    private List<Map<String,Object>> salesOfAllBrands(){
        logger.info("inside fetch salesOfAllBrands for ....");
        List<String> stringDataset= null;
        List<Map<String,Object>> result= new ArrayList<>();
        try {

            Dataset<Row> sqlDF = sparkSession.sql("SELECT tdrimssales,stcimssales,itemquantity,brand FROM test.imssales_parque where the_year=2017");
            sqlDF.createOrReplaceTempView("imssales_parque");
            System.out.println("cache -----> " + sparkSession.catalog().isCached("imssales_parque"));
            Dataset<Row> grpDataSet = sparkSession.sql("SELECT sum(tdrimssales) as tdrimssales ,sum(stcimssales) as stcimssales,sum(itemquantity) as itemquantity,brand FROM imssales_parque group by brand");

            stringDataset = grpDataSet.toJSON().collectAsList();
            logger.info("After String dataSet is populated...");
            for(String s : stringDataset){
                //Map<String,Object> map = new HashMap<>();
                //map = mapper.readValue(s, new TypeReference<Map<String, Object>>(){});
                result.add(convertToJSON(s));
            }
            logger.info("Before Returning...");

        }catch(Exception e){
            logger.error("Exception occurred during salesOfAllBrands ",e);
            e.printStackTrace();
        }

        return result;


    }


    private List<Map<String,Object>> brandSales(String brand){
        logger.info("inside fetch brandSales for ...." + brand);
        List<String> stringDataset= null;
        List<Map<String,Object>> result= new ArrayList<>();
        try {

            Dataset<Row> sqlDF = sparkSession.sql("SELECT tdrimssales,stcimssales,itemquantity,brand FROM test.imssales_parque where the_year=2017 and brand='"+brand +"'");
            sqlDF.createOrReplaceTempView("imssales_parque");
            System.out.println("cache -----> " + sparkSession.catalog().isCached("imssales_parque"));
            Dataset<Row> grpDataSet = sparkSession.sql("SELECT sum(tdrimssales) as tdrimssales ,sum(stcimssales) as stcimssales,sum(itemquantity) as itemquantity,brand FROM imssales_parque where brand='" + brand +"' group by brand");

            stringDataset = grpDataSet.toJSON().collectAsList();
            logger.info("After String dataSet is populated...");
            for(String s : stringDataset){
                //Map<String,Object> map = new HashMap<>();
                //map = mapper.readValue(s, new TypeReference<Map<String, Object>>(){});
                result.add(convertToJSON(s));
            }
            logger.info("Before Returning...");

        }catch(Exception e){
            logger.error("Exception occurred during brandSales ",e);
            e.printStackTrace();
        }

        return result;

    }

    private Map<String,Object> convertToJSON(String tempJson){
        String[] parts = tempJson.split(",");
        Map<String,Object> jsonHash = new HashMap<String,Object>();
        for(int i=0;i<parts.length;i++){
            parts[i]    =   parts[i].replace("\"", "");
            parts[i]    =   parts[i].replace("{", "");
            parts[i]    =   parts[i].replace("}", "");
            String[] subparts = parts[i].split(":");
            jsonHash.put(subparts[0],subparts[1]);
        }
        return jsonHash;
    }


}
