package com.tradingPlatform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tradingPlatform.Repositories.CMCRepository;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/coinInfo")
    public CoinInfo coinInfo(@RequestParam(value = "coin") String coin) {
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

            return coinInfoBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/coinList")
    public String coinList() {
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
                cmcRepository.save(coinInfoBuilder.build());
            }
            return latestCoinInfos.toString();
        } catch (IOException e) {
            e.printStackTrace();
            coinInfoObjects();
            return latestCoinInfos.toString();
        }
    }

    public void coinInfoObjects() {
        CoinInfo.Builder coinInfoBTC = new CoinInfo.Builder();
        coinInfoBTC.symbol("BTC").price(3855.15832672).id(1);
        latestCoinInfos.add("BTC", coinInfoBTC.build());
        cmcRepository.save(coinInfoBTC.build());

        CoinInfo.Builder coinInfoETH = new CoinInfo.Builder();
        coinInfoETH.symbol("ETH").price(154.212772221).id(1027);
        latestCoinInfos.add("ETH", coinInfoETH.build());
        cmcRepository.save(coinInfoETH.build());

        CoinInfo.Builder coinInfoTRX = new CoinInfo.Builder();
        coinInfoTRX.symbol("TRX").price(0.0216297193366).id(1958);
        latestCoinInfos.add("TRX", coinInfoTRX.build());
        cmcRepository.save(coinInfoTRX.build());

        CoinInfo.Builder coinInfoMIOTA = new CoinInfo.Builder();
        coinInfoMIOTA.symbol("MIOTA").price(0.376894697331).id(1720);
        latestCoinInfos.add("MIOTA", coinInfoMIOTA.build());
        cmcRepository.save(coinInfoMIOTA.build());

        CoinInfo.Builder coinInfoLTC = new CoinInfo.Builder();
        coinInfoLTC.symbol("LTC").price(32.3408920678).id(2);
        latestCoinInfos.add("LTC", coinInfoLTC.build());
        cmcRepository.save(coinInfoLTC.build());

        CoinInfo.Builder coinInfoXRP = new CoinInfo.Builder();
        coinInfoXRP.symbol("XRP").price(0.35803435642).id(52);
        latestCoinInfos.add("XRP", coinInfoXRP.build());
        cmcRepository.save(coinInfoXRP.build());

        CoinInfo.Builder coinInfoXLM = new CoinInfo.Builder();
        coinInfoXLM.symbol("XLM").price(0.115525502213).id(512);
        latestCoinInfos.add("XLM", coinInfoXLM.build());
        cmcRepository.save(coinInfoXLM.build());

        CoinInfo.Builder coinInfoBCH = new CoinInfo.Builder();
        coinInfoBCH.symbol("BCH").price(161.540193458).id(1831);
        latestCoinInfos.add("BCH", coinInfoBCH.build());
        cmcRepository.save(coinInfoBCH.build());
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
