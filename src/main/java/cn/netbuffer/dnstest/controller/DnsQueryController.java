package cn.netbuffer.dnstest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xbill.DNS.*;
import org.xbill.DNS.lookup.LookupSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/dns/query")
public class DnsQueryController {

    @GetMapping("a")
    public String a(@RequestParam(required = false, defaultValue = "www.baidu.com") String domain) throws UnknownHostException {
        InetAddress address = Address.getByName(domain);
        log.debug("query domain {} result:{}", domain, address);
        return address.getHostAddress();
    }

    @GetMapping("record")
    public void record(@RequestParam(required = false, defaultValue = "www.baidu.com") String domain) throws TextParseException, ExecutionException, InterruptedException {
        LookupSession lookupSession = LookupSession.defaultBuilder().build();
        Name name = Name.fromString(domain);
        lookupSession.lookupAsync(name, Type.ANY).whenComplete((answers, ex) -> {
            if (ex == null) {
                if (answers.getRecords().isEmpty()) {
                    log.error("{} has no record", domain);
                } else {
                    for (Record record : answers.getRecords()) {
                        log.debug("record={}", record);
                    }
                }
            } else {
                log.error("lookup {} error:{}", domain, ex.getMessage());
            }
        });
    }

}
