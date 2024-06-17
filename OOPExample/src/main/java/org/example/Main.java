package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

abstract class MenuItem{
    private final String Name;
    private final String Description;
    private final Double Price;
    private final Double TaxPercentage;
    public MenuItem(String name, String description, Double price, Double taxPercentage) {
        Name = name;
        Description = description;
        Price = price;
        TaxPercentage = taxPercentage;
    }

    @Override
    public String toString() {
        return Name + " " + Description+" " +" "+ Price+" " + " " +Price + " "+TaxPercentage;
    }

    public Double getTaxPercentage()
    {
        return TaxPercentage;
    }

    public String getName()
    {
        return Name;
    }

    public Double getPrice()
    {
        return Price;
    }


}

class Food extends MenuItem {
    enum Category {STARTER, MAIN, DESSERT}
    private Category category;

    public Food(String name, String description, Double price,  Category category) {
        super(name, description, price, 0.08);
        this.category=category;
    }


    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return super.toString() + ", Category: " + category;
    }
}

class Beverage extends MenuItem {
    enum Type {NON_ALCOHOLIC, ALCOHOLIC}
    private final Type type;

    public Beverage(String name, String description, Double price,Type type) {
        super(name, description, price, type == Type.NON_ALCOHOLIC ? 0.1 : 0.18);
        this.type=type;
    }
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: " + type;
    }
}
class OrderMenuItem{
    private final MenuItem item;
    private int quantity;

    public OrderMenuItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return item.toString() + " x " + quantity;
    }

    public double getPrice()
    {
        return item.getPrice()*(double)quantity;
    }

    public double getPriceAfterTax()
    {
        return (item.getPrice()+(item.getPrice()*item.getTaxPercentage()))*(double)quantity;
    }
}
class Order {
    private UUID id;
    private final Long timeOfOrder;
    private final List<OrderMenuItem> items;
    private Double tip;

    public Order() {
        this.id = UUID.randomUUID();
        this.timeOfOrder = System.currentTimeMillis();
        this.items = new ArrayList<>();
    }

    public void addItem(OrderMenuItem item) {
        items.add(item);
    }

    public void removeItem(OrderMenuItem item) {
        items.remove(item);
    }

    public Long getTimeOfOrder()
    {
        return timeOfOrder;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }
    public double getTip() {
        return tip;
    }

    public List<OrderMenuItem> getItems() {
        return items;
    }

    public UUID getId() {
        return id;
    }

    public double getTotalBalance()
    {
        double result=0.0;
        for(OrderMenuItem i: items)
        {
            result+=i.getPrice();
        }
        return result;
    }

    public double getTotalEarnings()
    {
        double result=0.0;
        for(OrderMenuItem i: items)
        {
            result+=i.getPriceAfterTax();
        }
        return result+getTip();

    }

    @Override
    public String toString() {
        return "Order ID: " + id + ", Time: " + timeOfOrder + ", Items: " + items.toString() + ", Tip: $" + tip + ", TotalBalance: $" + getTotalBalance()+ ", TotalEarnings: $" + getTotalEarnings();
    }
}
class RestaurantDay {
    private final List<MenuItem> menu;
    private final List<Order> orders;

    public RestaurantDay() {
        menu = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void removeMenuItem(MenuItem item) {
        menu.remove(item);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public MenuItem getMenuItemByName(String name)
    {
        for(MenuItem i: menu)
        {
            if(i.getName().equals(name))
            {
                return i;
            }

        }
       return null;
    }

    public Order getOrderById(UUID id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Order> getOrders() {
        return orders;
    }
    public List<Order> getOrdersSorted() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTimeOfOrder))
                .collect(Collectors.toList());
    }
    public List<MenuItem> getMenuItems() {
        return menu;
    }

    public Double AllDayEarnings()
    {
        double totalDayEarn=0.0;
        for (Order i: orders) {
            totalDayEarn+=i.getTotalEarnings();
        }
        return totalDayEarn;
    }

    @Override
    public String toString() {
        return "Restaurant Menu: " + menu + ", Orders: " + orders.toString();
    }
}





public class Main {
    public static void main(String[] args) {

        //MenuItem
       Food food=new Food("Hrana1","Desc1",100.0, Food.Category.MAIN);
       Beverage beverage=new Beverage("Bevrage1","Desc1",100.0, Beverage.Type.NON_ALCOHOLIC);
       RestaurantDay restaurant=new RestaurantDay();
       restaurant.addMenuItem(food);
       restaurant.addMenuItem(beverage);
       System.out.println("After adding 2 items: " +restaurant.getMenuItems());
       restaurant.removeMenuItem(beverage);
       System.out.println("After removing 1 item: " +restaurant.getMenuItems());
       System.out.println("Find Menu Item By Name: " + restaurant.getMenuItemByName("Hrana1"));

       //Order(total balance and earnings go after every order info)
       OrderMenuItem orderMenuItemFood=new OrderMenuItem(food,10);
       OrderMenuItem orderMenuItemBevarage=new OrderMenuItem(beverage,20);
       Order order1=new Order();
       order1.addItem(orderMenuItemFood);
       order1.setTip(100.0);
       restaurant.addOrder(order1);
       Order order2=new Order();
       order1.addItem(orderMenuItemBevarage);
       order2.setTip(200.0);
       order2.addItem(orderMenuItemBevarage);
       restaurant.addOrder(order2);
       System.out.println("After adding 2 orders: "+restaurant.getOrders());
       restaurant.removeOrder(order2);
       System.out.println("After removing 1 order: "+restaurant.getOrders());
       System.out.println("Details For Order By Id: "+restaurant.getOrderById(order1.getId()));


       //AllDay
        restaurant.addOrder(order2);
        System.out.println("All Orders Sorted: " + restaurant.getOrdersSorted());
        System.out.println("All Day Earnings: " + restaurant.AllDayEarnings());

        
    }
}