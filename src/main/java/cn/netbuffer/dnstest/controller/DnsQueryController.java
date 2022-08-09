package cn.netbuffer.dnstest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xbill.DNS.Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RestController
@RequestMapping("/dns/query")
public class DnsQueryController {

    @GetMapping("domain")
    public String domain(@RequestParam(required = false, defaultValue = "www.baidu.com") String domain) throws UnknownHostException {
        InetAddress address = Address.getByName(domain);
        log.debug("query domain {} result:{}", domain, address);
        return address.getHostAddress();
    }

}
