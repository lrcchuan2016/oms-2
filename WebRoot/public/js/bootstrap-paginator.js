//(function($) {
//	"use strict";
//	var BootstrapPaginator = function(element, options) {
//		this.init(element, options)
//	}, old = null;
//	BootstrapPaginator.prototype = {
//		init : function(element, options) {
//			this.$element = $(element);
//			this.currentPage = 1;
//			this.lastPage = 1;
//			this.setOptions(options);      //设置翻页导航
//			this.initialized = true;
//			
//		},
//		setOptions : function(options) {
//			this.options = $
//					.extend({},
//							(this.options || $.fn.bootstrapPaginator.defaults),
//							options);
//			this.totalPages = parseInt(this.options.totalPages, 10);
//			this.totalCount = parseInt(this.options.totalCount, 10);
//			this.numberOfPages = parseInt(this.options.numberOfPages, 10);
//			
//			if (options && typeof (options.currentPage) !== 'undefined') {
//				this.setCurrentPage(options.currentPage)
//			}
//			this.listen();
//			this.render();
//			if (!this.initialized && this.lastPage !== this.currentPage) {
//				this.$element.trigger("page-changed", [ this.lastPage,
//						this.currentPage ])
//			}
//		},
//		listen : function() {
//			this.$element.off("page-clicked");
//			this.$element.off("page-changed");
//			if (typeof (this.options.onPageClicked) === "function") {
//				this.$element.bind("page-clicked", this.options.onPageClicked)
//			}
//			if (typeof (this.options.onPageChanged) === "function") {
//				this.$element.on("page-changed", this.options.onPageChanged)
//			}
//		},
//		destroy : function() {
//			this.$element.off("page-clicked");
//			this.$element.off("page-changed");
//			$.removeData(this.$element, 'bootstrapPaginator');
//			this.$element.empty()
//		},
//		show : function(page) {
//			this.setCurrentPage(page);
//			this.render();
//			if (this.lastPage !== this.currentPage) {
//				this.$element.trigger("page-changed", [ this.lastPage,
//						this.currentPage ])
//			}
//		},
//		showNext : function() {
//			var pages = this.getPages();
//			if (pages.next) {
//				this.show(pages.next)
//			}
//		},
//		showPrevious : function() {
//			var pages = this.getPages();
//			if (pages.prev) {
//				this.show(pages.prev)
//			}
//		},
//		showFirst : function() {
//			var pages = this.getPages();
//			if (pages.first) {
//				this.show(pages.first)
//			}
//		},
//		showLast : function() {
//			var pages = this.getPages();
//			if (pages.last) {
//				this.show(pages.last)
//			}
//		},
//		onPageItemClicked : function(event) {
//			var type = event.data.type, page = event.data.page;
//			this.$element.trigger("page-clicked", [ event, type, page ]);
//			switch (type) {
//			case "first":
//				this.showFirst();
//				break;
//			case "prev":
//				this.showPrevious();
//				break;
//			case "next":
//				this.showNext();
//				break;
//			case "last":
//				this.showLast();
//				break;
//			case "page":
//				this.show(page);
//				break
//			}
//		},
//		//扩展(跳页面)
//		onPageItemSkiped : function(event){
//			var element = event.data.element;
//			element.attr({"data-valid":"[{rule:'not_empty'},{rule:'positive'},{rule:'max_value','value':"+this.totalPages+"}]"});
//			var formInput = element.parent().inputValid(valid_message_options);
//			if(formInput.validate_all()){
//				var page = parseInt(element.val());
//				this.$element.trigger("page-clicked", [ event, "page", page ]);
//				//this.show(page);
//			}
//				
//		},
//		render : function() {
//			
//			var containerClass = this.getValueFromOption(
//					this.options.containerClass, this.$element), size = this.options.size
//					|| "normal", alignment = this.options.alignment || "left", 
//					pages = this.getPages(), listContainer = $("<ul></ul>"), 
//					skipInputContainer = $("<input>"),
//					skipBtnContainer = $("<a></a>"),
//					listContainerClass = this.getValueFromOption(this.options.listContainerClass,
//							listContainer), 
//					//加入总页码与总记录数
//					numberContainer = $("<div><span>共"+this.totalPages+"页</span><span>&nbsp;"+this.totalCount+"条</span></div>"),   //添加页数、总记录数	
//					first = null, prev = null, next = null, last = null, p = null, i = 0;
//			numberContainer.css({"margin":"25px 20px"});
//			listContainer.prop("class", "");
//			listContainer.addClass("pagination");
//			//跳转页面（输入框与按钮组合）
//			skipInputContainer.addClass("pagination").css({width:"50px"}).attr({placeholder:"页码"});
//			skipBtnContainer.addClass("btn pagination").html("GO")
//			.on("click",null,{element:skipInputContainer},$.proxy(this.onPageItemSkiped,this));
//			
//			switch (size.toLowerCase()) {
//			case "large":
//				listContainer.addClass("pagination-lg");
//				skipInputContainer.addClass("pagination-lg");
//				skipBtnContainer.addClass("pagination-lg");
//				break;
//			case "small":
//				listContainer.addClass("pagination-sm");
//				skipInputContainer.addClass("pagination-sm");
//				skipBtnContainer.addClass("pagination-sm");
//				break;
//			case "mini":
//				listContainer.addClass("pagination-xs");
//				skipInputContainer.addClass("pagination-xs");
//				skipBtnContainer.addClass("pagination-xs");
//				break;
//			default:
//				break
//			}
//			switch (alignment.toLowerCase()) {
//			case "center":
//				listContainer.addClass("pagination-centered");
//				break;
//			case "right":
//				listContainer.addClass("pagination-right");
//				break;
//			default:
//				break
//			}
//			this.$element.addClass(containerClass);
//			this.$element.empty();
//			//创建div，包裹①页码与②导航页码
//			var $div = $("<div></div>");      
//			$div.css("display","inline-flex");
//			$div.append(numberContainer);
//			$div.append(listContainer);
//			//////
//			if(this.options.skipBtnShow && this.totalPages>this.numberOfPages){
//				$div.append(skipInputContainer);
//				$div.append(skipBtnContainer);
//			}
//			//////
//			
//			this.$element.append($div);
//			
//			listContainer.addClass(listContainerClass);
//			this.pageRef = [];
//			if (pages.first) {
//				first = this.buildPageItem("first", pages.first);
//				if (first) {
//					listContainer.append(first)
//				}
//			}
//			if (pages.prev) {
//				prev = this.buildPageItem("prev", pages.prev);
//				if (prev) {
//					listContainer.append(prev)
//				}
//			}
//			for (i = 0; i < pages.length; i = i + 1) {
//				p = this.buildPageItem("page", pages[i]);
//				if (p) {
//					listContainer.append(p)
//				}
//			}
//			if (pages.next) {
//				next = this.buildPageItem("next", pages.next);
//				if (next) {
//					listContainer.append(next)
//				}
//			}
//			if (pages.last) {
//				last = this.buildPageItem("last", pages.last);
//				if (last) {
//					listContainer.append(last)
//				}
//			}
//		},
//		buildPageItem : function(type, page) {
//			var itemContainer = $("<li></li>"), itemContent = $("<a></a>"), text = "", title = "", itemContainerClass = this.options
//					.itemContainerClass(type, page, this.currentPage), itemContentClass = this
//					.getValueFromOption(this.options.itemContentClass, type,
//							page, this.currentPage), tooltipOpts = null;
//			switch (type) {
//			case "first":
//				if (!this.getValueFromOption(this.options.shouldShowPage, type,
//						page, this.currentPage)) {
//					return
//				}
//				text = this.options.itemTexts(type, page, this.currentPage);
//				title = this.options
//						.tooltipTitles(type, page, this.currentPage);
//				break;
//			case "last":
//				if (!this.getValueFromOption(this.options.shouldShowPage, type,
//						page, this.currentPage)) {
//					return
//				}
//				text = this.options.itemTexts(type, page, this.currentPage);
//				title = this.options
//						.tooltipTitles(type, page, this.currentPage);
//				break;
//			case "prev":
//				if (!this.getValueFromOption(this.options.shouldShowPage, type,
//						page, this.currentPage)) {
//					return
//				}
//				text = this.options.itemTexts(type, page, this.currentPage);
//				title = this.options
//						.tooltipTitles(type, page, this.currentPage);
//				break;
//			case "next":
//				if (!this.getValueFromOption(this.options.shouldShowPage, type,
//						page, this.currentPage)) {
//					return
//				}
//				text = this.options.itemTexts(type, page, this.currentPage);
//				title = this.options
//						.tooltipTitles(type, page, this.currentPage);
//				break;
//			case "page":
//				if (!this.getValueFromOption(this.options.shouldShowPage, type,
//						page, this.currentPage)) {
//					return
//				}
//				text = this.options.itemTexts(type, page, this.currentPage);
//				title = this.options
//						.tooltipTitles(type, page, this.currentPage);
//				break
//			}
//			itemContainer.addClass(itemContainerClass).append(itemContent);
//			itemContent.addClass(itemContentClass).html(text).on("click", null,
//					{
//						type : type,
//						page : page
//					}, $.proxy(this.onPageItemClicked, this));
//			
//			if (this.options.pageUrl) {
//				itemContent.attr("href", this.getValueFromOption(
//						this.options.pageUrl, type, page, this.currentPage))
//			}
//			if (this.options.useBootstrapTooltip) {
//				tooltipOpts = $.extend({},
//						this.options.bootstrapTooltipOptions, {
//							title : title
//						});
//				itemContent.tooltip(tooltipOpts)
//			} else {
//				itemContent.attr("title", title)
//			}
//			itemContent.attr("href", 'javascript:void(null);');
//			return itemContainer
//		},
//		setCurrentPage : function(page) {
//			if (page > this.totalPages || page < 1) {
//				throw "Page out of range";
//			}
//			this.lastPage = this.currentPage;
//			this.currentPage = parseInt(page, 10)
//		},
//		getPages : function() {
//			var totalPages = this.totalPages, pageStart = (this.currentPage
//					% this.numberOfPages === 0) ? (parseInt(this.currentPage
//					/ this.numberOfPages, 10) - 1)
//					* this.numberOfPages + 1 : parseInt(this.currentPage
//					/ this.numberOfPages, 10)
//					* this.numberOfPages + 1, output = [], i = 0, counter = 0;
//			pageStart = pageStart < 1 ? 1 : pageStart;
//			for (i = pageStart, counter = 0; counter < this.numberOfPages
//					&& i <= totalPages; i = i + 1, counter = counter + 1) {
//				output.push(i)
//			}
//			if (this.currentPage > 1) {
//				output.first = 1
//			} else {
//				output.first = null
//			}
//			if (this.currentPage > 1) {
//				output.prev = this.currentPage - 1
//			} else {
//				output.prev = null
//			}
//			if (this.currentPage < totalPages) {
//				output.next = this.currentPage + 1
//			} else {
//				output.next = null
//			}
//			if (this.currentPage < totalPages) {
//				output.last = totalPages
//			} else {
//				output.last = null
//			}
//			output.current = this.currentPage;
//			output.total = totalPages;
//			output.numberOfPages = this.options.numberOfPages;
//			return output
//		},
//		getValueFromOption : function(value) {
//			var output = null, args = Array.prototype.slice.call(arguments, 1);
//			if (typeof value === 'function') {
//				output = value.apply(this, args)
//			} else {
//				output = value
//			}
//			return output
//		}
//	};
//	old = $.fn.bootstrapPaginator;
//	$.fn.bootstrapPaginator = function(option) {
//		var args = arguments, result = null;
//		$(this)
//				.each(
//						function(index, item) {
//							var $this = $(item), data = $this
//									.data('bootstrapPaginator'), options = (typeof option !== 'object') ? null
//									: option;
//							if (!data) {
//								$this.data('bootstrapPaginator',
//										(data = new BootstrapPaginator(this,
//												options)));
//								return
//							}
//							if (typeof option === 'string') {
//								if (data[option]) {
//									result = data[option]
//											.apply(data, Array.prototype.slice
//													.call(args, 1))
//								} else {
//									throw "Method " + option
//											+ " does not exist";
//								}
//							} else {
//								result = data.setOptions(option)
//							}
//						});
//		return result
//	};
//	$.fn.bootstrapPaginator.defaults = {
//		containerClass : "",
//		size : "normal",
//		alignment : "left",
//		listContainerClass : "",
//		itemContainerClass : function(type, page, current) {
//			return (page === current) ? "active" : ""
//		},
//		itemContentClass : function(type, page, current) {
//			return ""
//		},
//		currentPage : 1,
//		numberOfPages : 5,
//		totalPages : 1,
//		pageUrl : function(type, page, current) {
//			return null
//		},
//		onPageClicked : null,
//		onPageChanged : null,
//		useBootstrapTooltip : false,
//		shouldShowPage : true,
//		skipBtnShow : true,
//		itemTexts : function(type, page, current) {
//			switch (type) {
//			case "first":
//				return "&lt;&lt;";
//			case "prev":
//				return "&lt;";
//			case "next":
//				return "&gt;";
//			case "last":
//				return "&gt;&gt;";
//			case "page":
//				return page
//			}
//		},
//		tooltipTitles : function(type, page, current) {
//			switch (type) {
//			case "first":
//				return "Go to first page";
//			case "prev":
//				return "Go to previous page";
//			case "next":
//				return "Go to next page";
//			case "last":
//				return "Go to last page";
//			case "page":
//				return (page === current) ? "Current page is " + page
//						: "Go to page " + page
//			}
//		},
//		bootstrapTooltipOptions : {
//			animation : true,
//			html : true,
//			placement : 'top',
//			selector : false,
//			title : "",
//			container : false
//		}
//	};
//	$.fn.bootstrapPaginator.Constructor = BootstrapPaginator
//}(window.jQuery));
//
(function($){var BootstrapPaginator=function(element,options){this.init(element,options)},old=null;BootstrapPaginator.prototype={init:function(element,options){this.$element=$(element);this.currentPage=1;this.lastPage=1;this.setOptions(options);this.initialized=true},setOptions:function(options){this.options=$.extend({},(this.options||$.fn.bootstrapPaginator.defaults),options);this.totalPages=parseInt(this.options.totalPages,10);this.totalCount=parseInt(this.options.totalCount,10);this.numberOfPages=parseInt(this.options.numberOfPages,10);if(options&&typeof(options.currentPage)!=="undefined"){this.setCurrentPage(options.currentPage)}this.listen();this.render();if(!this.initialized&&this.lastPage!==this.currentPage){this.$element.trigger("page-changed",[this.lastPage,this.currentPage])}},listen:function(){this.$element.off("page-clicked");this.$element.off("page-changed");if(typeof(this.options.onPageClicked)==="function"){this.$element.bind("page-clicked",this.options.onPageClicked)}if(typeof(this.options.onPageChanged)==="function"){this.$element.on("page-changed",this.options.onPageChanged)}},destroy:function(){this.$element.off("page-clicked");this.$element.off("page-changed");$.removeData(this.$element,"bootstrapPaginator");this.$element.empty()},show:function(page){this.setCurrentPage(page);this.render();if(this.lastPage!==this.currentPage){this.$element.trigger("page-changed",[this.lastPage,this.currentPage])}},showNext:function(){var pages=this.getPages();if(pages.next){this.show(pages.next)}},showPrevious:function(){var pages=this.getPages();if(pages.prev){this.show(pages.prev)}},showFirst:function(){var pages=this.getPages();if(pages.first){this.show(pages.first)}},showLast:function(){var pages=this.getPages();if(pages.last){this.show(pages.last)}},onPageItemClicked:function(event){var type=event.data.type,page=event.data.page;this.$element.trigger("page-clicked",[event,type,page]);switch(type){case"first":this.showFirst();break;case"prev":this.showPrevious();break;case"next":this.showNext();break;case"last":this.showLast();break;case"page":this.show(page);break}},onPageItemSkiped:function(event){var element=event.data.element;element.attr({"data-valid":"[{rule:'not_empty'},{rule:'positive'},{rule:'max_value','value':"+this.totalPages+"}]"});var formInput=element.parent().inputValid(valid_message_options);if(formInput.validate_all()){var page=parseInt(element.val());this.$element.trigger("page-clicked",[event,"page",page])}},render:function(){var containerClass=this.getValueFromOption(this.options.containerClass,this.$element),size=this.options.size||"normal",alignment=this.options.alignment||"left",pages=this.getPages(),listContainer=$("<ul></ul>"),skipInputContainer=$("<input>"),skipBtnContainer=$("<a></a>"),listContainerClass=this.getValueFromOption(this.options.listContainerClass,listContainer),numberContainer=$("<div><span>共"+this.totalPages+"页</span><span>&nbsp;"+this.totalCount+"条</span></div>"),first=null,prev=null,next=null,last=null,p=null,i=0;numberContainer.css({"margin":"25px 20px"});listContainer.prop("class","");listContainer.addClass("pagination");skipInputContainer.addClass("pagination").css({width:"50px"}).attr({placeholder:"页码"});skipBtnContainer.addClass("btn pagination").html("GO").on("click",null,{element:skipInputContainer},$.proxy(this.onPageItemSkiped,this));switch(size.toLowerCase()){case"large":listContainer.addClass("pagination-lg");skipInputContainer.addClass("pagination-lg");skipBtnContainer.addClass("pagination-lg");break;case"small":listContainer.addClass("pagination-sm");skipInputContainer.addClass("pagination-sm");skipBtnContainer.addClass("pagination-sm");break;case"mini":listContainer.addClass("pagination-xs");skipInputContainer.addClass("pagination-xs");skipBtnContainer.addClass("pagination-xs");break;default:break}switch(alignment.toLowerCase()){case"center":listContainer.addClass("pagination-centered");break;case"right":listContainer.addClass("pagination-right");break;default:break}this.$element.addClass(containerClass);this.$element.empty();var $div=$("<div></div>");$div.css("display","inline-flex");$div.append(numberContainer);$div.append(listContainer);if(this.options.skipBtnShow&&this.totalPages>this.numberOfPages){$div.append(skipInputContainer);$div.append(skipBtnContainer)}this.$element.append($div);listContainer.addClass(listContainerClass);this.pageRef=[];if(pages.first){first=this.buildPageItem("first",pages.first);if(first){listContainer.append(first)}}if(pages.prev){prev=this.buildPageItem("prev",pages.prev);if(prev){listContainer.append(prev)}}for(i=0;i<pages.length;i=i+1){p=this.buildPageItem("page",pages[i]);if(p){listContainer.append(p)}}if(pages.next){next=this.buildPageItem("next",pages.next);if(next){listContainer.append(next)}}if(pages.last){last=this.buildPageItem("last",pages.last);if(last){listContainer.append(last)}}},buildPageItem:function(type,page){var itemContainer=$("<li></li>"),itemContent=$("<a></a>"),text="",title="",itemContainerClass=this.options.itemContainerClass(type,page,this.currentPage),itemContentClass=this.getValueFromOption(this.options.itemContentClass,type,page,this.currentPage),tooltipOpts=null;
switch(type){case"first":if(!this.getValueFromOption(this.options.shouldShowPage,type,page,this.currentPage)){return}text=this.options.itemTexts(type,page,this.currentPage);title=this.options.tooltipTitles(type,page,this.currentPage);break;case"last":if(!this.getValueFromOption(this.options.shouldShowPage,type,page,this.currentPage)){return}text=this.options.itemTexts(type,page,this.currentPage);title=this.options.tooltipTitles(type,page,this.currentPage);break;case"prev":if(!this.getValueFromOption(this.options.shouldShowPage,type,page,this.currentPage)){return}text=this.options.itemTexts(type,page,this.currentPage);title=this.options.tooltipTitles(type,page,this.currentPage);break;case"next":if(!this.getValueFromOption(this.options.shouldShowPage,type,page,this.currentPage)){return}text=this.options.itemTexts(type,page,this.currentPage);title=this.options.tooltipTitles(type,page,this.currentPage);break;case"page":if(!this.getValueFromOption(this.options.shouldShowPage,type,page,this.currentPage)){return}text=this.options.itemTexts(type,page,this.currentPage);title=this.options.tooltipTitles(type,page,this.currentPage);break}itemContainer.addClass(itemContainerClass).append(itemContent);itemContent.addClass(itemContentClass).html(text).on("click",null,{type:type,page:page},$.proxy(this.onPageItemClicked,this));if(this.options.pageUrl){itemContent.attr("href",this.getValueFromOption(this.options.pageUrl,type,page,this.currentPage))}if(this.options.useBootstrapTooltip){tooltipOpts=$.extend({},this.options.bootstrapTooltipOptions,{title:title});itemContent.tooltip(tooltipOpts)}else{itemContent.attr("title",title)}itemContent.attr("href","javascript:void(null);");return itemContainer},setCurrentPage:function(page){if(page>this.totalPages||page<1){throw"Page out of range"}this.lastPage=this.currentPage;this.currentPage=parseInt(page,10)},getPages:function(){var totalPages=this.totalPages,pageStart=(this.currentPage%this.numberOfPages===0)?(parseInt(this.currentPage/this.numberOfPages,10)-1)*this.numberOfPages+1:parseInt(this.currentPage/this.numberOfPages,10)*this.numberOfPages+1,output=[],i=0,counter=0;pageStart=pageStart<1?1:pageStart;for(i=pageStart,counter=0;counter<this.numberOfPages&&i<=totalPages;i=i+1,counter=counter+1){output.push(i)}if(this.currentPage>1){output.first=1}else{output.first=null}if(this.currentPage>1){output.prev=this.currentPage-1}else{output.prev=null}if(this.currentPage<totalPages){output.next=this.currentPage+1}else{output.next=null}if(this.currentPage<totalPages){output.last=totalPages}else{output.last=null}output.current=this.currentPage;output.total=totalPages;output.numberOfPages=this.options.numberOfPages;return output},getValueFromOption:function(value){var output=null,args=Array.prototype.slice.call(arguments,1);if(typeof value==="function"){output=value.apply(this,args)}else{output=value}return output}};old=$.fn.bootstrapPaginator;$.fn.bootstrapPaginator=function(option){var args=arguments,result=null;$(this).each(function(index,item){var $this=$(item),data=$this.data("bootstrapPaginator"),options=(typeof option!=="object")?null:option;if(!data){$this.data("bootstrapPaginator",(data=new BootstrapPaginator(this,options)));return}if(typeof option==="string"){if(data[option]){result=data[option].apply(data,Array.prototype.slice.call(args,1))}else{throw"Method "+option+" does not exist"}}else{result=data.setOptions(option)}});return result};$.fn.bootstrapPaginator.defaults={containerClass:"",size:"normal",alignment:"left",listContainerClass:"",itemContainerClass:function(type,page,current){return(page===current)?"active":""},itemContentClass:function(type,page,current){return""},currentPage:1,numberOfPages:5,totalPages:1,pageUrl:function(type,page,current){return null},onPageClicked:null,onPageChanged:null,useBootstrapTooltip:false,shouldShowPage:true,skipBtnShow:true,itemTexts:function(type,page,current){switch(type){case"first":return"&lt;&lt;";case"prev":return"&lt;";case"next":return"&gt;";case"last":return"&gt;&gt;";case"page":return page}},tooltipTitles:function(type,page,current){switch(type){case"first":return"Go to first page";case"prev":return"Go to previous page";case"next":return"Go to next page";case"last":return"Go to last page";case"page":return(page===current)?"Current page is "+page:"Go to page "+page}},bootstrapTooltipOptions:{animation:true,html:true,placement:"top",selector:false,title:"",container:false}};$.fn.bootstrapPaginator.Constructor=BootstrapPaginator}(window.jQuery));