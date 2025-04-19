package com.wahak.service.impl;

import com.wahak.dto.ChalakDto;
import com.wahak.dto.ChalakOrderDTO;
import com.wahak.entity.*;
import com.wahak.enums.OrderStatus;
import com.wahak.enums.OtpType;
import com.wahak.repository.ChalakRepository;
import com.wahak.repository.OrderRiderMappingRepository;
import com.wahak.repository.RiderWalletRepository;
import com.wahak.service.ChalakService;
import com.wahak.service.OrderService;
import com.wahak.service.OtpService;
import com.wahak.service.SMSService;
import com.wahak.utils.AuthenticatedUserUtil;
import com.wahak.utils.ChalakUtils;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author krishna.meena
 */

@Service
public class ChalakServiceImpl implements ChalakService {

    private static final int CHALAK_ORDER_PAGE_SIZE = 20;
    private final ChalakRepository chalakRepository;
    private final OtpService otpService;
    private final SMSService smsService;
    private final OrderRiderMappingRepository orderRiderMappingRepository;
    private final OrderService orderService;
    private final RiderWalletRepository riderWalletRespository;

    private static final Logger logger=LoggerFactory.getLogger(ChalakServiceImpl.class);

    public ChalakServiceImpl(ChalakRepository chalakRepository, OtpService otpService, SMSService smsService, OrderRiderMappingRepository orderRiderMappingRepository, OrderService orderService, RiderWalletRepository riderWalletRespository) {
        this.chalakRepository = chalakRepository;
        this.otpService = otpService;
        this.smsService = smsService;
        this.orderRiderMappingRepository = orderRiderMappingRepository;
        this.orderService = orderService;
        this.riderWalletRespository = riderWalletRespository;
    }

    @Override
    @Transactional
    public ChalakDto create(ChalakDto chalak) {

        Chalak entity = ChalakUtils.convertDtoToEntity(chalak);
        entity.setCity("test");
        chalakRepository.save(entity);
        String otp=otpService.generateRegistartionVerificationOtp(entity);
        sendMessage(entity,otp);
        return ChalakUtils.convertEntityToDto(entity);
    }

