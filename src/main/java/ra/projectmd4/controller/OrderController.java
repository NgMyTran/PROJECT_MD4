package ra.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Order;
import ra.projectmd4.model.entity.OrderStatus;
import ra.projectmd4.service.order.IOrderService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/place-order")
    public String showPlaceOrderForm(@RequestParam("items") String items,
                                     @RequestParam("totalAmount") BigDecimal totalAmount,
                                     Model model,
                                     HttpSession session) {
        UserInfo u = (UserInfo) session.getAttribute("userLogin");
        if (u == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", u.getUserId()); // Thêm userId vào model
        model.addAttribute("items", items);
        model.addAttribute("totalAmount", totalAmount);
        return "user/address";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam("userId") Long userId,
                             @RequestParam("receiveName") String receiveName,
                             @RequestParam("receiveAddress") String receiveAddress,
                             @RequestParam("receivePhone") String receivePhone,
                             @RequestParam("totalAmount") BigDecimal totalAmount) {
        Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
        System.out.println("Creating order: " + order.toString());
        orderService.createOrder(order); // Kiểm tra giá trị ở đây
        return "redirect:/user/order/bill?orderId=" + order.getOrderId();
    }
    //    @PostMapping("/placeOrder")
//    public String placeOrder(@RequestParam("userId") Long userId,
//                             @RequestParam("receiveName") String receiveName,
//                             @RequestParam("receiveAddress") String receiveAddress,
//                             @RequestParam("receivePhone") String receivePhone,
//                             @RequestParam("totalAmount") BigDecimal totalAmount,
//                             @RequestParam("items") List<Long> items) {
//        Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
//        orderService.createOrder(order);
//        int totalQuantity = items.size();
//        return "redirect:/user/order/bill?orderId=" + order.getOrderId() + "&totalQuantity=" + totalQuantity;
//    }
    @GetMapping("/bill")
    public String showBill(@RequestParam("orderId") Long orderId,
                           @RequestParam(value = "totalQuantity", required = false) Integer totalQuantity,
                           Model model) {
        Order order = orderService.findById(orderId);
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("totalQuantity", totalQuantity);
            return "user/bill";
        } else {
            model.addAttribute("message", "Order not found.");
            return "redirect:/user/order/bill?orderId=" + orderId;
        }
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam("userId") Long userId,
                               @RequestParam("totalAmount") BigDecimal totalAmount,
                               @RequestParam("receiveName") String receiveName,
                               @RequestParam("receiveAddress") String receiveAddress,
                               @RequestParam("receivePhone") String receivePhone) {
        Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
        orderService.createOrder(order);
        return "redirect:/user/cart/success";
    }

    private Order createOrder(Long userId, String receiveName, String receiveAddress,
                              String receivePhone, BigDecimal totalAmount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalAmount);
        order.setReceiveName(receiveName);
        order.setReceiveAddress(receiveAddress);
        order.setReceivePhone(receivePhone);
        order.setStatus(OrderStatus.WAITING);
        order.setCreatedAt(LocalDate.now());
        String serialNumber = "ORD" + userId + "-" + System.currentTimeMillis();
        order.setSerialNumber(serialNumber);
        return order;
    }
}
