package com.luismarcilio.grocery_brasil_app.textSearch;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.luismarcilio.grocery_brasil_app.textSearch.config.DirectoryPath;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Products;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Unity;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Like this

public class IntegrationTests {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        DirectoryPath directoryPath;

        @Autowired
        String apiKey;

        @BeforeEach
        public void setIndexDirectory() throws IOException {
                String command = "rm -rf " + directoryPath.getPath();
                Runtime.getRuntime().exec(command);
                Files.createDirectories(Path.of(directoryPath.getPath()));
        }

        @Test
        public void shouldInsertaProduct() throws URISyntaxException {
                final String url = "http://localhost:" + port + "/product/";
                final HttpHeaders headers = new HttpHeaders();
                headers.set("x-api-key", apiKey);

                final Product product = Product.builder().id("id01").name("Cerveja Backer").eanCode("someEanCode")
                                .ncmCode("someNcmCode").unity(new Unity("Lt")).thumbnail("http://somethumbnail")
                                .normalized(false).build();
                HttpEntity<Product> request = new HttpEntity<>(product, headers);
                ResponseEntity<Object> responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request,
                                Object.class);
                assertEquals(201, responseEntity.getStatusCodeValue());
        }

        @Test
        public void shouldFindProduct() throws URISyntaxException {
                final String url = "http://localhost:" + port + "/product/";
                final HttpHeaders headers = new HttpHeaders();
                headers.set("X-API-KEY", apiKey);

                List<Product> productInputList = Lists.list(
                                Product.builder().id("id01").name("Cerveja Backer").eanCode("someEanCode")
                                                .ncmCode("someNcmCode").unity(new Unity("Lt"))
                                                .thumbnail("http://somethumbnail").normalized(false).build(),
                                Product.builder().id("id02").name("Cerveja Skol").eanCode("someEanCode")
                                                .ncmCode("someNcmCode").unity(new Unity("Lt"))
                                                .thumbnail("http://somethumbnail").normalized(false).build(),
                                Product.builder().id("id03").name("Leite Condensado").eanCode("someEanCode")
                                                .ncmCode("someNcmCode").unity(new Unity("Cx"))
                                                .thumbnail("http://somethumbnail").normalized(false).build(),
                                Product.builder().id("id04").name("Doce de Leite").eanCode("someEanCode")
                                                .ncmCode("someNcmCode").unity(new Unity("Kg"))
                                                .thumbnail("http://somethumbnail").normalized(false).build(),
                                Product.builder().id("id05").name("RAÇÃO PARA CACHORRO").eanCode("someEanCode")
                                                .ncmCode("someNcmCode").unity(new Unity("Kg"))
                                                .thumbnail("http://somethumbnail").normalized(false).build());
                List<Product> expected = Lists.list(productInputList.get(0), productInputList.get(1));

                HttpEntity<Product> request = new HttpEntity<>(productInputList.get(0), headers);
                this.restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
                request = new HttpEntity<>(productInputList.get(1), headers);
                this.restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
                request = new HttpEntity<>(productInputList.get(2), headers);
                this.restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
                request = new HttpEntity<>(productInputList.get(3), headers);
                this.restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
                request = new HttpEntity<>(productInputList.get(4), headers);
                this.restTemplate.exchange(url, HttpMethod.POST, request, Object.class);

                request = new HttpEntity<>(null, headers);

                ResponseEntity<Products> responseEntity = this.restTemplate.exchange(
                                "http://localhost:" + port + "/product?text=Cervej", HttpMethod.GET, request,
                                Products.class);
                assertEquals(200, responseEntity.getStatusCodeValue());
                assertArrayEquals(expected.toArray(), responseEntity.getBody().getProductList().toArray());

                responseEntity = this.restTemplate.exchange(
                                "http://localhost:" + port + "/product?text=racao", HttpMethod.GET, request,
                                Products.class);
                assertEquals(200, responseEntity.getStatusCodeValue());
                assertEquals(1, responseEntity.getBody().getProductList().size());
                assertEquals(productInputList.get(4), responseEntity.getBody().getProductList().get(0));


                responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/product/all", HttpMethod.GET,
                                request, Products.class);

                assertEquals(200, responseEntity.getStatusCodeValue());
                assertArrayEquals(productInputList.toArray(), responseEntity.getBody().getProductList().toArray());

        }
}
