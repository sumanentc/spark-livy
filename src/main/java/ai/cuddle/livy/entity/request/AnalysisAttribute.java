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
public class AnalysisAttribute implements Serializable {

    private static final long serialVersionUID = 1190901743626774117L;
    private String attributeName;
    private List<AttributeFilters> filters;
    private boolean inGroupBy = false;
    private Boolean toBeDisplayed = Boolean.valueOf(false);
    private Boolean includedInLabel = Boolean.valueOf(false);
    private Long position;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<AttributeFilters> getFilters() {
        return filters;
    }

    public void setFilters(List<AttributeFilters> filters) {
        this.filters = filters;
    }

    public boolean isInGroupBy() {
        return inGroupBy;
    }

    public void setInGroupBy(boolean inGroupBy) {
        this.inGroupBy = inGroupBy;
    }

    public Boolean getToBeDisplayed() {
        return toBeDisplayed;
    }

    public void setToBeDisplayed(Boolean toBeDisplayed) {
        this.toBeDisplayed = toBeDisplayed;
    }

    public Boolean getIncludedInLabel() {
        return includedInLabel;
    }

    public void setIncludedInLabel(Boolean includedInLabel) {
        this.includedInLabel = includedInLabel;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnalysisAttribute{");
        sb.append("attributeName='").append(attributeName).append('\'');
        sb.append(", filters=").append(filters);
        sb.append(", inGroupBy=").append(inGroupBy);
        sb.append(", toBeDisplayed=").append(toBeDisplayed);
        sb.append(", includedInLabel=").append(includedInLabel);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }
}
