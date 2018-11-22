package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotBlank
    private String name;
    private String description;

    public Authority() {
        super();
    }

    public Authority(final String name, final String description) {
        this.name = name;
        this.description = description;
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

        return !(this.name != null ?
                !this.name.equals(authority.name) && !this.description.equals(authority.description) :
                authority.name != null && authority.description != null);
    }

    @Override
    public int hashCode() {
        return this.name != null ? this.name.hashCode() + this.description.hashCode() : 0;
    }
}
