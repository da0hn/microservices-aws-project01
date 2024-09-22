package dev.da0hn.aws.project01;

import dev.da0hn.aws.project01.entities.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Product Controller Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends IntegrationTest {

  @LocalServerPort
  private Integer serverPort;

  @BeforeEach
  void setUp() {
    RestAssured.port = this.serverPort;
  }

  @Test
  @CleanupAfterTest
  @DisplayName("Should create a product")
  void test1() {
    RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/create_product_payload.json"))
      .when()
      .post("/api/products")
      .then()
      .statusCode(200)
      .body("id", Matchers.notNullValue(Long.class))
      .body("name", Matchers.equalTo("Produto 1"))
      .body("model", Matchers.equalTo("Modelo 1"))
      .body("code", Matchers.equalTo("ABCD1234"))
      .body("price", Matchers.equalTo(1999.99F))
      .log();
  }

  @Test
  @CleanupAfterTest
  @DisplayName("Should update a product")
  void test2() {
    final var newProduct = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/create_product_payload.json"))
      .when()
      .post("/api/products")
      .thenReturn()
      .as(Product.class);

    RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/update_product_payload.json"))
      .when()
      .put("/api/products/{productId}", newProduct.getId())
      .then()
      .statusCode(200)
      .body("id", Matchers.notNullValue(Long.class))
      .body("name", Matchers.equalTo("Produto 2"))
      .body("model", Matchers.equalTo("Modelo 2"))
      .body("code", Matchers.equalTo("WXYZ9876"))
      .body("price", Matchers.equalTo(2500.00F))
      .log();
  }

  @Test
  @CleanupAfterTest
  @DisplayName("Should delete product by id")
  void test3() {
    final var newProduct = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/create_product_payload.json"))
      .when()
      .post("/api/products")
      .thenReturn()
      .as(Product.class);

    RestAssured.given()
      .contentType(ContentType.JSON)
      .when()
      .delete("/api/products/{productId}", newProduct.getId())
      .then()
      .statusCode(200)
      .log();

    RestAssured.given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/products/{productId}", newProduct.getId())
      .then()
      .statusCode(404)
      .log();

  }

  @Test
  @CleanupAfterTest
  @DisplayName("Should find product by id")
  void test4() {
    final var newProduct = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/create_product_payload.json"))
      .when()
      .post("/api/products")
      .thenReturn()
      .as(Product.class);

    RestAssured.given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/products/{productId}", newProduct.getId())
      .then()
      .statusCode(200)
      .body("id", Matchers.notNullValue(Long.class))
      .body("name", Matchers.equalTo("Produto 1"))
      .body("model", Matchers.equalTo("Modelo 1"))
      .body("code", Matchers.equalTo("ABCD1234"))
      .body("price", Matchers.equalTo(1999.99F))
      .log();
  }

  @Test
  @CleanupAfterTest
  @DisplayName("Should find product by code")
  void test5() {
    final var newProduct = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/product/create_product_payload.json"))
      .when()
      .post("/api/products")
      .thenReturn()
      .as(Product.class);

    RestAssured.given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/products/code/{code}", newProduct.getCode())
      .then()
      .statusCode(200)
      .body("id", Matchers.notNullValue(Long.class))
      .body("name", Matchers.equalTo("Produto 1"))
      .body("model", Matchers.equalTo("Modelo 1"))
      .body("code", Matchers.equalTo("ABCD1234"))
      .body("price", Matchers.equalTo(1999.99F))
      .log();
  }

}
