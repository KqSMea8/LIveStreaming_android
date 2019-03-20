package com.fanwe.hybrid.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-25 下午3:51:16 类说明
 */
public class SdkShareModel
{
	private String share_content;// 分享内容
	private String share_imageUrl;// 分享图片链接
	private String share_url;// 分享链接
	private String share_key;// 用于服务端记录
	private String share_title;// 标题

	public String getShare_key()
	{
		return share_key;
	}

	public void setShare_key(String share_key)
	{
		this.share_key = share_key;
	}

	public String getShare_title()
	{
		return share_title;
	}

	public void setShare_title(String share_title)
	{
		this.share_title = share_title;
	}

	public String getShare_content()
	{
		return share_content;
	}

	public void setShare_content(String share_content)
	{
		this.share_content = share_content;
	}

	public String getShare_imageUrl()
	{
		return share_imageUrl;
	}

	public void setShare_imageUrl(String share_imageUrl)
	{
		this.share_imageUrl = share_imageUrl;
	}

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

}
