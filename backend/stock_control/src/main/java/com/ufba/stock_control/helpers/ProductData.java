package com.ufba.stock_control.helpers;

import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.ProductCategory;
import com.ufba.stock_control.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductData {
  
  @Autowired
  private ProductsRepository productsRepository;
  
  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    Product product = Product.builder()
      .name("Tylenol")
      .stock(500)
      .price(34.0)
      .description("Paracetamol 750mg 20 comprimidos")
      .categories(ProductCategory.MEDICAMENTOS)
      .build();
      productsRepository.save(product);

    Product product2 = Product.builder()
      .name("Colgate Total 12")
      .stock(100)
      .price(22.49)
      .description("Creme dental Colgate Total 12 90g")
      .categories(ProductCategory.HIGIENE)
      .build();
      productsRepository.save(product2);
    
    Product product3 = Product.builder()
      .name("Whey Integralmedica")
      .stock(150)
      .price(69.90)
      .description("Whey Isolado E Concentrado 900g - Integralmédica")
      .categories(ProductCategory.SUPLEMENTOS)
      .build();
      productsRepository.save(product3);
    
    Product product4 = Product.builder()
      .name("Esmalte Deméter")
      .stock(280)
      .price(24.90)
      .description("Esmalte Deméter - Holográfico Hits - 6ml")
      .categories(ProductCategory.COSMETICOS)
      .build();
      productsRepository.save(product4);
    
    Product product5 = Product.builder()
      .name("Redoxon Vitamina C")
      .stock(200)
      .price(42.99)
      .description("Redoxon Tripla Ação com Vitamina C, Vitamina D e Zinco, 30 comprimidos Efervescentes sabor Laranja.")
      .categories(ProductCategory.VITAMINAS)
      .build();
      productsRepository.save(product5);

    
  }
  
}
