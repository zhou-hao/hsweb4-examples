package org.hswebframework.example.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "join_test")
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JoinTable extends GenericEntity<String> {

    @Column
    private String name;

    @Column
    private String code;
}
