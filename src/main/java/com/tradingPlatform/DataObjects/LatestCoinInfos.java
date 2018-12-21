package com.tradingPlatform.DataObjects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LatestCoinInfos implements Iterable<CoinInfo>, Serializable {

    private static HashMap<String,CoinInfo> latestCoinInfos = new HashMap<>(8);

    public LatestCoinInfos () {}

    public void add (String key, CoinInfo value) {
        latestCoinInfos.putIfAbsent(key, value);
    }

    public void remove (String key) {
        latestCoinInfos.remove(key);
    }

    public CoinInfo get (String key) {
        return latestCoinInfos.get(key);
    }

    @Override
    public Iterator<CoinInfo> iterator() {
        return latestCoinInfos.values().iterator();
    }

    public void getLatestCoinInfos () {
        final String uri = "http://localhost:8761/cmc/coinList";
        RestTemplate restTemplate = new RestTemplate();
        try {
            LatestCoinInfos result = restTemplate.getForObject(uri, LatestCoinInfos.class);
            for (CoinInfo coinInfo : result) {
                latestCoinInfos.put(coinInfo.getSymbol(), coinInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String str="";
        for(CoinInfo coinInfo:latestCoinInfos.values()) {
            str+=coinInfo.getSymbol()+"\n"+coinInfo.getPrice()+"\n";
        }
        return str;
    }

}
