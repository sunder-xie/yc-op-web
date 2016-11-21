<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>已完成订单列表</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    	<div class="form-label">
		                    	<!--查询条件-->
		                    	<ul>
		                    		<li class="col-md-6">
							            <p class="word">订单编号</p>
							            <p><input class="int-text int-medium"  id="orderId" type="text"></p>
						            </li>
		                    		<li class="col-md-6">
							            <p class="word">昵称</p>
							            <p><input class="int-text int-medium" id="nickName" type="text"></p>
						            </li>
		                    	</ul>
		                    	<ul>
							         <li class="col-md-6">
							            <p class="word">订单类型</p>
							            <p>
						            		<select class="select select-medium" id="orderType">
						            			<option value="">全部</option>
						            		</select>
					            		</p>
					            		<p>
					            			高级搜索<a href="javascript:void(0);"><i class="fa fa-caret-down" id="showQuery"></i></a>
							         	</p>
							         </li>
		                    	</ul>
		                    	<div id="selectDiv" style="display:none;">
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">下单开始时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeBegin" name="orderTimeBegin"/>
							                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">下单结束时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeEnd" name="orderTimeEnd"/>
							                     <span class="time"><i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li> 
						            	</ul>
			                    	</div>
						            <ul>
			                    		<li class="col-md-6">
								            <p class="word">订单来源</p>
								            <p>
							            		<select class="select select-medium" id="orderSource">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
							            	<p class="word">语种方向</p>
						            		<p>
							            		<select class="select select-medium" id="langugePaire">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							         	</li>
		                    		</ul>
		                    		<ul>
		                    			 <li class="col-md-6">
							            	<p class="word">译员昵称</p>
							            	<p><input class="int-text int-medium" id="interperName" type="text"></p>
						            	</li>
		                    		</ul>
		                    	</div> 
	                    		<ul>
									<li class="width-xlag">
										<p class="word">&nbsp;&nbsp;&nbsp;</p>
										<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="search" value="查  询"></p>
										<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												   id="export" value="导  出"></p>
									</li>
								</ul>
					         </div>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h5 class="pull-left">订单列表</h5>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
                                            	<th>订单来源</th>
                                                <th>订单类型</th>
                                                <th>订单编号</th>
                                                <th>下单时间</th>
                                                <th>昵称</th>
                                                <th>语种方向</th>
                                                <th>翻译级别</th>
                                                <th>订单级别</th>
                                                <th>订单金额</th>
                                                <th>译员昵称</th>
                                                <th>领取时间</th>
                                                <th>提交时间</th>
                                                <th>确认时间</th>
                                                <th>订单状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                         <tbody id="orderListData"></tbody>
                                    </table>
                                    <div id="showMessage"></div>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
								 <div>
					 				 <nav style="text-align: center">
										<ul id="pagination"></ul>
									</nav>
								  </div>
								<!--分页结束-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="orderListTemple" type="text/template">  
{{if levelSize>=extendSize && levelSize!=0 && extendSize!=0}}
	{{for ordTransLevelList ~chlIdPage=chlIdPage ~orderTypePage=orderTypePage  ~orderId=orderId
		~orderTime=orderTime ~totalFeePage=totalFeePage ~lockTime=lockTime ~finishTime=finishTime
		 ~remainingTime=remainingTime  ~statePage=statePage  ~levelSize=levelSize ~interperName=interperName
		~ordProdExtendList=ordProdExtendList ~extendSize=extendSize ~orderLevelPage=orderLevelPage
	}}
		<tr>
			{{if #index ==0}}
				<td rowspan="{{:~levelSize}}">{{:~chlIdPage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~orderTypePage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~orderId}}</td>
				<td rowspan="{{:~levelSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~orderTime)}}</td>
				<td rowspan="{{:~levelSize}}">{{:~userName}}</td>
				<td rowspan="{{:~levelSize}}">
					 <table width="100%" height="100%">
							{{if ~ordProdExtendList!=null}}
								{{for ~ordProdExtendList}}
      								<tr>
      									<td>{{:langungePairChName}}</td>	
      								</tr>
								{{/for}}
							{{/if}}
      				</table>
				</td>	
			{{/if}}
			<td>{{:translateLevelPage}}</td>
			{{if #index ==0 }}
				<td rowspan="{{:~levelSize}}">{{:~orderLevelPage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~totalFeePage}}</td>
 				<td rowspan="{{:~levelSize}}">{{:~interperName}}</td>
				<td rowspan="{{:~levelSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~lockTime)}}</td>
				<td rowspan="{{:~levelSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~endTime)}}</td>
				<td rowspan="{{:~levelSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~finishTime)}}</td>
				<td rowspan="{{:~levelSize}}">{{:~statePage}}</td>
				<td rowspan="{{:~levelSize}}"><a  href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">查看</a></td>
			{{/if}}	
		</tr>		
	{{/for}}
{{/if}}
{{if levelSize<extendSize}}
	{{for ordProdExtendList ~chlIdPage=chlIdPage ~orderTypePage=orderTypePage  ~orderId=orderId
		~orderTime=orderTime ~totalFeePage=totalFeePage ~lockTime=lockTime ~finishTime=finishTime
		 ~remainingTime=remainingTime  ~statePage=statePage  ~levelSize=levelSize ~interperName=interperName
		~ordTransLevelList=ordTransLevelList ~extendSize=extendSize ~orderLevelPage=orderLevelPage
	}}
		<tr>
			{{if #index ==0}}
				<td rowspan="{{:~extendSize}}">{{:~chlIdPage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~orderTypePage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~orderId}}</td>
				<td rowspan="{{:~extendSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~orderTime)}}</td>
				<td rowspan="{{:~extendSize}}">{{:~userName}}</td>
			{{/if}}
			<td>{{:langungePairChName}}</td>
			{{if #index ==0 }}
				<td rowspan="{{:~extendSize}}">
					 <table width="100%" height="100%">
							{{if ~ordTransLevelList!=null}}
								{{for ~ordTransLevelList}}
      								<tr>
      									<td >{{:translateLevelPage}}</td>	
      								</tr>
								{{/for}}
							{{/if}}
      				</table>
				</td>	
				<td rowspan="{{:~extendSize}}">{{:~orderLevelPage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~totalFeePage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~interperName}}</td>
				<td rowspan="{{:~extendSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~lockTime)}}</td>
				<td rowspan="{{:~extendSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~endTime)}}</td>
				<td rowspan="{{:~extendSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~finishTime)}}</td>
				<td rowspan="{{:~extendSize}}">{{:~statePage}}</td>
				<td rowspan="{{:~extendSize}}"><a  href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">查看</a></td>
			{{/if}}	
		</tr>		
	{{/for}}
{{/if}}
  {{if levelSize===0 && extendSize===0}}
	<tr>
	<td>{{:chlIdPage}}</td>
	<td>{{:orderTypePage}}</td>
	<td>{{:orderId}}</td>
	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
	<td>{{:userName}}</td>
	<td></td>
	<td></td>
	<td>{{:orderLevelPage}}</td>
	<td>{{:totalFeePage}}</td>
	<td>{{:interperName}}</td>
	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', lockTime)}}</td>
	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', endTime)}}</td>
	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', finishTime)}}</td>
	<td>{{:statePage}}</td>
	<td><a  href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}')">查看</a></td>	
	</tr>			
{{/if}}                                           
</script> 
  <script type="text/javascript">
  <%-- 展示日历 --%>
	$('#orderTimeDiv').delegate('.fa-calendar','click',function(){
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		console.log("click calendar "+timeId);
		WdatePicker({el:timeId,readOnly:true});
	});
	$('#payTimeDiv').delegate('.fa-calendar','click',function(){
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		console.log("click calendar "+timeId);
		WdatePicker({el:timeId,readOnly:true});
	});
	var pager;
	(function () {
		seajs.use('app/jsp/order/doneOrderList', function (DoneOrderListPager) {
			pager = new DoneOrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>