    @Override
    public Boolean validateRegistrationOtp(ChalakDto optRequest) {

        logger.info("validateRegistrationOtp: "+optRequest.toString());
        Chalak chalak=chalakRepository.findByMobile(optRequest.getMobile()).orElseGet(null);
        if(isValidchalakForRegistration(chalak)) {
            boolean isValid=otpService.validateOtp(optRequest.getOtp(), OtpType.CHALAK_REGISTRATION,chalak.getId());
            if(isValid) {
                RiderWallet riderWallet=new RiderWallet();
                riderWallet.setRider(chalak);
                riderWallet.setActive(true);
                riderWallet.setMaxAmount(500d);
                riderWallet.setAmount(0d);
                chalak.markVerify();
                chalakRepository.save(chalak);
                riderWalletRespository.save(riderWallet);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean enableChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(isValidToEnable(chalak)) {
            chalak.doActivate();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean blockChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.doBlock();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean disableChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.deActivate();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean unBlockChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.unblock();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Map<String,Object> login(ChalakDto chalakDto) {

        ChalakUtils.validateEntityForLogin(chalakDto);

        Map<String,Object> response=new HashMap<>();
        response.put("status", false);
        response.put("message","");
        Chalak chalak=chalakRepository.findByMobileActive(chalakDto.getMobile()).orElseGet(null);
        validateChalak(chalak);
        if(otpService.sendOtpWithTimeOut(chalak.getMobile(), chalak.getId(), OtpType.CHALAK_LOGIN, LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5))) {
            response.put("status", true);
            response.put("message","Otp Send Successfully");
        }else {
            response.put("status",false);
            response.put("message","failed to send Otp");
        }
        return response;
    }

    @Override
    public Map<String, Object> verifyOTP(ChalakDto chalakDto) {
        Map<String,Object> response=new HashMap<>();
        ChalakUtils.validateEntityForVerifyOTP(chalakDto);

        Chalak chalak=chalakRepository.findByMobileActive(chalakDto.getMobile()).orElseGet(null);
        validateChalak(chalak);

        if(otpService.verifyOtp(chalakDto.getMobile(), chalak.getId(), chalakDto.getOtp(), OtpType.CHALAK_LOGIN)) {
            response.put("status",true);
            response.put("message","Login Successfully");
            return response;
        }
        return response;
    }

    @Override
    public Map<String, Object> chalakOrder(String name, int pageNo) {

        String chalakId= AuthenticatedUserUtil.getAuthenticatedUserId();

        chalakId = StringUtils.isBlank(chalakId) ? 1+"" :chalakId;
        Sort sort = Sort.by(Sort.Order.desc("created_at"));
        Pageable pageable = PageRequest.of(pageNo, CHALAK_ORDER_PAGE_SIZE, sort);

        List<OrderRiderMapping> orders=orderRiderMappingRepository.findChalakOrder(Integer.valueOf(chalakId),pageable);

        List<ChalakOrderDTO> currentOrders=new ArrayList<>();
        List<ChalakOrderDTO> previousOrders=new ArrayList<>();

        for(OrderRiderMapping orm:orders) {
            Order order=orm.getOrder();
            User user= order.getUser();

            Address address=order.getAddress();
            ChalakOrderDTO dto=new ChalakOrderDTO();
            dto.setOrderId("ORD#"+order.getId());
            dto.setCustomerName(user.getName());
            dto.setAddress(address.getAddress());
            dto.setLang(address.getLang());
            dto.setLat(address.getLat());
            dto.setPaymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null);
            dto.setOrderStatus(order.getOrderStatus().name());
            dto.setAmount(order.getTotalAmount()+"");
            dto.setOrderStatus(order.getOrderStatus().name());

            if(order.getOrderStatus().equals(OrderStatus.PENDING)
                    || order.getOrderStatus().equals(OrderStatus.PENDING)
                    || order.getOrderStatus().equals(OrderStatus.ON_THE_WAY)
                    || order.getOrderStatus().equals(OrderStatus.REACHED) )   {
                currentOrders.add(dto);
            }else {
                previousOrders.add(dto);
            }

        }

        Map<String,Object> response=new HashMap<>();

        response.put("current",currentOrders);
        response.put("prev",previousOrders);


        return response;
    }

    @Override
    public Map<String, Object> updateOrderStatus(Integer orderId) {

        Map<String,Object> response=new HashMap<>();
        response.put("status",false);
        response.put("message","");

        String chalakId = AuthenticatedUserUtil.getAuthenticatedUserId();
        OrderRiderMapping orm = orderRiderMappingRepository.findByOrderIdAndRiderId(orderId, chalakId).orElseGet(null);
        if (orm != null) {
            Order order = orm.getOrder();

            OrderStatus currStatus = order.getOrderStatus();

            if (currStatus == OrderStatus.ACCEPTED
                    || currStatus == OrderStatus.ON_THE_WAY
                    || currStatus == OrderStatus.REACHED) {
                OrderStatus nextStatus = orderService.getNextStatus(order);
                order.setOrderStatus(nextStatus);
            }

            orderService.save(order);
            response.put("status",true);
            response.put("message","Done");
        }
        return response;
    }


    private void validateChalak(Chalak chalak) {
        if(chalak == null || chalak.isBlocked() || !chalak.isActive() ||
                !chalak.isVerified()) {
            throw new RuntimeException("Invalid chalak");
        }
    }

    private boolean isValidToEnable(Chalak chalak) {
        if(chalak == null) {
            return false;
        }
        if(chalak.isBlocked()){
            return false;
        }
        if(chalak.getDeletedOn() != null) {
            return false;
        }
        return true;
    }

    private boolean isValidchalakForRegistration(Chalak chalak) {

        if(chalak == null)  {
            return false;
        }

        if(!chalak.isActive() && !chalak.isBlocked() && !chalak.isVerified()) {
            return true;
        }

        return false;

    }

    private void sendMessage(Chalak entity, String otp) {
        System.out.println("sending otp "+otp+" to "+entity.getMobile());
        String message="your Registration OTP is: "+otp;
        smsService.sendSMS(message,entity.getMobile());
    }
}
