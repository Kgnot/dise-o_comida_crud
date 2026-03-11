package proj.food.entity;

import jakarta.persistence.*;

@Table(name = "customer")
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public CustomerEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomerEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
