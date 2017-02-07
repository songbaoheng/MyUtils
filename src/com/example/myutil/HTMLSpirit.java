
package com.example.myutil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 去除文本中特殊字符
 * */
public class HTMLSpirit {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	/**
	 * 去除所有的js脚本、样式及<>
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 去除所有的js脚本
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLScript(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 得到网页中所有图片
	 */
	public static List<String> getImgStr(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = m_image.group().replaceAll("'", "\"");
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
					.matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}
	/**去除文本中所有的标签
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
 
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
                                                                                                        // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
                                                                                                    // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
 
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
 
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
 
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
 
            textStr = htmlStr;
 
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
 
        return textStr;// 返回文本字符串
    }
	public static void main(String[] args) {
		// String
		// aa="<p>凯文·米特尼克，有评论称他为“世界头号骇客”。他的技术也许并不是黑客中最好的，甚至相当多的黑客们都反感他，认为他是只会用攻击、
		//不懂技术的攻击狂，但是其黑客经历是的传奇性足以站全世界为之震惊，也使得所有网络安全人员丢尽面子。</p>
		//<p>他是第一个被美国联邦调查局通缉的黑客，走出牢狱之后，他马上又想插手电脑和互联网。有了他，
		//世界又不平静了。凯文·米特尼克也许可以算得上迄今为止世界上最厉害的黑客，他的名声盛极一时，
		//后随着入狱而逐渐消退。</p><p style=\"text-align: center;\"><img class=\"alignnone size-full wp-image-64209\" alt=\"Kevin Mitnick\" src=\"http://jbcdn2.b0.upaiyun.com/2014/03/11d93aa89afbbeafaff0d8fef679657b.jpg\" /></p><p style=\"text-align: center;\"><p style=\"text-align: center;\"><p>凯文·米特尼克是一个黑客，而且是一个顶级黑客。1964年他出生在美国洛杉矶一个中下阶层的家庭里。3岁时父母就离异了，他跟着母亲劳拉生活，由于家庭环境的变迁导致了他的性格十分孤僻，学习成绩也不佳。但这丝毫没有妨碍米特尼克超人智力的发育。事实上，在很小的时候，米特尼克就显示了他在日后成为美国头号电脑杀手应具备的天才。</p><p>米特尼克小时候喜欢玩“滑铁卢的拿破仑”游戏。这是当时很流行的游戏，根据很多专家的尝试，最快需要78步能使拿破仑杀出重围到达目的地——巴黎。令人吃惊的是，米特尼克很快便带领拿破仑冲出了包围圈。两天以后，米特尼克只花83步就让拿破仑逃过了滑铁卢的灭顶之灾。而一周后，米特尼克就达到了与专家一致的水平——78步。随后，米特尼克便将拿破仑扔进了储物箱里，并淡淡地对母亲说：“已经不能再快了。”当时米特尼克年仅4岁。</p><p>当米特尼克刚刚接触到电脑时，就已经明白他这一生将与电脑密不可分。他对电脑有一种特殊的感情。电脑语言“0 1”的蕴涵的数理逻辑知识与他的思维方式天生合拍，在学习电脑的过程中，为米特尼克几乎没有遇到过什么太大的障碍。他编写的程序简洁、实用、所表现的美感令电脑教师都为之倾倒。他的电脑知识很快便超出了他的年龄。</p><p>直到有一天，老师们发现他用本校的计算机闯入其它学校的网络系统，他因此不得不退学了。美国的一些社区里提供电脑网络服务，米特所在的社区网络中，家庭电脑不仅和企业、大学相通，而且和政府部门相通。当然这些电脑领地之门都会有密码的。这时，一个异乎寻常的大胆的计划在米特脑中形成了。此后，他以远远超出其年龄的耐心和毅力，试图破解美国高级军事密码。不久，只有15岁的米特仅凭一台电脑和一部调制解调器闯入了“<b>北美空中防护指挥系统</b>”的计算机主机，同时和另外一些朋友翻遍了美国指向前苏联及其盟国的核弹头的数据资料，然后又悄然无息的溜了出来。这成为了黑客历史上一次经典之作。1983年好莱坞曾以此为蓝本，拍摄了电影《战争游戏》，演绎了一个同样的故事（在电影中一个少年黑客几乎引发了第三次世界大战）。</p><p>这件事对美国军方来说已成为一大丑闻，五角大楼对此一直保持沉默。事后，美国著名的军事情报专家克赖顿曾说：“如果当时米特尼克将这些情报卖给克格勃（前苏联的著名情报机关），那么他至少可以得到50万美元的酬金。而美国则需花费数十亿美元来重新部署导弹。”</p><p>80年代初正是美国电话业开始转向数字化的时候，米特尼克用遥控方式控制了数字中央控制台的转换器，轻而易举地进入了电话公司的电脑，使他可以任意地拨打免费电话，还可以随意偷听任何人的电话。1981年，米特尼克和同伙在某个假日潜入洛杉矶市电话中心盗取了一批用户密码，毁掉了其中央控制电脑内的一些档案，并用假名植入了一批可供他们使用的电话号码。太平洋电脑公司开始以为电脑出现了故障，经过相当长时间，发现电脑本身毫无问题，这使他们终于明白了：自己的系统被入侵了。</p><p>警方进行了周密地调查，可始终没有结果。直到有一天一名米特尼克同伙的女朋友向警方举报，这时才真相大白。也许由于当时米特尼克年纪尚小，17岁的米特尼克只被判监禁3个月，外加一年监督居住。</p><p>老师们赞叹他是一位电脑奇才，认为他是个很有培养前途的天才少年。但首次监狱生活不仅未使他改过自新，反而使他变本加厉在网络黑客的道路上越走越远。</p><p>1983年，他因被发现使用一台大学里的电脑擅自进入今日互联网的前身<b>ARPA网</b>，并通过该网进入了美国<b>五角大楼</b>的的电脑，而被判在加州的青年管教所管教了6个月。被释后，米特尼克干脆申请了一个号码为“<b>XHACKER</b>”即“前黑客”的汽车牌照，挂在自己的尼桑车上。然后，米特尼克继续在网络上横行无忌，时而潜入软件公司非法窃取其软件，时而进入电脑研究机构的实验室制造麻烦，并继续给电话公司捣蛋。</p><p>2002年，对于曾经臭名昭著的计算机黑客凯文·米特尼克来说，圣诞节提前来到了。这一年，的确是Kevin Mitnick快乐的一年。不但是获得了彻底的自由（从此可以自由上网，不能上网对于黑客来说，就是另一种监狱生活）。而且，他还推出了一本刚刚完成的畅销书《欺骗的艺术》(<span class='wp_keywordlink_affiliate'><a href=\"http://www.amazon.cn/gp/product/0786890002/ref=as_li_qf_sp_asin_il_tl?ie=UTF8&camp=536&creative=3200&creativeASIN=0786890002&linkCode=as2&tag=vastwork-23\" title=\"The Art of Deception\" rel=\"nofollow\" target=\"_blank\">The Art of Deception</a></span>: Controlling the Human Element of Security)。此书大获成功，成为Kevin Mitnick重新引起人们关注的第一炮。</p>";
//		String aa = "<p>凯文·米特尼克，有评论称他为“世界头号骇客”</p><a></a><script>ssss</script>";
//		System.out.println(HTMLSpirit.delHTMLScript(aa));
		String ss = "<img    src='www.diyangsoft.com/saaa.jpg'/>sfsafsdf";
		List<String> picList = getImgStr(ss);
		System.out.println(picList.get(0));
		
	}
}
