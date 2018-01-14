package ai.cuddle.livy.config;

import com.cloudera.livy.LivyClient;
import com.cloudera.livy.LivyClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by suman.das on 12/27/17.
 */
@Configuration
public class LivyConfig {

    @Value("${livyUrl}")
    private String livyUrl;

    @Value("${jarPath}")
    private String jarPath;

    private static Logger logger = Logger.getLogger(LivyConfig.class);

    @Bean
    public LivyClient client() throws URISyntaxException,IOException {
        URI uri = new URI(livyUrl);
        Map<String,String> config = new HashMap<>();
        config.put("spark.app.name","livy-poc");
        config.put("connection.timeout", "180s");
        config.put("spark.driver.memory", "4g");
        config.put("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        config.put("spark.executor.memory", "15g");
        config.put("spark.executor.cores","10");
        //spark.executor.cores concurrent task that and executor can run thread count
        config.put("spark.executor.instances","3");

        //config.put("spark.dynamicAllocation.enabled","true");
        //config.put("spark.dynamicAllocation.minExecutors","2");
        //config.put("spark.dynamicAllocation.maxExecutors","5");
        //config.put("spark.dynamicAllocation.initialExecutors","2");
        //config.put("spark.dynamicAllocation.schedulerBacklogTimeout","300s");
        //config.put("spark.dynamicAllocation.sustainedSchedulerBacklogTimeout","60s");
        //config.put("spark.dynamicAllocation.executorIdleTimeout","300s");


        LivyClient client = new LivyClientBuilder(false).setURI(uri).setAll(config).build();
        logger.info("Uploading to the Spark context.\n"+ jarPath);
        try {
            client.addJar(new URI(jarPath)).get();
            logger.info("Upload done to the Spark context...\n"+ jarPath);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return client;

    }


}
