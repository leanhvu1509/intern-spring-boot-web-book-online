package com.lavu.library.service.Impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavu.library.model.CartItem;
import com.lavu.library.model.Customer;
import com.lavu.library.model.Product;
import com.lavu.library.model.ShoppingCart;
import com.lavu.library.repository.CartItemRepository;
import com.lavu.library.repository.ShoppingCartRepository;
import com.lavu.library.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemRepository itemRepository;

	@Autowired
	private ShoppingCartRepository cartRepository;

	// Thêm item vào cart
	@Override
	public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
		ShoppingCart cart = customer.getCart();

		if (cart == null) {
			cart = new ShoppingCart();
		}
		Set<CartItem> cartItems = cart.getCartItem();
		CartItem cartItem = findCartItem(cartItems, product.getId());
		if (cartItems == null) {
			cartItems = new HashSet<>();
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setTotalPrice(quantity * (product.getPrice()-product.getPrice()*product.getDiscount()/100));
				cartItem.setUnitPrice(product.getPrice()-product.getPrice()*product.getDiscount()/100);
				cartItem.setQuantity(quantity);
				cartItem.setCart(cart);
				cartItems.add(cartItem);
				itemRepository.save(cartItem);
			}
		} else {
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setTotalPrice(quantity * (product.getPrice()-product.getPrice()*product.getDiscount()/100));
				cartItem.setUnitPrice(product.getPrice()-product.getPrice()*product.getDiscount()/100);
				cartItem.setQuantity(quantity);
				cartItem.setCart(cart);
				cartItems.add(cartItem);
				itemRepository.save(cartItem);
			} else {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * (product.getPrice()-product.getPrice()*product.getDiscount()/100)));
				itemRepository.save(cartItem);
			}
		}
		cart.setCartItem(cartItems);

		int totalItems = totalItems(cart.getCartItem());
		double totalPrice = totalPrice(cart.getCartItem());

		cart.setTotalPrice(totalPrice);
		cart.setTotalItems(totalItems);
		cart.setCustomer(customer);

		return cartRepository.save(cart);
	}

	// Cập nhật cart
	@Override
	public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
		ShoppingCart cart = customer.getCart();

		Set<CartItem> cartItems = cart.getCartItem();

		CartItem item = findCartItem(cartItems, product.getId());

		item.setQuantity(quantity);
		item.setTotalPrice(quantity * (product.getPrice()-product.getPrice()*product.getDiscount()/100));

		itemRepository.save(item);

		int totalItems = totalItems(cartItems);
		double totalPrice = totalPrice(cartItems);

		cart.setTotalItems(totalItems);
		cart.setTotalPrice(totalPrice);

		return cartRepository.save(cart);
	}

	// Xóa item cart
	@Override
	public ShoppingCart deleteItemFromCart(Product product, Customer customer) {
		ShoppingCart cart = customer.getCart();

		Set<CartItem> cartItems = cart.getCartItem();

		CartItem item = findCartItem(cartItems, product.getId());

		cartItems.remove(item);

		itemRepository.delete(item);

		double totalPrice = totalPrice(cartItems);
		int totalItems = totalItems(cartItems);

		cart.setCartItem(cartItems);
		cart.setTotalItems(totalItems);
		cart.setTotalPrice(totalPrice);

		return cartRepository.save(cart);
	}

	/*
	 * private
	 */
	private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
		if (cartItems == null) {
			return null;
		}
		CartItem cartItem = null;

		for (CartItem item : cartItems) {
			if (item.getProduct().getId() == productId) {
				cartItem = item;
			}
		}
		return cartItem;
	}

	private int totalItems(Set<CartItem> cartItems) {
		int totalItems = 0;
		for (CartItem item : cartItems) {
			totalItems += item.getQuantity();
		}
		return totalItems;
	}

	private double totalPrice(Set<CartItem> cartItems) {
		double totalPrice = 0.0;

		for (CartItem item : cartItems) {
			totalPrice += item.getTotalPrice();
		}

		return totalPrice;
	}
}
