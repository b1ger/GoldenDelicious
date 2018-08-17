package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.enums.StaffType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    @Enumerated(value = EnumType.STRING)
    private StaffType type;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    private String username;

    @Transient
    private String password;

    private String passwordHash;

    @Lob
    private Byte[] image;
}
