package cn.broadin.oms.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PagePlugin {

	private int startIndex;   //起始
	private int pageSize;		//每页记录数
	private int totalCount;		//总记录数
	private int pageNum;		//显示的链接页数
	private int currentPageNum;  //当前页
	private String href;		//链接请求的地址
	private Map<String, String> param;	//参数
	
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public PagePlugin(int startIndex,int pageSize,int pageNum,int currentPageNum,int totalCount,String href,Map<String, String> param) {
		this.startIndex = startIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.href = href;
		this.param = param;
		this.currentPageNum = currentPageNum;
		this.pageNum = pageNum;
	}
	
	public String getPageLinkStr(){
		StringBuilder pageStr = new StringBuilder();
		int page = (int) Math.ceil(totalCount*1.0/pageSize);
		//页数大于1  显示分页导航
		if(page>1){
			//1、获得首页、上页、中心页、下页、末页
			pageStr.append(getPageHrefLink(page));
			
			//2、获得页数，总记录数String
			pageStr.append(getPageNumAndTotalCountLink(page));
		}
		return pageStr.toString();
	}
	
	private String getPageNumAndTotalCountLink(int page){
		StringBuilder pageStr = new StringBuilder();
		
		pageStr.append("<div class='pull-right'>");
		pageStr.append("<span>共").append("<span class='totalPage'>").append(page).append("</span>").append("页");
		pageStr.append("<strong class='totalCount'>").append(totalCount).append("</strong>").append("条");
		pageStr.append("&nbsp;&nbsp;</span>");
		pageStr.append("</div>");
		return pageStr.toString();
	}
	
	private String getPageHrefLink(int page){
		StringBuilder pageStr = new StringBuilder();
		int pageUp = currentPageNum-1>0?currentPageNum-1:1;   
		int pageDown = currentPageNum+1>page?page:currentPageNum+1;
		String paramStr = getParamStr(param);
		//ul
		pageStr.append("<ul class='pagination pull-right'>");
		//前面链接部分（首页、上页）
	    pageStr.append(getPageLink(1,pageUp,paramStr,"首页","上页"));
		//中间部分
	    pageStr.append(getPageCenterLinks(pageNum,page,currentPageNum,paramStr));
	    //后面链接部分（下页、末页）
	    pageStr.append(getPageLink(pageDown,page,paramStr,"下页","末页"));
	    
	    //跳转页面
	    pageStr.append("<li><input class='skip pull-left' style='width:60px;height:35px;' placeholder='页数'>" +
	    		"<a class='pageSkip' href='javascript:void(null);'>GO&nbsp;<span class='glyphicon glyphicon-circle-arrow-right'></span></a></li>");
	   //结束ul
	    pageStr.append("</ul>");
		return pageStr.toString();
	}
	
	/**
	 * totalPage<=page     ==>全显示
	 * totalPage>page      ==>显示链接数page
	 * @param page
	 * @param paramStr
	 * @return
	 */

	private String getPageCenterLinks(int showPageNum,int totalPage,int currentPage, String paramStr) {
		StringBuilder pageStr = new StringBuilder();
		//要显示的页数>总页数    全显示，否者显示目标页数
		if(totalPage<=showPageNum){
			for(int i=1;i<=totalPage;i++){
				if(i == currentPage)
					pageStr.append("<li class='active'><a href=\"");
				else
					pageStr.append("<li><a href=\"");
				pageStr.append(href).append("?").append("page=").append(i);
				pageStr.append(paramStr);
				pageStr.append("\">");
				pageStr.append(i);
				pageStr.append("</a></li>");
			}
			return pageStr.toString();
		}
		//(展示的页数小于总页数)单数或者双数
		int mod = showPageNum%2;
		int margin = showPageNum/2;
		if(mod == 0){
			//
			pageStr.append(getDoubleShowPage(margin,showPageNum,totalPage,currentPage,paramStr));
		}else{
			pageStr.append(getSingleShowPage(margin,showPageNum,totalPage,currentPage,paramStr));
		}
		return pageStr.toString();
	}

	private String getSingleShowPage(int margin,int showPageNum, int totalPage,
			int currentPage, String paramStr) {
		StringBuilder pageStr = new StringBuilder();
		if(currentPage<=margin){
			for(int i=1;i<=showPageNum;i++){
				append(pageStr, i, currentPage, paramStr);
			}
		}else if(currentPage+margin>totalPage){
			for(int i=totalPage-showPageNum+1;i<=totalPage;i++){
				append(pageStr, i, currentPage, paramStr);
			}
		}else{
			for(int i=currentPage-margin;i<=currentPage+margin;i++){
				append(pageStr, i, currentPage, paramStr);
			}
		}
		return pageStr.toString();
	}

	private String getDoubleShowPage(int margin,int showPageNum, int totalPage,
			int currentPage, String paramStr) {
		StringBuilder pageStr = new StringBuilder();
		if(currentPage<=margin){
			for(int i=1;i<=showPageNum;i++){
				append(pageStr,i,currentPage,paramStr);
			}
		}else if(currentPage+margin>totalPage){
			for(int i=totalPage-showPageNum+1;i<=totalPage;i++){
				append(pageStr,i,currentPage,paramStr);
			}
		}else{
			for(int i = currentPage-margin+1;i<=currentPage+margin;i++){
				append(pageStr,i,currentPage,paramStr);
			}
		}
		return pageStr.toString();
	}

	private String getPageLink(int firstPage,int secondPage,String paramStr,String firstStr,String secondStr) {
		StringBuilder pageStr = new StringBuilder();
		pageStr.append("<li><a href=\"");
		pageStr.append(href).append("?").append("page=").append(firstPage);
		pageStr.append(paramStr);
		pageStr.append("\">");
		pageStr.append(firstStr);
		pageStr.append("</a></li>");
		
		pageStr.append("<li><a href=\"");
		pageStr.append(href).append("?").append("page=").append(secondPage);
		pageStr.append(paramStr);
		pageStr.append("\">");
		pageStr.append(secondStr);
		pageStr.append("</a></li>");
		return pageStr.toString();
	}

	private String getParamStr(Map<String, String> params) {
		StringBuilder paramStr = new StringBuilder();
		
		if(null != params && !params.isEmpty()){
			Set<Entry<String, String>> entry = params.entrySet();
			for (Entry<String, String> e : entry) {
				paramStr.append("&");
				paramStr.append(e.getKey());
				paramStr.append("=");
				paramStr.append(e.getValue());
			}
		}
		return paramStr.toString();
	}

	private void append(StringBuilder pageStr,int i,int currentPage,String paramStr){
		if(i == currentPage)
			pageStr.append("<li class='active'><a href=\"");
		else
			pageStr.append("<li><a href=\"");
		pageStr.append(href).append("?").append("page=").append(i);
		pageStr.append(paramStr);
		pageStr.append("\">");
		pageStr.append(i);
		pageStr.append("</a></li>");
	}
}
