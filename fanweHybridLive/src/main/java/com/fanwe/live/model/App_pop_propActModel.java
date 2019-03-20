package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;
import java.util.List;

public class App_pop_propActModel extends BaseActModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "App_pop_propActModel{" +
				"award=" + award +
				", is_notify=" + is_notify +
				", data=" + data +
				'}';
	}


	private AwardBean award;
	private int is_notify;
	private boolean data;

	public AwardBean getAward() {
		return award;
	}

	public void setAward(AwardBean award) {
		this.award = award;
	}

	public int getIs_notify() {
		return is_notify;
	}

	public void setIs_notify(int is_notify) {
		this.is_notify = is_notify;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}


	public static class AwardBean {
		@Override
		public String toString() {
			return "AwardBean{" +
					"status=" + status +
					", errorX='" + error + '\'' +
					", award_log_id=" + award_log_id +
					", winning_type=" + winning_type +
					", winning_num=" + winning_num +
					", winning_diamonds=" + winning_diamonds +
					", prop_id=" + prop_id +
					", user_id=" + user_id +
					", is_animated=" + is_animated +
					", gif_gift_show_style=" + gif_gift_show_style +
					", desc='" + desc + '\'' +
					", desc2='" + desc2 + '\'' +
					", anim_cfg=" + anim_cfg +
					'}';
		}

		/**
		 * status : 1
		 * error :
		 * award_log_id : 46
		 * winning_type : 1
		 * winning_num : 10
		 * winning_diamonds : 7
		 * prop_id : 2
		 * user_id : 200012
		 * is_animated : 1
		 * gif_gift_show_style : 1
		 * desc : 嗯哼送礼物黄瓜中奖了
		 * desc2 : 你送黄瓜中奖了！
		 * anim_cfg : [{"url":"http://site.88817235.cn/public/attachment/201808/14/18/5b72ab528b216.gif","play_count":"1","delay_time":"0","duration":"1000","show_user":"1","type":"2","gif_gift_show_style":null}]
		 */

		private int status;
		private String error;
		private int award_log_id;
		private int winning_type;
		private int winning_num;
		private int winning_diamonds;
		private int prop_id;
		private int user_id;
		private int is_animated;
		private int gif_gift_show_style;
		private String desc;
		private String desc2;
		private List<AnimCfgBean> anim_cfg;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public int getAward_log_id() {
			return award_log_id;
		}

		public void setAward_log_id(int award_log_id) {
			this.award_log_id = award_log_id;
		}

		public int getWinning_type() {
			return winning_type;
		}

		public void setWinning_type(int winning_type) {
			this.winning_type = winning_type;
		}

		public int getWinning_num() {
			return winning_num;
		}

		public void setWinning_num(int winning_num) {
			this.winning_num = winning_num;
		}

		public int getWinning_diamonds() {
			return winning_diamonds;
		}

		public void setWinning_diamonds(int winning_diamonds) {
			this.winning_diamonds = winning_diamonds;
		}

		public int getProp_id() {
			return prop_id;
		}

		public void setProp_id(int prop_id) {
			this.prop_id = prop_id;
		}

		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public int getIs_animated() {
			return is_animated;
		}

		public void setIs_animated(int is_animated) {
			this.is_animated = is_animated;
		}

		public int getGif_gift_show_style() {
			return gif_gift_show_style;
		}

		public void setGif_gift_show_style(int gif_gift_show_style) {
			this.gif_gift_show_style = gif_gift_show_style;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getDesc2() {
			return desc2;
		}

		public void setDesc2(String desc2) {
			this.desc2 = desc2;
		}

		public List<AnimCfgBean> getAnim_cfg() {
			return anim_cfg;
		}

		public void setAnim_cfg(List<AnimCfgBean> anim_cfg) {
			this.anim_cfg = anim_cfg;
		}

		public static class AnimCfgBean {
			/**
			 * url : http://site.88817235.cn/public/attachment/201808/14/18/5b72ab528b216.gif
			 * play_count : 1
			 * delay_time : 0
			 * duration : 1000
			 * show_user : 1
			 * type : 2
			 * gif_gift_show_style : null
			 */

			private String url;
			private String play_count;
			private String delay_time;
			private String duration;
			private String show_user;
			private String type;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getPlay_count() {
				return play_count;
			}

			public void setPlay_count(String play_count) {
				this.play_count = play_count;
			}

			public String getDelay_time() {
				return delay_time;
			}

			public void setDelay_time(String delay_time) {
				this.delay_time = delay_time;
			}

			public String getDuration() {
				return duration;
			}

			public void setDuration(String duration) {
				this.duration = duration;
			}

			public String getShow_user() {
				return show_user;
			}

			public void setShow_user(String show_user) {
				this.show_user = show_user;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

		}
	}
}
