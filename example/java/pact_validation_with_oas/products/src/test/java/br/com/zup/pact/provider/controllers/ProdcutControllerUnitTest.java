package br.com.zup.pact.provider.controllers;

import br.com.zup.pact.provider.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

@WebMvcTest(ProductController.class)
public class ProdcutControllerUnitTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String PRODUCTS_PATH = "/api/products";

    @Test
    public void testGetAllWhenProductListIsEmpty() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(PRODUCTS_PATH + "/"))
                .andExpect(status().is4xxClientError());

    }
}
