package ai.cuddle.livy.service;

import ai.cuddle.livy.entity.request.Analysis;
import ai.cuddle.livy.entity.request.AnalysisMeasure;
import org.apache.livy.Job;
import org.apache.livy.JobContext;
import org.apache.spark.sql.SparkSession;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suman.das on 1/3/18.
 */
public class AnalysisJob implements Job<List<Map<String,Object>>> {

    private Analysis analysis;

    private static Logger logger = Logger.getLogger(AnalysisJob.class);

    private SparkSession sparkSession;

    public AnalysisJob(Analysis analysis){
        this.analysis=analysis;
    }

    @Override
    public List<Map<String, Object>> call(JobContext jobContext) throws Exception {
        sparkSession = jobContext.sparkSession();
        return executeJob();
    }

    private List<Map<String,Object>> executeJob(){
        logger.info("inside analysis for ...." + analysis);
        List<String> stringDataset= null;
        List<Map<String,Object>> result= new ArrayList<>();
        for(AnalysisMeasure measure : analysis.getMeasures()){

        }
        return null;
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
