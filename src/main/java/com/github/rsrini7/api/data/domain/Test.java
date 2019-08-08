package com.github.rsrini7.api.data.domain;

import com.yahoo.elide.annotation.Include;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Entity
@Include(rootLevel =  true, type = Test.TYPE_NAME)
@EqualsAndHashCode(of = "id")
public class Test extends AbstractEntity{

    public static final String TYPE_NAME = "test";

    private String name;
    public String getName() {
        return name;
    }
}
