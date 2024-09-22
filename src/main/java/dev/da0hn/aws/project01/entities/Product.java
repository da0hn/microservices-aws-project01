package dev.da0hn.aws.project01.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(
  name = "product",
  uniqueConstraints = @UniqueConstraint(columnNames = { "code" })
)
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", length = 32, nullable = false)
  private String name;

  @Column(name = "model", length = 24, nullable = false)
  private String model;

  @Column(name = "code", length = 8, nullable = false)
  private String code;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

}
