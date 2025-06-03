package com.interviewproject.cryptocurrency_platform.models.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KrakenMessage {
    public String channel;
    public String type;
    public List<TickerData> data;
}
