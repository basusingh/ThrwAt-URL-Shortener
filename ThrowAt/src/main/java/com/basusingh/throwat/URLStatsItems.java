package com.basusingh.throwat;

import java.io.Serializable;

public class URLStatsItems implements Serializable {

    public String host, port, userAgentVersion, userAgentPlatform, userContinentName, userCurrency;

    public String stats_id, url_id, referrer, userAgentName, userRegion, userCity, userCountryName, userTimezone;

    public String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserAgentVersion() {
        return userAgentVersion;
    }

    public void setUserAgentVersion(String userAgentVersion) {
        this.userAgentVersion = userAgentVersion;
    }

    public String getUserAgentPlatform() {
        return userAgentPlatform;
    }

    public void setUserAgentPlatform(String userAgentPlatform) {
        this.userAgentPlatform = userAgentPlatform;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserContinentName() {
        return userContinentName;
    }

    public void setUserContinentName(String userContinentName) {
        this.userContinentName = userContinentName;
    }

    public String getUserCurrency() {
        return userCurrency;
    }

    public void setUserCurrency(String userCurrency) {
        this.userCurrency = userCurrency;
    }

    public String getStats_id() {
        return stats_id;
    }

    public void setStats_id(String stats_id) {
        this.stats_id = stats_id;
    }

    public String getUrl_id() {
        return url_id;
    }

    public void setUrl_id(String url_id) {
        this.url_id = url_id;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUserAgentName() {
        return userAgentName;
    }

    public void setUserAgentName(String userAgentName) {
        this.userAgentName = userAgentName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountryName() {
        return userCountryName;
    }

    public void setUserCountryName(String userCountryName) {
        this.userCountryName = userCountryName;
    }

    public String getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(String userTimezone) {
        this.userTimezone = userTimezone;
    }
}
