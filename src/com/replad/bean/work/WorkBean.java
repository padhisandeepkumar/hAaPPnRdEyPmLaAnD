package com.replad.bean.work;

import java.io.Serializable;
import java.util.List;

import com.replad.sub.work.SubworkBean;

public class WorkBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int workid=0;
	private String workname=null;
	private String iconfile=null;
	private List<SubworkBean> listSubworkBean = null;
	
	public List<SubworkBean> getListSubworkBean() {
		return listSubworkBean;
	}
	public void setListSubworkBean(List<SubworkBean> listSubworkBean) {
		this.listSubworkBean = listSubworkBean;
	}
	public int getWorkid() {
		return workid;
	}
	public void setWorkid(int workid) {
		this.workid = workid;
	}
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public String getIconfile() {
		return iconfile;
	}
	public void setIconfile(String iconfile) {
		this.iconfile = iconfile;
	}
	
	
}
