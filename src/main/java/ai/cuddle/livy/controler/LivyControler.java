package ai.cuddle.livy.controler;

import ai.cuddle.livy.entity.CacheDetails;
import ai.cuddle.livy.entity.Response;
import ai.cuddle.livy.service.CacheJob;
import ai.cuddle.livy.service.LivyHiveJob;
import ai.cuddle.livy.service.PiJob;
import com.cloudera.livy.LivyClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by suman.das on 12/27/17.
 */
@RestController
@RequestMapping("api")
public class LivyControler {

    @Value("${livyUrl}")
    private String livyUrl;

    @Value("${jarPath}")
    private String jarPath;

    @Autowired
    LivyClient client;


    private static Logger logger = Logger.getLogger(LivyControler.class);

    @RequestMapping("/{pi}")
    public ResponseEntity<Object> findPi(@PathVariable("pi") Integer samples) throws IOException,URISyntaxException{
        logger.info("Before submitting spark job...");
        Double res=null;
        System.err.printf("Running PiJob with %d samples...\n", samples);
        try {
            res = client.submit(new PiJob(samples)).get();
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        logger.info("Pi is roughly: " + res);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("/sales/{brand}")
    public ResponseEntity<Response> brandSales(@PathVariable("brand") String brand) {
        logger.info("Before submitting brandSales job...");
        List<Map<String,Object>> result = new ArrayList<>();
        Response resp = new Response();
        resp.setCode(200);
        resp.setMessage("Request completed successfully");
        try {
            //result = client.submit(new LivyHiveJob(brand)).get();
            result = client.run(new LivyHiveJob(brand)).get(5, TimeUnit.MINUTES);
        }catch (ExecutionException | InterruptedException | TimeoutException e){
            e.printStackTrace();
            resp.setCode(500);
            resp.setMessage(e.getMessage());
        }
        resp.setResult(result);
        logger.info("Before returning result " + result);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @RequestMapping("/sales")
    public ResponseEntity<Response> sales() {
        logger.info("Before submitting sales job...");
        List<Map<String,Object>> result = new ArrayList<>();
        Response resp = new Response();
        resp.setCode(200);
        resp.setMessage("Request completed successfully");
        try {
            result = client.run(new LivyHiveJob()).get(5, TimeUnit.MINUTES);
        }catch (ExecutionException | InterruptedException | TimeoutException e){
            e.printStackTrace();
            resp.setCode(500);
            resp.setMessage(e.getMessage());
        }
        resp.setResult(result);
        logger.info("Before returning result " + result);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @RequestMapping(value="/cache",method = RequestMethod.POST)
    public ResponseEntity<String> cache(@RequestBody CacheDetails cacheDetails){
        logger.info("Before submitting cache job...");
        String result="";
        try {
            result = client.submit(new CacheJob(cacheDetails)).get();
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        logger.info("Before returning result " + result);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }



    @PreDestroy
    public void shutdownHook(){
        logger.info("Inside shutdownHook...");
        client.stop(true);
    }
}
