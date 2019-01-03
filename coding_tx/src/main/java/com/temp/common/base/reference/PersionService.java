package com.temp.common.base.reference;

    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

public class PersionService {
    private Integer id=1;
    private String name="persion1";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
