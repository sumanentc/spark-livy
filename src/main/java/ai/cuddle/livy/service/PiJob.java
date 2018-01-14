package ai.cuddle.livy.service;


import com.cloudera.livy.Job;
import com.cloudera.livy.JobContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suman.das on 12/27/17.
 */

public class PiJob implements Job<Double>, Function<Integer, Integer>,Function2<Integer, Integer, Integer>{

    private int samples;

    public PiJob(int samples) {
        this.samples = samples;
    }

    public PiJob(){

    }

    @Override
    public Double call(JobContext jobContext) throws Exception {
        List<Integer> sampleList = new ArrayList<Integer>();
        for (int i = 0; i < samples; i++) {
            sampleList.add(i + 1);
        }
        return 4.0d * jobContext.sc().parallelize(sampleList).map(this).reduce(this) / samples;
    }

    @Override
    public Integer call(Integer integer) throws Exception {
        double x = Math.random();
        double y = Math.random();
        return (x*x + y*y < 1) ? 1 : 0;
    }

    @Override
    public Integer call(Integer v1, Integer v2) throws Exception {
        return v1 + v2;
    }
}
