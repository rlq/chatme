package com.he.data;

import com.he.data.friendcircle.CircleItem;
import com.he.data.friendcircle.CommentItem;
import com.he.data.friendcircle.FavortItem;
import com.he.data.friendcircle.Friend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class FriendCircleData {

	public final String[] CONTENTS = { "null--", "美丽人生", "呱呱", "哈哈,告诉我你是WHO", "老漂亮咯...","吾爱??",
			"我去,美的不要不要的","天下第一美,有人反对? 站出来", "美乐美乐", "笑吧", "不说话是几个意思" };
	public final String[] PHOTOS = {
			"https://pic.pimg.tw/alx570601/1434006818-3220185377_n.jpg?v=1434006823",
			"http://p1.img.cctvpic.com/photoworkspace/contentimg/2015/05/15/2015051513585426431.jpg",
			"http://img1.cache.oeeee.com/201403/10/2363156180796227205.jpg",
			"http://www.zgrbhb.com/uploadfile/2014/1013/20141013040602826.jpg",
			"http://img1.gtimg.com/ent/pics/hv1/165/136/1884/122541945.jpg",
			"http://www.haopic.me/wp-content/uploads/2016/03/20160304073506790.jpg",
			"http://img1.dog126.com/data/files/store_2221/goods_176/small_201401161129368550.jpg",
			"http://www.90riji.com/wp-content/uploads/2013/03/u3967159204290676343fm24gp0.jpg",
			"http://img1.gtimg.com/fashion/pics/hv1/57/113/1912/124356672.jpg",
			"http://www.people.com.cn/mediafile/pic/20150928/86/1505856476725283458.jpg",
			"http://news.fjsen.com/images/attachement/jpg/site2/20160222/b083feae22b91834a4ea26.jpg",
			"http://image3.wangchao.net.cn/xiezhen/1334185899557.jpg",
			"http://www.sinaimg.cn/dy/slidenews/4_img/2014_09/704_1250278_467420.jpg",
			"http://img.52jbj.com/uploads/allimg/150825/co150R5023114-0.jpg",
			"http://cnpic.crntt.com/upload/201307/13/102627896.jpg",
			"http://www.chinadaily.com.cn/dfpd/attachement/jpg/site1/20130627/eca86bd9ddb21335e3680d.jpg",
			"http://img5.cache.netease.com/ent/2016/1/14/20160114132036079e8.jpg" };
	public final String[] HEADIMG = {
			"http://img.7160.com/uploads/allimg/140619/9-140619121T70-L.jpg",
			"http://img.7160.com/uploads/allimg/140607/9-14060G040290-L.jpg",
			"http://www.ilegance.com/sj/UploadFiles_9645/201111/2011111223572737.jpg",
			"http://www.zhlzw.com/sj/UploadFiles_9645/201111/2011111002080725.jpg",
			"http://www.feizl.com/upload2007/2014_06/1406272351394618.png",
			"http://news.xinhuanet.com/health/2014-10/13/127091729_14131692240341n.jpg",
			"http://ent.people.com.cn/mediafile/201203/19/F201203191024312508121188.jpg",
			"http://himg2.huanqiu.com/attachment2010/2016/0602/20160602110342570.jpg",
			"http://r1.ykimg.com/0514000052BBE8D5675839621C02A850",
			// "http://i3.download.fd.pchome.net/g1/M00/02/06/ooYBAFKgQeOIOn7OAAL0GDilF1wAABIsAPcOxUAAvQw743.jpg",
			"http://img.6xw.com/uploads/allimg/120823/6-120R3163021.jpg",
			"http://img.5669.com/uploads/mingxing/huojianhua/14526678690.jpg",
			"http://r3.ykimg.com/0514000055C014ED67BC3C5D2B0A0CC1",
			"http://imge.gmw.cn/attachement/jpg/site2/20160330/f04da226e54a1865913512.jpg",
			"http://pic.wenwo.com/fimg/6094170_365.jpg",
			"http://www.people.com.cn/mediafile/pic/20160308/64/18247735266948800376.jpg",
			"http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212295986807.jpg"};

	public List<Friend> users = new ArrayList<Friend>();

	private int circleId = 0;

	private int commentId = 0;

	private static FriendCircleData instance;
	public FriendCircleData(){
		if(users.size() <= 0)
			initUsers();
	}
	public static FriendCircleData getInstance(){
		if(instance == null){
			instance = new FriendCircleData();
		}
		return instance;
	}

	public Friend curUser = new Friend("ren123", "自己--小河", HEADIMG[0]);
	public void initUsers(){

		Friend user1 = new Friend("ren1234", "小蓝", HEADIMG[1]);
		Friend user2 = new Friend("ren1243", "小王", HEADIMG[2]);
		Friend user3 = new Friend("ren1235", "小马", HEADIMG[3]);
		Friend user4 = new Friend("4", "清风@@徐来", HEADIMG[4]);
		Friend user5 = new Friend("5", "我是水波", HEADIMG[5]);
		Friend user6 = new Friend("6", "不清不楚的诱惑", HEADIMG[6]);
		Friend user7 = new Friend("7", "我不想多说话,因为我真的长得丑,心塞的要命!", HEADIMG[7]);

		users.add(curUser);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		users.add(user7);
	}
	//static {}

	public List<CircleItem> createCircleDatas(int count, int j) {
		Calendar cc = Calendar.getInstance();
		String m = "" + (cc.get(Calendar.MONTH)+1) +"月";

		List<CircleItem> circleDatas = new ArrayList<CircleItem>();
		for (int i = 0; i < count; i++) {//users.size()
			CircleItem item = new CircleItem();
			Friend user = getUser();
			item.setId(String.valueOf(circleId++));
			item.setUser(user);
			item.setContent(getContent());
			int d = cc.get(Calendar.DATE);
			if(i > j && i <= j*2)
				d = d-1;
			else if(i > j*2 && i <= j*2)
				d = d-2;
			else if(i > j*3)
				d = d-3;
			item.setCreateTime(m+d+"日");
			item.setOwer(curUser);
			item.setFavorters(createFavortItemList());
			item.setComments(createCommentItemList());
			if (getRandomNum(10) % 2 == 0) {
				item.setType("1");// 链接
				//item.setLinkImg("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
				item.setLinkImg("http://mams.br.baidu.com/luckyday/usr/2015/12/07/56659e49946494.45392590.gif");
				item.setLinkTitle("百度一下，你就知道");
			} else {
				item.setType("2");// 图片
				item.setPhotos(createPhotos());
			}
			circleDatas.add(item);
		}
		return circleDatas;
	}

	public Friend getUser() {
		return users.get(getRandomNum(users.size()));
	}

	public String getContent() {
		return CONTENTS[getRandomNum(CONTENTS.length)];
	}

	public int getRandomNum(int max) {
		Random random = new Random();
		int result = random.nextInt(max);
		return result;
	}

	public List<String> createPhotos() {
		List<String> photos = new ArrayList<String>();
		int size = getRandomNum(PHOTOS.length);
		if (size > 0) {
			if (size > 9) {
				size = 9;
			}
			for (int i = 0; i < size; i++) {
				String photo = PHOTOS[getRandomNum(PHOTOS.length)];
				if (!photos.contains(photo)) {
					photos.add(photo);
				} else {
					i--;
				}
			}
		}
		return photos;
	}

	public List<FavortItem> createFavortItemList() {
		int size = getRandomNum(users.size());
		List<FavortItem> items = new ArrayList<FavortItem>();
		List<String> history = new ArrayList<String>();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				FavortItem newItem = createFavortItem();
				String userid = newItem.getUser().getUserId();
				if (!history.contains(userid)) {
					items.add(newItem);
					history.add(userid);
				} else {
					i--;
				}
			}
		}
		return items;
	}

	public FavortItem createFavortItem() {
		FavortItem item = new FavortItem();
		item.setUser(getUser());
		return item;
	}
	
	public FavortItem createCurUserFavortItem() {
		FavortItem item = new FavortItem();
		item.setUser(curUser);
		return item;
	}

	public List<CommentItem> createCommentItemList() {
		List<CommentItem> items = new ArrayList<CommentItem>();
		int size = getRandomNum(9) + 1;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				items.add(createComment());
			}
		}
		return items;
	}

	public CommentItem createComment() {
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(getContent());
		Friend user = getUser();
		item.setUser(user);
		if (getRandomNum(10) % 2 == 0) {
			while (true) {
				Friend replyUser = getUser();
				if (!user.getUserId().equals(replyUser.getUserId())) {
					item.setToReplyUser(replyUser);
					break;
				}
			}
		}
		return item;
	}
	
	/**
	 * 创建发布评论
	 * @return
	 */
	public CommentItem createPublicComment(String content){
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		return item;
	}
	
	/**
	 * 创建回复评论
	 * @return
	 */
	public CommentItem createReplyComment(Friend replyUser, String content){
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		item.setToReplyUser(replyUser);
		return item;
	}

	public List<CircleItem> addCircleDatas(List<CircleItem> curCircleDatas) {
		Calendar cc = Calendar.getInstance();
		String m = "" + (cc.get(Calendar.MONTH)+1) +"月";
		int d = cc.get(Calendar.DATE);

		List<CircleItem> circleDatas = new ArrayList<>(100);
		for (int i = 0; i < 3; i++) {//users.size()
			CircleItem item = new CircleItem();
			Friend user = getUser();
			item.setId(String.valueOf(circleId++));
			item.setUser(user);
			item.setContent(getContent());
			item.setCreateTime(m+d+"日");
			item.setOwer(curUser);
			item.setFavorters(createFavortItemList());
			item.setComments(createCommentItemList());
			if (getRandomNum(10) % 2 == 0) {
				item.setType("1");// 链接
				//item.setLinkImg("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
				item.setLinkImg("http://mams.br.baidu.com/luckyday/usr/2015/12/07/56659e49946494.45392590.gif");
				item.setLinkTitle("百度一下，你就知道");
			} else {
				item.setType("2");// 图片
				item.setPhotos(createPhotos());
			}
			circleDatas.add(item);
		}

		for(int n = 0; n < curCircleDatas.size(); n++){
			circleDatas.add(curCircleDatas.get(n));
		}
		return circleDatas;
	}
}
