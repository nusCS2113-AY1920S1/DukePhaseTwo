package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Module {
    private String name;
    private String code;

    @JsonCreator
    public Module(@JsonProperty("code") String code, @JsonProperty("name") String name) {
        this.code = code;
        this.name = name;
    }

    public String toString() {
        return this.code + " " + this.name;
    }

    public boolean isOfCode(String code) {
        return this.code.equalsIgnoreCase(code);
    }

    public boolean equals(Module that) {
        return this.code.equalsIgnoreCase(that.code);
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("code")
    public String getCode() {
        return code;
    }
}
