package com.interviewproject.cryptocurrency_platform.services.market;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewproject.cryptocurrency_platform.models.market.KrakenMessage;
import com.interviewproject.cryptocurrency_platform.websockets.MarketWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class KrakenWebSocketService implements WebSocket.Listener {
    private static final Logger logger = Logger.getLogger(KrakenWebSocketService.class.getName());

    private static final String WS_URL = "wss://ws.kraken.com/v2";

    private WebSocket webSocket;
    private final MarketWebSocketHandler broadcaster;

    @Autowired
    public KrakenWebSocketService(MarketWebSocketHandler broadcaster) {
        this.broadcaster = broadcaster;
        connect();
    }

    public void connect() {
        Executor executor = Executors.newSingleThreadExecutor();
        HttpClient client = HttpClient.newBuilder()
                .executor(executor)
                .build();

        client.newWebSocketBuilder()
                .buildAsync(URI.create(WS_URL), this)
                .thenAccept(ws -> {
                    this.webSocket = ws;
                    subscribeTicker(ws);
                });
    }

    private void subscribeTicker(WebSocket ws) {
        String subscribeMessage = """
        {
          "method": "subscribe",
          "params": {
            "channel": "ticker",
            "symbol": [
               "BTC/USD", "ETH/USD", "USDT/USD", "BNB/USD", "SOL/USD",
               "XRP/USD", "ADA/USD", "DOGE/USD", "TON/USD", "AVAX/USD",
               "DOT/USD", "LINK/USD", "MATIC/USD", "LTC/USD", "TRX/USD",
               "SHIB/USD", "ICP/USD", "XLM/USD", "UNI/USD", "ATOM/USD"
             ]
          }
        }
        """;

        ws.sendText(subscribeMessage, true);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        logger.info("Connected to Kraken WebSocket");
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            KrakenMessage message = mapper.readValue(data.toString(), KrakenMessage.class);

            if (message.channel != null && message.channel.equals("ticker")) {
                logger.info(mapper.writeValueAsString(message.data));
                broadcaster.broadcast(mapper.writeValueAsString(message.data));
            }


        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        logger.info("OnError: " + error.getMessage());
    }
}
