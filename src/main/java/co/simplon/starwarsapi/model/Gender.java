package co.simplon.starwarsapi.model;

import javax.persistence.*;

@Entity
@Table(name = "genders")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_name")
    private String name;

    public Gender() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
