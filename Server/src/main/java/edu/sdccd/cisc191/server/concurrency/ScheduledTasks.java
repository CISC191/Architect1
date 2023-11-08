package edu.sdccd.cisc191.server.concurrency;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private BlockingQueue<FinnhubTask> taskQueue;

    private String[] tickers = {"AAPL","DIS","BAC","UAA","CCL","KO","WMT","T","GOOGL","MSFT","V","NVDA","AMZN","COST","AMD","TSM","META","TSLA"};

    private Instant lastFetched = Instant.now();

    // every 5 seconds or 5000 miliseconds
    @Scheduled(fixedRate = 5000)
    public void fetchNewCandleData() {
        for(String ticker : tickers) {
            Instant now = Instant.now();
            FinnhubTask task = new FinnhubTask(ticker, "day", lastFetched, now);
            taskQueue.offer(task);
            lastFetched = now;
        }
    }
}
