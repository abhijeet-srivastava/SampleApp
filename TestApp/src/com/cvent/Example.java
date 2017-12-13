/**
 * Created by a.srivastava on 3/2/16.
 */

package com.cvent;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "os",
        "name",
        "resolution"
})
public class Example {

    @JsonProperty("os")
    private String os;
    @JsonProperty("name")
    private String name;
    @JsonProperty("resolution")
    private Resolution resolution;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The os
     */
    @JsonProperty("os")
    public String getOs() {
        return os;
    }

    /**
     *
     * @param os
     * The os
     */
    @JsonProperty("os")
    public void setOs(String os) {
        this.os = os;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The resolution
     */
    @JsonProperty("resolution")
    public Resolution getResolution() {
        return resolution;
    }

    /**
     *
     * @param resolution
     * The resolution
     */
    @JsonProperty("resolution")
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
