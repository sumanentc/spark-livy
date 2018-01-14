package ai.cuddle.livy.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by divyesheth on 18/01/17.
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class AnalysisMeasure implements Serializable {

    private static final long serialVersionUID = -4970260137898970904L;
    private String measureName;
    private List<AnalysisMeasureFilter> filters;
    private Boolean ascending;
    private Boolean sorted;
    private String sentimentExpression;


    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public List<AnalysisMeasureFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<AnalysisMeasureFilter> filters) {
        this.filters = filters;
    }

    public Boolean isAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }

    public Boolean isSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public String getSentimentExpression() {
        return sentimentExpression;
    }

    public void setSentimentExpression(String sentimentExpression) {
        this.sentimentExpression = sentimentExpression;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnalysisMeasure{");
        sb.append("measureName='").append(measureName).append('\'');
        sb.append(", filters=").append(filters);
        sb.append(", ascending=").append(ascending);
        sb.append(", sorted=").append(sorted);
        sb.append(", sentimentExpression='").append(sentimentExpression).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
