<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container self-home-page">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <div class="row self-info">
          	<div class="col-xs-6 col-sm-3">
          		<img alt="" src="../../images/selfPicture/2.png"></img>
          	</div>
          	<div class="col-xs-12 col-sm-9">
          		<h1>{{user.email}}</h1>
          		<dl class="dl-horizontal">
				  <dt>目前就职</dt><dd>药明康德新药开发有限公司</dd>
				  <dt>目前职位</dt><dd>资深化学家</dd>
				  <dt>教育信息</dt><dd>剑桥大学药学博士</dd>
				  <dt>我的签名</dt><dd>爱生活，爱工作。</dd>
				</dl>
          	</div>
          </div>
          <div class="row">
          	<ul id="self-home-page-tab" class="nav nav-tabs">
			    <li class="active">
			    		<a data-target="#attention-question" data-toggle="tab">关注的问题 <span class="badge">3</span></a>
			    </li>
			    <li><a data-target="#my-question" data-toggle="tab">我的提问 <span class="badge">1万+</span></a></li>
			    <li><a data-target="#my-answer" data-toggle="tab">我的问答 <span class="badge">7</span></a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
			    <div class="tab-pane fade in active" id="attention-question">
			    		<topic-list></topic-list>
			    </div>
			    <div class="tab-pane fade" id="my-question">
			   	 	<topic-list></topic-list>
			    </div>
			    <div class="tab-pane fade" id="my-answer">
			        <topic-list></topic-list>
			    </div>
			</div>
          </div><!--/row-->
        </div><!--/.col-xs-12.col-sm-9-->

        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
          <hot-tag-siderbar topic="ttttopic"></hot-tag-siderbar>
		  <hot-superstar-siderbar star="str"></hot-superstar-siderbar>
        </div><!--/.sidebar-offcanvas-->
      </div><!--/row-->

 </div>