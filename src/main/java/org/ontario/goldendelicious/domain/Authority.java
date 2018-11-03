package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@Setter
public class Authority {

    @Id
    @Column(length = 50)
    private String name;

    public Authority() {
        super();
    }

    public Authority(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final Authority authority = (Authority) obj;

        return !(this.name != null ? !this.name.equals(authority.name) : authority.name != null);
    }

    @Override
    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }
}
