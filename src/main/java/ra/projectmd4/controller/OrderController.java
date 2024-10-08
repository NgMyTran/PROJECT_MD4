//package ra.projectmd4.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.projectmd4.model.dto.response.UserInfo;
//import ra.projectmd4.model.entity.Order;
//import ra.projectmd4.model.entity.OrderStatus;
//import ra.projectmd4.service.order.IOrderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpSession;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/user/order")
//public class OrderController {
//    @Autowired
//    private IOrderService orderService;
//    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
//
//    @GetMapping("/place-order")
//    public String showPlaceOrderForm(@RequestParam("items") String items,
//                                     @RequestParam("totalAmount") BigDecimal totalAmount,
//                                     Model model,
//                                     HttpSession session) {
//        UserInfo u = (UserInfo) session.getAttribute("userLogin");
//        if (u == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("userId", u.getUserId()); // Thêm userId vào model
//        model.addAttribute("items", items);
//        model.addAttribute("totalAmount", totalAmount);
//        return "user/address";
//    }
//
//    @PostMapping("/place-order")
//    public String placeOrder(@RequestParam("userId") Long userId,
//                             @RequestParam("receiveName") String receiveName,
//                             @RequestParam("receiveAddress") String receiveAddress,
//                             @RequestParam("receivePhone") String receivePhone,
//                             @RequestParam("totalAmount") BigDecimal totalAmount,
//                             @RequestParam(value = "items", required = false) String items) {
//        // Chuyển đổi chuỗi items thành danh sách Long nếu cần
//        List<Long> itemIds = items != null && !items.isEmpty()
//                ? Arrays.stream(items.split(",")).map(Long::parseLong).collect(Collectors.toList())
//                : Collections.emptyList();
//
//        Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
//        orderService.createOrder(order);
//
//        // Sử dụng logger thay vì System.err
//        logger.error("Item IDs: {}", itemIds);
//        int totalQuantity = itemIds.size(); // Tính toán tổng số lượng
//        logger.error("Total Quantity: {}", totalQuantity);
//
//        return "redirect:/user/order/bill?orderId=" + order.getOrderId() + "&totalQuantity=" + totalQuantity;
//    }
//
////@PostMapping("/place-order")
////public String placeOrder(@RequestParam("userId") Long userId,
////                         @RequestParam("receiveName") String receiveName,
////                         @RequestParam("receiveAddress") String receiveAddress,
////                         @RequestParam("receivePhone") String receivePhone,
////                         @RequestParam("totalAmount") BigDecimal totalAmount,
////                         @RequestParam("items") List<Long> items) {
////    Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
////    orderService.createOrder(order);
//////    int totalQuantity = items.size();
////    int totalQuantity = (items != null) ? items.size() : 0;
////    return "redirect:/user/order/bill?orderId=" + order.getOrderId() + "&totalQuantity=" + totalQuantity;
////}
//
//    @GetMapping("/bill")
//    public String showBill(@RequestParam("orderId") Long orderId,
//                           @RequestParam(value = "totalQuantity", required = false) Integer totalQuantity,
//                           Model model) {
//        Order order = orderService.findById(orderId);
//        if (order != null) {
//            model.addAttribute("order", order);
//            model.addAttribute("totalQuantity", totalQuantity);
//            return "user/bill"; // Trả về view bill
//        } else {
//            model.addAttribute("message", "Order not found.");
//            return "redirect:/user/cart"; // Nếu không tìm thấy đơn hàng, chuyển hướng về giỏ hàng
//        }
//    }
//
////    @GetMapping("/bill")
////    public String showBill(@RequestParam("orderId") Long orderId, Model model) {
////        Order order = orderService.findById(orderId);
////        if (order != null) {
////            model.addAttribute("order", order);
////            // Thêm thông tin product
////            return "user/bill";
////        } else {
////            model.addAttribute("message", "Order not found.");
////            return "redirect:/user/order/bill?orderId=" + orderId;
////        }
////    }
//
//    @PostMapping("/confirm")
//    public String confirmOrder(@RequestParam("userId") Long userId,
//                               @RequestParam("totalAmount") BigDecimal totalAmount,
//                               @RequestParam("receiveName") String receiveName,
//                               @RequestParam("receiveAddress") String receiveAddress,
//                               @RequestParam("receivePhone") String receivePhone) {
//        Order order = createOrder(userId, receiveName, receiveAddress, receivePhone, totalAmount);
//        orderService.createOrder(order);
//        return "redirect:/user/cart/success";
//    }
//
//    private Order createOrder(Long userId, String receiveName, String receiveAddress,
//                              String receivePhone, BigDecimal totalAmount) {
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setTotalPrice(totalAmount);
//        order.setReceiveName(receiveName);
//        order.setReceiveAddress(receiveAddress);
//        order.setReceivePhone(receivePhone);
//        order.setStatus(OrderStatus.WAITING);
//        order.setCreatedAt(LocalDate.now());
//        String serialNumber = "ORD" + userId + "-" + System.currentTimeMillis();
//        order.setSerialNumber(serialNumber);
//        return order;
//    }
//}
