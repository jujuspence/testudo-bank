package net.testudobank;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yahoofinance.YahooFinance;

import java.io.IOException;

@Component
public class CryptoPriceClient {
    /**
     * Method to control which supported Cryptocurrency's price should be returned.
     * @return
     */
    public double getCurrentCryptoValue(String cryptoName) {
      if (cryptoName.equals("ETH")) {
        return getCurrentEthValue();
      } else if (cryptoName.equals("SOL")) {
        return getCurrentSolValue();
      } else if (cryptoName.equals("BTC")){
        return getCurrentBtcValue();
      } else {
        return -1;
      }
    }

    /**
     * Method which is used to return the current value of Ethereum
     * in USD. This method uses a Yahoo Finance Wrapper API (https://github.com/sstrickx/yahoofinance-api).
     * <p>
     * To avoid frequent calls to the external service, the value is cached.
     * See {@link #clearEthPriceCache()}
     * <p>
     * NOTE: If the web scraper fails, a value of -1 is returned
     *
     * @return the current value of 1 ETH in USD
     */
    @Cacheable("eth-value")
    public double getCurrentEthValue() {
        try {
            return YahooFinance.get("ETH-USD").getQuote().getPrice().doubleValue();
        } catch (IOException e1) {
            // Print Stack Trace for Debugging
            e1.printStackTrace();
            return -1;
        }
    }


    /**
     * Method which is used to return the current value of Bitcoin
     * in USD. This method uses a Yahoo Finance Wrapper API (https://github.com/sstrickx/yahoofinance-api).
     * <p>
     * To avoid frequent calls to the external service, the value is cached.
     * See {@link #clearBtcPriceCache()}
     * <p>
     * NOTE: If the web scraper fails, a value of -1 is returned
     *
     * @return the current value of 1 BTC in USD
     */
    @Cacheable("btc-value")
    public double getCurrentBtcValue() {
        try {
            return YahooFinance.get("BTC-USD").getQuote().getPrice().doubleValue();
        } catch (IOException e1) {
            // Print Stack Trace for Debugging
            e1.printStackTrace();
            return -1;
        }
    }


    /**
     * Method which is used to return the current value of Solana
     * in USD. This method uses a Yahoo Finance Wrapper API (https://github.com/sstrickx/yahoofinance-api).
     * <p>
     * To avoid frequent calls to the external service, the value is cached.
     * See {@link #clearSolPriceCache()}
     * <p>
     * NOTE: If the web scraper fails, a value of -1 is returned
     *
     * @return the current value of 1 SOL in USD
     */
    @Cacheable("sol-value")
    public double getCurrentSolValue() {
        try {
            return YahooFinance.get("SOL-USD").getQuote().getPrice().doubleValue();
        } catch (IOException e1) {
            // Print Stack Trace for Debugging
            e1.printStackTrace();
            return -1;
        }
    }


    /**
     * Clear the cached price of solana.
     * <p>
     * This method is scheduled to run every 30 seconds.
     */
    @Scheduled(fixedRate = 30000)
    @CacheEvict("sol-value")
    public void clearSolPriceCache() {
    }


   /**
     * Clear the cached price of bitcoin.
     * <p>
     * This method is scheduled to run every 30 seconds.
     */
    @Scheduled(fixedRate = 30000)
    @CacheEvict("btc-value")
    public void clearBtcPriceCache() {
    }


    /**
     * Clear the cached price of ethereum.
     * <p>
     * This method is scheduled to run every 30 seconds.
     */
    @Scheduled(fixedRate = 30000)
    @CacheEvict("eth-value")
    public void clearEthPriceCache() {
    }

}
