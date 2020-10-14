package com.basusingh.throwat;

import java.util.List;

public abstract class ThrwAtURLStatsFetchTask {
    public abstract boolean isSuccessful();

    public abstract String getStats();

    public abstract String getMessage();

    public abstract List<URLStatsItems> getURLStats();

    public abstract String getPremium();
}
