package org.hswebframework.example.crud.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.dict.EnumDict;

@Getter
@AllArgsConstructor
@JsonDeserialize(
        contentUsing = EnumDict.EnumDictJSONDeserializer.class
)
public enum TestEnum implements EnumDict<String> {

    state1("状态1"),
    state2("状态2"),
    state3("状态3"),


    ;
    private final String text;
    @Override
    public String getValue() {
        return name();
    }
}
