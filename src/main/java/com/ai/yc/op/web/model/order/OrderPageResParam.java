package com.ai.yc.op.web.model.order;

import com.ai.yc.order.api.orderquery.param.OrdOrderVo;

public class OrderPageResParam extends OrdOrderVo {
	private static final long serialVersionUID = 1L;
	private String chlIdPage;
	private String orderTypePage;
	private String payStylePage;
	private String statePage;
	private String totalFeePage;
	private String cancelTypePage;

	private String orderLevelPage;

	public String getChlIdPage() {
		return chlIdPage;
	}

	public void setChlIdPage(String chlIdPage) {
		this.chlIdPage = chlIdPage;
	}

	public String getOrderTypePage() {
		return orderTypePage;
	}

	public void setOrderTypePage(String orderTypePage) {
		this.orderTypePage = orderTypePage;
	}

	public String getPayStylePage() {
		return payStylePage;
	}

	public void setPayStylePage(String payStylePage) {
		this.payStylePage = payStylePage;
	}

	public String getStatePage() {
		return statePage;
	}

	public void setStatePage(String statePage) {
		this.statePage = statePage;
	}

	public String getTotalFeePage() {
		return totalFeePage;
	}

	public void setTotalFeePage(String totalFeePage) {
		this.totalFeePage = totalFeePage;
	}

	public String getCancelTypePage() {
		return cancelTypePage;
	}

	public void setCancelTypePage(String cancelTypePage) {
		this.cancelTypePage = cancelTypePage;
	}

	public String getOrderLevelPage() {
		return orderLevelPage;
	}

	public void setOrderLevelPage(String orderLevelPage) {
		this.orderLevelPage = orderLevelPage;
	}

}
