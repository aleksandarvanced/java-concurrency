package com.scalefocus.idgen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value="/api/v1/idgeneration")
public class IdGenerationController {

    private Map<String, Object> lockObjects = new ConcurrentHashMap<>();

    // http://localhost:8080/api/v1/idgeneration/id?domain=table1
    // curl http://localhost:8080/api/v1/idgeneration/id?domain=table1
    // https://stackoverflow.com/questions/133988/synchronizing-on-string-objects-in-java
    // artillery quick --count 5 -n 5 http://localhost:8080/api/v1/idgeneration/id?domain=table1
    // artillery quick --count 5 -n 1 http://localhost:8080/api/v1/idgeneration/id?domain=table1
    // C:\Dev\Projects\artillery-load-tests\idgen> artillery run idgen.yml

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Long getUniqueId(@RequestParam(name = "domain") String domain) throws InterruptedException {
        lockObjects.putIfAbsent(domain, new Object());

        synchronized (lockObjects.get(domain)) {
            // Actual ID Generation Logic
            if ("table1".equals(domain)) {
                int systemTimeInt = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
                BitSet id = new BitSet(52);
                BitSet unixTimeBits = BitSet.valueOf(new byte[] { (byte) systemTimeInt, (byte) (systemTimeInt >>> 8), (byte) (systemTimeInt >>> 16), (byte) (systemTimeInt >>> 24) });

                System.out.println("Sleeping for table 1");
                Thread.sleep(2000);
                System.out.println("Returning Id for table 1");
                return 1L;
            } else {
                System.out.println("Returning Id for table 2");
                return 2L;
            }
        }

        // Using Strings for Java Synchronization - not good approach
        
//        String keyObj = new String(domain);
//        String keyObj2 = new String(domain);
//        System.out.println(keyObj == keyObj2);
//
//        String key = domain;
//        String key2 = domain;
//        System.out.println(key == key2);
//
//
//        String key3 = "domain";
//
//        synchronized (keyObj) {
//            if ("table1".equals(domain)) {
//                System.out.println("Sleeping for table 1");
//                Thread.sleep(5);
//                System.out.println("Returning Id for table 1");
//                return 1L;
//            } else {
//                System.out.println("Returning Id for table 2");
//                return 2L;
//            }
//        }


    }
}
