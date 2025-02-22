package org.limir.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long service_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    public Service(Long service_id, String name, String description, BigDecimal price) {
        this.service_id = service_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private Company company;

    public Service() {

    }

    public Service(Long service_id, String name, String description, BigDecimal price, Company company) {
        this.service_id = service_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.company = company;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Service{" +
                "service_id=" + service_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", company_id=" + (company != null ? company.getCompany_id() : "null") +
                '}';
    }
}
