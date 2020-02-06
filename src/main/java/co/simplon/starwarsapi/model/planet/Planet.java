package co.simplon.starwarsapi.model.planet;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "planet_name")
    private String name;

    @Column(name = "rotation_period", nullable = true)
    private Integer rotationPeriod;

    @Column(name = "orbital_period", nullable = true)
    private Integer orbitalPeriod;

    @Column(name = "diameter", nullable = true)
    private Integer diameter;

    @Column(name = "gravity")
    private BigDecimal gravity;

    @Column(name = "surface_water", nullable = true)
    private Integer surfaceWater;

    @Column(name = "population", nullable = true)
    private Long population;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "planet_terrains",
            joinColumns = @JoinColumn(name = "planet_idx"),
            inverseJoinColumns = @JoinColumn(name = "terrain_idx"))
    private Set<Terrain> planetTerrains = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "planet_climates",
            joinColumns = @JoinColumn(name = "planet_idx"),
            inverseJoinColumns = @JoinColumn(name = "climate_idx"))
    private Set<Climate> planetClimates = new HashSet<>();

    public Planet() {
    }

    public Planet(String name) { this.name = name; }

    public Planet(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRotationPeriod() {
        return rotationPeriod;
    }

    public Integer getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public BigDecimal getGravity() {
        return gravity;
    }

    public Integer getSurfaceWater() {
        return surfaceWater;
    }

    public Long getPopulation() {
        return population;
    }

    public Set<Terrain> getPlanetTerrains() {
        return planetTerrains;
    }

    public Set<Climate> getPlanetClimates() {
        return planetClimates;
    }
}
