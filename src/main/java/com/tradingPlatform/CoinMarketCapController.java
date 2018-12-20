package com.tradingPlatform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tradingPlatform.DataObjects.CoinInfo;
import com.tradingPlatform.DataObjects.LatestCoinInfos;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@EnableDiscoveryClient
@SpringBootApplication
public class CoinMarketCapController {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "cmc");
        SpringApplication.run(CoinMarketCapController.class, args);
    }
}

@RestController
@RequestMapping("/cmc")
class cmcRestController {

    private static String[] coinSymbols = {"ETH", "BTC", "TRX", "LTC", "BCH", "MIOTA", "XRP", "XLM"};
    private static LatestCoinInfos latestCoinInfos = new LatestCoinInfos();
    private CMCRepository cmcRepository;


    public cmcRestController (CMCRepository cmcRepository) {
        this.cmcRepository = cmcRepository;
    }


    @RequestMapping("/coinInfo")
    public String coinInfo(@RequestParam(value = "coin") String coin) {
        try {
            Connection.Response response = Jsoup.connect("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol="+coin)
                    .header("X-CMC_PRO_API_KEY", "3d7072b7-f645-4b86-9d8b-1ce755042b08")
                    .ignoreContentType(true)
                    .execute();

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.body()).getAsJsonObject().get("data").getAsJsonObject().get(coin).getAsJsonObject();
            CoinInfo.Builder coinInfoBuilder = new CoinInfo.Builder();
            coinInfoBuilder.id(jsonObject.get("id").getAsInt());
            coinInfoBuilder.name(jsonObject.get("name").getAsString());
            coinInfoBuilder.symbol(jsonObject.get("symbol").getAsString());
            coinInfoBuilder.circulatingSupply(jsonObject.get("circulating_supply").getAsInt());
            coinInfoBuilder.dateAdded(jsonObject.get("date_added").getAsString());
            coinInfoBuilder.cmcRank(jsonObject.get("cmc_rank").getAsInt());
            coinInfoBuilder.currency("USD");
            coinInfoBuilder.price(jsonObject.get("quote").getAsJsonObject().get("USD").getAsJsonObject().get("price").getAsDouble());
            coinInfoBuilder.percentChange24hr(jsonObject.get("quote").getAsJsonObject().get("USD").getAsJsonObject().get("percent_change_24h").getAsDouble());

            cmcRepository.save(coinInfoBuilder.build());

            return coinInfoBuilder.build().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/coinList")
    public LatestCoinInfos coinList() {
        try {
            JsonParser parser = new JsonParser();
            for (String coinSymbol: coinSymbols) {
                Connection.Response response = Jsoup.connect("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol="+coinSymbol)
                        .header("X-CMC_PRO_API_KEY", "3d7072b7-f645-4b86-9d8b-1ce755042b08")
                        .ignoreContentType(true)
                        .execute();
                JsonObject jsonObject = parser.parse(response.body()).getAsJsonObject().get("data").getAsJsonObject().get(coinSymbol).getAsJsonObject();
                CoinInfo.Builder coinInfoBuilder = new CoinInfo.Builder();
                coinInfoBuilder.id(jsonObject.get("id").getAsInt());
                coinInfoBuilder.name(jsonObject.get("name").getAsString());
                coinInfoBuilder.symbol(jsonObject.get("symbol").getAsString());
                coinInfoBuilder.circulatingSupply(jsonObject.get("circulating_supply").getAsInt());
                coinInfoBuilder.dateAdded(jsonObject.get("date_added").getAsString());
                coinInfoBuilder.cmcRank(jsonObject.get("cmc_rank").getAsInt());
                coinInfoBuilder.currency("USD");
                coinInfoBuilder.price(jsonObject.get("quote").getAsJsonObject().get("USD").getAsJsonObject().get("price").getAsDouble());
                coinInfoBuilder.percentChange24hr(jsonObject.get("quote").getAsJsonObject().get("USD").getAsJsonObject().get("percent_change_24h").getAsDouble());
                latestCoinInfos.add(jsonObject.get("symbol").getAsString(),coinInfoBuilder.build());
            }

            return latestCoinInfos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/latestInfo")
    public String latestInfo () {
        String result="";
        latestCoinInfos.getLatestCoinInfos();
        for (CoinInfo coinInfo:latestCoinInfos) {
            result+=coinInfo.toString();
        }
        return result;
    }

    @RequestMapping("/symbol")
    public String getBySymbol(@RequestParam(value = "symbol") String symbol){
        CoinInfo coin = cmcRepository.findBySymbol(symbol);
        return coin.toString();
    }

}
