package br.com.zup.pact.provider.controllers;

import br.com.zup.pact.provider.entities.Product;
import br.com.zup.pact.provider.repositories.ProductRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/" )
    public ResponseEntity<List<Product>> getAllProducts(){
        final List<Product> products = ImmutableList.copyOf(productRepository.findAll());

        if (products.isEmpty())
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
