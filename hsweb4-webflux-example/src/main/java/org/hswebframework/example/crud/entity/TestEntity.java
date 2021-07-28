package org.hswebframework.example.crud.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.example.crud.enums.TestEnum;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.hswebframework.ezorm.rdb.mapping.annotation.JsonCodec;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.crud.generator.Generators;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.JDBCType;
import java.util.Date;
import java.util.Map;

@Table(name = "t_test")
@Getter
@Setter
public class TestEntity extends GenericEntity<String> {

    @Column(length = 64, nullable = false)
    @NotBlank
    @Schema(description = "名称")
    private String name;

    //枚举
    @Column(length = 32)
    @EnumCodec
    @ColumnType(javaType = String.class)
    private TestEnum enumTest;

    @Column
    @JsonCodec
    @ColumnType(javaType = String.class, jdbcType = JDBCType.CLOB)
    private Map<String, Object> jsonValue;

    @Column(updatable = false)
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @DefaultValue(generator = Generators.CURRENT_TIME)//逻辑默认值
    private Date createTime;
}
