package com.ai.yc.op.web.controller.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.op.web.model.order.OrdOrderDetails;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.LoginUtil;
import com.ai.yc.order.api.orderdetails.interfaces.IQueryOrderDetailsSV;
import com.ai.yc.order.api.orderdetails.param.QueryOrderDetailsRequest;
import com.ai.yc.order.api.orderdetails.param.QueryOrderDetailsResponse;
import com.ai.yc.order.api.orderrefund.interfaces.IOrderRefundSV;
import com.ai.yc.order.api.orderrefund.param.OrderRefundCheckRequest;
import com.ai.yc.order.api.orderrefund.param.OrderRefundResponse;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetResponse;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.SearchYCUserRequest;
import com.ai.yc.user.api.userservice.param.YCUserInfoResponse;
@Controller
public class RefundOrdDetailController {
	private static final Logger log = LoggerFactory.getLogger(OrdOrderController.class);
	public final static String ORDER_DETAILS_PAGE = "jsp/order/refundOrderDetail";
	@RequestMapping("/toRefundOrderDetail")
	 public ModelAndView toOrderDetailsPage(@RequestParam(value="mod",defaultValue="view")String mod,Long orderId,String isAll,String stateCheck) {
		 ModelAndView view = new ModelAndView(ORDER_DETAILS_PAGE);
		 view.addObject("model", mod);
		 view.addObject("orderId", orderId);
		 view.addObject("isAll", isAll);
		 view.addObject("stateCheck", stateCheck);
	     return view;
	 } 
	@RequestMapping("/refundOrdDetails")
	@ResponseBody
	public ResponseData<OrdOrderDetails> queryOrderDetails(Long orderId){
		IQueryOrderDetailsSV iQueryOrderDetailsSV = DubboConsumerFactory.getService(IQueryOrderDetailsSV.class);
		QueryOrderDetailsResponse resp = null;
		QueryOrderDetailsRequest request = new QueryOrderDetailsRequest();
		request.setOrderId(orderId);
		try {
			resp =iQueryOrderDetailsSV.queryOrderDetails(request);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		OrdOrderDetails details = new OrdOrderDetails();
		BeanUtils.copyProperties(details, resp);
		//调用用户中心
		installUserInfo(details);
		return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", details);
		
	}
	private void installUserInfo(OrdOrderDetails details){
		IYCUserServiceSV iYCUserServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
		if(!StringUtil.isBlank(details.getUserId())){
			SearchYCUserRequest searchYCUserReq = new SearchYCUserRequest();
			searchYCUserReq.setUserId(details.getUserId());
			try {
				YCUserInfoResponse user = iYCUserServiceSV.searchYCUserInfo(searchYCUserReq);
				details.setUsernick(user.getNickname());
			} catch (Exception e) {
				log.error("获取用户昵称失败", e);
			}
			IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
			UcMembersGetRequest membersGetRequest = new UcMembersGetRequest();
			membersGetRequest.setUsername(details.getUserId());
			membersGetRequest.setGetmode("1");
			try {	
				UcMembersGetResponse ucMember = ucMembersSV.ucGetMember(membersGetRequest);	
				Object username = ucMember.getDate().get("username");
				details.setUsername((String)username);
			}catch (Exception e) {
				log.error("获取用户名称信息失败", e);
			}
		}
		
	}
	/**
	 * 退款审核订单
	 */
	@RequestMapping("/refundCheck")
	@ResponseBody
    public ResponseData<Boolean> refundCheck(OrderRefundCheckRequest req){
		IOrderRefundSV orderRefundSV = DubboConsumerFactory.getService(IOrderRefundSV.class);
		OrderRefundResponse resp = null;
		try {
			GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
			req.setOperId(loginUser.getUserId());
			req.setOperName(loginUser.getLoginName());
			resp = orderRefundSV.refundCheck(req);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "订单审核成功", true);
		
	}
}
