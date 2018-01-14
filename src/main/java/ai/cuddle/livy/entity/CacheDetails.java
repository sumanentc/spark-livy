package ai.cuddle.livy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by suman.das on 12/29/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CacheDetails  implements Serializable{
    private String tableName;
    private boolean clearCache;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isClearCache() {
        return clearCache;
    }

    public void setClearCache(boolean clearCache) {
        this.clearCache = clearCache;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CacheDetails{");
        sb.append("tableName='").append(tableName).append('\'');
        sb.append(", clearCache=").append(clearCache);
        sb.append('}');
        return sb.toString();
    }
}
