package com.translator.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class JPA {

    private Long id;
    private Date version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "version")
    public Date getVersion() {
        return version;
    }
    public void setVersion(Date version) {
        this.version = version;
    }

}