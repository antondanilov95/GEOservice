package ru.macrobit.geoservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.macrobit.drivertaxi.taximeter.TaximeterLocation;
import ru.macrobit.drivertaxi.taximeter.TaximeterLocationString;

/**
 * Created by [david] on 05.10.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "taximeterlog")
@CompoundIndexes({
        @CompoundIndex(name = "orderId", def = "{orderId:1}"),
        @CompoundIndex(name = "timestamp", def = "{timestamp:1}"),
        @CompoundIndex(name = "timestamp_orderid", def = "{orderId:1, timestamp:1}", unique = true)
})
public class LogEntry implements TaximeterLocation {
    @Id
    private String id;
    private double lat;
    private double lon;
    private long timestamp;
    private String src;
    private String error;
    private boolean builded;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonIgnore
    public String toLocationString() {
        return lat + "," + lon;
    }

    @Override
    @JsonIgnore
    public double getLatitude() {
        return this.lat;
    }

    @Override
    @JsonIgnore
    public double getLongitude() {
        return this.lon;
    }

    @Override
    @JsonIgnore
    public long getTime() {
        return this.timestamp;
    }

    @Override
    @JsonIgnore
    public double distanceTo(TaximeterLocation taximeterLocation) {
        return TaximeterLocationString.HaversineAlgorithm.HaversineInM(getLatitude(), getLongitude(), taximeterLocation.getLatitude(), taximeterLocation.getLongitude());
    }

    @Override
    @JsonIgnore
    public boolean isNetworkProvider() {
        return "network".equals(this.src);
    }

    @Override
    @JsonIgnore
    public String getProvider() {
        return this.src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntry logEntry = (LogEntry) o;

        return timestamp == logEntry.timestamp;

    }

    @Override
    public int hashCode() {
        return (int) (timestamp ^ (timestamp >>> 32));
    }

    public boolean isBuilded() {
        return builded;
    }

    public void setBuilded(boolean builded) {
        this.builded = builded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
