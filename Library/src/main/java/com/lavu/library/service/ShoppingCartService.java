package com.lavu.library.service;

import com.lavu.library.model.Customer;
import com.lavu.library.model.Product;
import com.lavu.library.model.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart deleteItemFromCart(Product product, Customer customer);

	ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);

	ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

}
