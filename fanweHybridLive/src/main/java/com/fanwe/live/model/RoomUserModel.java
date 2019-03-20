package com.fanwe.live.model;

public class RoomUserModel
{

	private UserModel user;
	// 举报按钮，1-显示，0-不显示
	private int show_tipoff;
	// 管理按钮，1,2-显示，0-不显示(1 管理员：举报，禁言，取消;2主播：设置为管理员/取消管理员，管理员列表，禁言，取消)
	private int show_admin;
	// 0-未关注;1-已关注
	private int has_focus;
	// 0-非管理员;1-是管理员
	private int has_admin;
	private  int v_identity;
	private int v_speak_num;
	private String v_identity_colour;

	public int getV_speak_num() {
		return v_speak_num;
	}

	public void setV_speak_num(int v_speak_num) {
		this.v_speak_num = v_speak_num;
	}

	public String getV_identity_colour() {
		return v_identity_colour;
	}

	public void setV_identity_colour(String v_identity_colour) {
		this.v_identity_colour = v_identity_colour;
	}

	public int getV_identity() {
		return v_identity;
	}

	public void setV_identity(int v_identity) {
		this.v_identity = v_identity;
	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(UserModel user)
	{
		this.user = user;
	}

	public int getShow_tipoff()
	{
		return show_tipoff;
	}

	public void setShow_tipoff(int show_tipoff)
	{
		this.show_tipoff = show_tipoff;
	}

	public int getShow_admin()
	{
		return show_admin;
	}

	public void setShow_admin(int show_admin)
	{
		this.show_admin = show_admin;
	}

	public int getHas_focus()
	{
		return has_focus;
	}

	public void setHas_focus(int has_focus)
	{
		this.has_focus = has_focus;
	}

	public int getHas_admin()
	{
		return has_admin;
	}

	public void setHas_admin(int has_admin)
	{
		this.has_admin = has_admin;
	}

}
